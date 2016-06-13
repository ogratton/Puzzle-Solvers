package Codeword;

import java.util.ArrayList;
import auxil.CSVReader;

/**
 * TODO make a GUI for this
 * An early version should allow the user to solve it
 * The final version should have a "solve" button
 * @author Oliver
 *
 */
public class CodewordSolver
{
	private String[][] grid;
	private int height;
	private int width;
	
	// need a map of numbers to letters

	public CodewordSolver(String filename)
	{
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);
		height = setUp.size();
		width = setUp.get(0).length;
		grid = new String[height][width];
		for (int i = 0; i < height; i++)
		{
			for ( int j = 0; j < height; j++)
			{
				grid[i][j] = setUp.get(i)[j];
			}
		}
		printGrid();
		// the letters we are given at the start:
		// TODO these should be read from the file
		replace("16","B");
		replace("17","Z");
		replace("26","K");
//		replace("23","A");
//		replace("04","O");
//		replace("21","S");
		printGrid();
	}
	
	public void replace(String number, String letter)
	{
		for (int i = 0; i < height; i++)
		{
			for ( int j = 0; j < height; j++)
			{
				if (grid[i][j].equals(number))
				{
					grid[i][j] = letter+" "; // the space is so it prints nicely
				}
			}
		}
	}

	public void printGrid()
	{
		for (int i = 0; i < height; i++)
		{
			for ( int j = 0; j < height; j++)
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
				
				System.out.print(value+"|");
			}
			System.out.println(); // print below each line
		}
		System.out.println("----------------------------------------"); // print below last row
	}

	public static void main(String[] args)
	{
		new CodewordSolver("codewords/1.csv");
	}
}
