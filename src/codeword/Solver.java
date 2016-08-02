package codeword;

import java.util.ArrayList;

/**
 * The immutable data structure in which the solving shall take place
 * 
 * @author Oliver
 *
 */
public class Solver
{
	private HypothesisTable hypTable;
	private WordGuessTable guessTable;
	private ArrayList<String[]> words;
	private String[] word;
	private WordGuesser wg;

	// TODO temp debug fields:
	private int height;
	private int width;
	private final String NO_HYP = "_"; // the string to indicate no hypothesis
	private String[][] grid;

	public Solver(HypothesisTable ht, WordGuessTable wgt, ArrayList<String[]> ws, WordGuesser wg, int height, int width, String[][] grid)
	{
		hypTable = ht;
		guessTable = wgt;
		words = ws;
		this.wg = wg;
		// TODO temp debug fields
		this.height = height;
		this.width = width;
		this.grid = grid;
	}

	/**
	 * Begin solving
	 * 
	 * @return true if successful, false if not, bundled in a tuple with the
	 * hypTable so that we can see the result
	 */
	public Tuple<Boolean, HypothesisTable> start()
	{
		// get the word we'll we working on
		if (!words.isEmpty())
			word = findMostCertainWord();
		else
		{
			// TODO this is code copying from below. Bad boy.
			if (hypTable.isFull()) // TODO I *think* this is an adequate end goal measure
				// TODO should also check if the hyp table contains all the letters of the alphabet
				return new Tuple<Boolean, HypothesisTable>(new Boolean(true), hypTable);
			else
				return new Tuple<Boolean, HypothesisTable>(new Boolean(false), hypTable);
		}

		// if any words have zero possibilities then we have either finished or got something wrong
		for (String[] word : words) // coded words left in puzzle
		{
			if (guessTable.getWord(word).isEmpty())
			{
				if (hypTable.isFull())
				{
					/*
					 * the puzzle is solved, so now we pass the correct hypTable
					 * all the way up the tree to the top
					 */
					return new Tuple<Boolean, HypothesisTable>(new Boolean(true), hypTable);
				}
				else
				{
					/*
					 * there is a word pattern that we can't guess this may be a
					 * dictionary problem but in most cases I should think it is
					 * because a hypothesis was wrong
					 */
					return new Tuple<Boolean, HypothesisTable>(new Boolean(false), hypTable);
				}
			}
		}

		/*
		 * for every possibility of 'The Word', make and start a child Solver
		 * for it. This will build up a depth first game tree
		 */
		for (int i = 0; i < guessTable.getWord(word).size(); i++)
		{

			// apply the hypothesis of "word"
			HypothesisTable htCopy;
			htCopy = applyHypothesis(i);
			// refresh the guess table with the new hyp table
			WordGuessTable wgtCopy = refreshGuesses(htCopy);

			// creates a tree of Solvers as they are started before this Solver ends
			Solver child = genChild(htCopy, wgtCopy);

			Tuple<Boolean, HypothesisTable> solution = child.start();
			if (solution.x)
			{
				return solution; // child was successful!!1!
			}

		}
		return new Tuple<Boolean, HypothesisTable>(new Boolean(false), hypTable); // all the children have run and they have all failed
	}

	/**
	 * make a single child
	 * 
	 * @return a child
	 */
	public Solver genChild(HypothesisTable ht, WordGuessTable wgt)
	{
		words.remove(word);
		return new Solver(ht, wgt, words, wg, height, width, grid);
	}

	/**
	 * Calculate the possibilities for all the words in the puzzle
	 * 
	 * @param ht the unborn child's ht with "word" applied
	 */
	private WordGuessTable refreshGuesses(HypothesisTable ht)
	{
		WordGuessTable wgtCopy = guessTable.copy();

		for (int i = 0; i < words.size(); i++)
		{
			ArrayList<String> guesses = wg.guess(words.get(i), ht);
			wgtCopy.addWord(words.get(i), guesses);
		}
		return wgtCopy;
	}

	/**
	 * Of all the coded words, return the one with the fewest possibilities
	 * 
	 * @return a coded word
	 */
	private String[] findMostCertainWord()
	{
		// to start, assume the first is the best
		String[] mostCertainWord = words.get(0);
		// then get proven wrong (most of the time)
		for (String[] word : words)
		{
			if (guessTable.getWord(word).size() < guessTable.getWord(mostCertainWord).size())
			{
				mostCertainWord = word;
			}
		}
		return mostCertainWord;
	}

	/**
	 * Use The Word and make a hypothesis for each of its letters
	 * @param pos 
	 * 
	 * @return A new copy of the hypothesis table with changes applied
	 * @throws InvalidHypothesisException if a letter is already hypothesised to another number
	 */
	private HypothesisTable applyHypothesis(int pos)
	{
		// Copy existing hypTable
		HypothesisTable htCopy = hypTable.copy();
		// get the first of the possibilities for the coded word with the least possibilities
		String guess = guessTable.getWord(word).get(pos);
		for (int i = 0; i < word.length; i++)
		{
			String letter = guess.substring(i, i + 1);
			//TODO if the letter isn't already hypothesised elsewhere
			htCopy.makeHyp(word[i], letter);
			System.out.println("Hyp: " + word[i] + "\t= " + letter);

		}

		printGrid(htCopy);

		return htCopy;
	}

	//	/**
	//	 * DEBUG
	//	 * Print an array with items separated by a comma (not toString)
	//	 * 
	//	 * @param array array to be printed
	//	 */
	//	private void printArray(String[] array)
	//	{
	//		String toPrint = "";
	//		for (int i = 0; i < array.length; i++)
	//		{
	//			toPrint += array[i] + ",";
	//		}
	//		System.out.println(toPrint.substring(0, toPrint.length() - 1));
	//	}

	/**
	 * DEBUG
	 * Format the codeword somewhat
	 */
	public void printGrid(HypothesisTable htCopy)
	{
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (i == 0 && j == 0)
				{
					System.out.println("----------------------------------------"); // print above first row
				}
				if (j == 0)
				{
					System.out.print("|"); // print at the beginning of the line
				}
				String value;
				if (grid[i][j].equals("00"))
				{
					value = "  ";
				}
				else
				{
					String hyp = htCopy.getHyp(grid[i][j]);

					// if no hyp made yet stick with the number
					if (hyp.equals(NO_HYP))
					{
						value = grid[i][j];
						if (value.length() == 1)
						{
							value += " ";
						}
					}
					// hyp has been made
					else
					{
						value = hyp + " ";
					}
				}

				System.out.print(value + "|");
			}
			System.out.println(); // print below each line
		}
		System.out.println("----------------------------------------"); // print below last row
	}

}
