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
	private String[][] grid;
	private ArrayList<String[]> words = new ArrayList<String[]>();
	private WordGuesser wg = new WordGuesser("dict/super.txt");
	private HypothesisTable hypTable = new HypothesisTable();
	private WordGuessTable guessTable = new WordGuessTable();
	private int height;
	private int width;

	public CodewordSolver(String filename)
	{
		// read grid from file
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);
		height = setUp.size() - 1;
		width = setUp.get(0).length;
		grid = new String[height][width];

		// get the given letters
		String[] clues = setUp.get(height);
		for (int i = 0; i < clues.length; i++)
		{
			String[] clue = clues[i].split("=");
			hypTable.makeHyp(clue[0], clue[1]);
		}

		// fill the grid with the actual numbers
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				grid[i][j] = setUp.get(i)[j];
			}
		}

		getWords();
		refreshGuesses(guessTable);
		solver();
		//printGrid();
	}

	/**
	 * Solving algorithm
	 * Should be recursive
	 */
	private void solver()
	{
		// TODO lol
		/*
		 * Need a recursive strategy with immutable tables
		 * Each "node" must contain its parents' tables too
		 * This needs to be its own immutable data structure
		 */
		WordGuessTable tempGuessTable = guessTable.clone();

		// 1. work through guesses for the "words" with the fewest possibilities
		String[] mostCertainWord = findMostCertainWord(tempGuessTable);
		String guess = tempGuessTable.getWord(mostCertainWord).get(0);
		// 2. apply one of these and remove it from the "guess list"
		for (int j = 0; j < mostCertainWord.length; j++)
		{
			hypTable.makeHyp(mostCertainWord[j], guess.substring(j, j + 1));
		}
		tempGuessTable.remHyp(mostCertainWord);
		// 3. refresh guessTable
		refreshGuesses(tempGuessTable);

		// if any words have 0 guesses, either the dictionary has failed or one of the hypotheses has
		// therefore undo the most recent hypothesis and try the next guess along (now the top as we removed the one we used)
	}

	/**
	 * Calculate the possibilities for all the words in the puzzle
	 * 
	 * @param wgt the current table being used
	 */
	private void refreshGuesses(WordGuessTable wgt)
	{
		for (int i = 0; i < words.size(); i++)
		{
			ArrayList<String> guesses = wg.guess(words.get(i), hypTable);
			wgt.addWord(words.get(i), guesses);
			System.out.println(wgt.getWord(words.get(i)).size());
		}
	}

	private String[] findMostCertainWord(WordGuessTable wgt)
	{
		String[] mostCertainWord = words.get(0);
		for (String[] word : words)
		{
			if (wgt.getWord(word).size() < wgt.getWord(mostCertainWord).size())
			{
				mostCertainWord = word;
			}
		}
		printArray(mostCertainWord);
		return mostCertainWord;
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
	 * Print an array with items separated by a comma (not toString)
	 * 
	 * @param array array to be printed
	 */
	private void printArray(String[] array)
	{
		String toPrint = "";
		for (int i = 0; i < array.length; i++)
		{
			toPrint += array[i] + ",";
		}
		System.out.println(toPrint.substring(0, toPrint.length() - 1));
	}

	/**
	 * Format the codeword somewhat
	 */
	public void printGrid()
	{
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < height; j++)
			{
				if (i == 0 && j == 0)
				{
					System.out.println("----------------------------------------"); // print above first row
				}
				if (j == 0)
				{
					System.out.print("|"); // print at the beginning of the line
				}
				String value = grid[i][j].equals("00") ? "  " : grid[i][j];
				// TODO add in hypotheses

				System.out.print(value + "|");
			}
			System.out.println(); // print below each line
		}
		System.out.println("----------------------------------------"); // print below last row
	}

	public static void main(String[] args)
	{
		new CodewordSolver("codewords/2.csv");
	}
}
