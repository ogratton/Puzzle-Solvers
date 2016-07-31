package codeword;

import java.util.ArrayList;

import auxil.CSVReader;

/**
 * TODO there will be a problem with having 01 and 1 etc. fix.
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
	private int height;
	private int width;

	public CodewordSolver(String filename)
	{
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);
		height = setUp.size();
		width = setUp.get(0).length;
		grid = new String[height][width];

		// fill the grid with the actual numbers
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				grid[i][j] = setUp.get(i)[j];
			}
		}
		getWords();
		
		// TODO: TEMP: PROVIDE STARTER LETTERS HERE
		hypTable.makeHyp("16", "b");
		hypTable.makeHyp("17", "z");
		hypTable.makeHyp("26", "k");
		
		solver();
		//printGrid();
	}
	
	private void solver()
	{
		// run WordGuesser on all the words
		for (int i = 0; i < words.size(); i++)
		{
			ArrayList<String> guesses = wg.guess(words.get(i), hypTable);
			System.out.println(guesses.size());
		}
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
			words.add(ArrayListToArray(currentWord));
			System.out.println(currentWord);
			currentWord.clear();
		}
		else
		{
			currentWord.clear();
		}
	}
	
	private String[] ArrayListToArray(ArrayList<String> al)
	{
		String[] a = new String[al.size()];
		for (int i = 0; i < al.size(); i++)
		{
			a[i] = al.get(i);
		}
		return a;
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
