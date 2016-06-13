package Futoshiki;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import auxil.CSVReader;

public class FutoshikiSolver
{
	private int dim;
	private int big_dim;
	private String[][] board; // what we read from the file, < data included
	private FutoshikiCell[][] grid; // just the boxes (no < data)
	private Map<Point,Point> info; // where the <,>,v,^ data will be stored with coords in grid

	public FutoshikiSolver(String filename)
	{
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);
		dim = 1+setUp.size()/2;
		big_dim = setUp.size();
		board = new String[big_dim][big_dim];
		grid = new FutoshikiCell[dim][dim];
		
		for (int i = 0; i < setUp.size(); i++)
		{
			for (int j = 0; j < setUp.size(); j++)
			{
				//System.out.println(grid[i][j]);
				board[i][j] = setUp.get(i)[j];
				if (i %2 == 0 && j %2 == 0)
				{
					grid[i/2][j/2] = new FutoshikiCell(dim, Integer.valueOf(setUp.get(i)[j]));
				}
			}
		}
		printBoard();
	}
	
	/**
	 * TODO should eventually construct this from the grid
	 * unless you want to have to remember to update the board
	 * whenever you update the grid, which could get messy
	 */
	private void printBoard()
	{
		// using board
		for (int i = 0; i < big_dim; i++)
		{
			for (int j = 0; j < big_dim; j++)
			{
				String s = board[i][j];
				if (s.equals("-"))
				{
					s = " ";
				}
				if (s.equals("0"))
				{
					s = "?";
				}
				System.out.print(s+" ");
			}
			System.out.println();
		}
		
		System.out.println("\n\n");
		
		// using grid
		for (int i = 0; i < dim; i++)
		{
			for (int j = 0; j < dim; j++)
			{
				String symb = "   "; // TODO this should check if there should be an operator here
				System.out.print(grid[i][j]+symb);
			}
			System.out.println("\n"); // TODO should be appropriate operators
		}
		
	}

	public static void main(String[] args)
	{
		new FutoshikiSolver("futoshiki/1.csv");
	}

}
