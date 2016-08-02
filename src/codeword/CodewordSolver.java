package codeword;

import java.util.ArrayList;

import auxil.CSVReader;

/**
 * TODO make a GUI for this
 * An early version should allow the user to solve it
 * The final version should have a "solve" button
 * 
 * @author Oliver
 *
 */
public class CodewordSolver
{
	private ArrayList<String[]> words = new ArrayList<String[]>();
	private HypothesisTable hypTable = new HypothesisTable();
	private WordGuessTable guessTable = new WordGuessTable();
	private WordGuesser wg = new WordGuesser("dict/super.txt");
	private String[][] grid;
	private int height;
	private int width;

	private final String NO_HYP = "_"; // the string to indicate no hypothesis

	public CodewordSolver(String filename)
	{
		// read grid from file
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);
		height = setUp.size() - 1;
		width = setUp.get(0).length;
		grid = new String[height][width];

		try
		{
			// get the given letters from file
			String[] clues = setUp.get(height);
			for (int i = 0; i < clues.length; i++)
			{
				String[] clue = clues[i].split("=");
				hypTable.makeHyp(clue[0], clue[1]);
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println("Please enter the given letters");
		}

		// fill the grid with the actual numbers
		// TODO debug only, not essential for solving
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				grid[i][j] = setUp.get(i)[j];
			}
		}

		// extract the chains of squares that will make up words from the puzzle
		getWords();
		printGrid(hypTable);
		// start the solving algorithm
		Tuple<Boolean, HypothesisTable> solution = solver();
		if (solution.x)
		{
			System.out.println("SUCCESS!!");
			printGrid(solution.y);
			solution.y.print();
		}
		else
		{
			System.out.println("Failure :(");
			printGrid(solution.y);
			solution.y.print();
		}
	}

	/**
	 * Starts solving algorithm found in Solver
	 */
	private Tuple<Boolean, HypothesisTable> solver()
	{
		for (int i = 0; i < words.size(); i++)
		{
			ArrayList<String> guesses = wg.guess(words.get(i), hypTable);
			guessTable.addWord(words.get(i), guesses);
			//			System.out.println(guessTable.getWord(words.get(i)).size());
		}

		Solver solver = new Solver(hypTable, guessTable, words, wg, height, width, grid);
		return solver.start();
	}

	/**
	 * Read through the puzzle and pick out the words
	 */
	private void getWords()
	{
		ArrayList<String> horizWord = new ArrayList<String>();
		ArrayList<String> vertiWord = new ArrayList<String>();
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				getWords_helper(horizWord, i, j);
				getWords_helper(vertiWord, j, i);
			}
			getWords_reachedEnd(horizWord);
			getWords_reachedEnd(vertiWord);
		}
	}

	private void getWords_helper(ArrayList<String> currentWord, int i, int j)
	{
		// if we reach a blank space
		if (grid[i][j].equals("00"))
		{
			getWords_reachedEnd(currentWord);
		}
		// we're on a number
		else
		{
			String num = grid[i][j];
			if (num.startsWith("0"))
			{
				num = num.substring(1, 2); // strip the leading zero if present
				grid[i][j] = num;
			}
			currentWord.add(num);
		}
	}

	/**
	 * Helper function for getWords for when we reach a blank space or the end
	 * of the line
	 * 
	 * @param currentWord
	 */
	private void getWords_reachedEnd(ArrayList<String> currentWord)
	{
		if (currentWord.size() > 1)
		{
			words.add(arrayListToArray(currentWord));
			//			System.out.println(currentWord);
			currentWord.clear();
		}
		else
		{
			currentWord.clear();
		}
	}

	private String[] arrayListToArray(ArrayList<String> al)
	{
		String[] a = new String[al.size()];
		for (int i = 0; i < al.size(); i++)
		{
			a[i] = al.get(i);
		}
		return a;
	}

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
				if (grid[i][j].equals("00") || grid[i][j].equals("0"))
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

	public static void main(String[] args)
	{
		new CodewordSolver("codewords/5.csv");
	}
}
