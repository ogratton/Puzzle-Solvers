package Sudoku;

import java.util.ArrayList;
import java.util.TreeSet;

import auxil.CSVReader;

/**
 * TODO make it admit defeat after it gets stuck for 3 turns
 * The bulk of the work is in here
 * This makes a 2D array of SudokuCells and calculates the "poss" set based on
 * the actual values
 * 
 * @author Oliver
 *
 */
public class SudokuSolver
{
	private final int DIM = 9;
	private final int BOX = DIM / 3; // assumes 3x3 boxes all the time
	private SudokuCell[][] grid = new SudokuCell[DIM][DIM];

	public SudokuSolver(String filename)
	{
		CSVReader csv = new CSVReader();
		ArrayList<String[]> setUp = csv.readContents(filename);

		for (int i = 0; i < setUp.size(); i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				// need to tell each cell what box it's in
				double boxI = Math.ceil((i + 1) / (double) BOX);
				double boxY = Math.ceil((j + 1) / (double) BOX); // this should give the coords of the big boxes
				int boxID = (int) ((boxI * 100) + boxY); // this should be different for every box

				grid[i][j] = new SudokuCell(DIM,Integer.valueOf(setUp.get(i)[j]), boxID);
			}
		}
	}
	
	public SudokuCell[][] getGrid()
	{
		return grid;
	}
	
	/**
	 * Solve the board
	 * TODO make it realise when it's stuck
	 */
	public void solve()
	{
		System.out.println("Solving board: ");
		printBoard();
		
		boolean stuck = false;
		while (!solved() && !stuck)//for (int i = 0; i < 4; i++)
		{
			stuck = evaluate();
			printBoard();
		}
		if (stuck)
		{
			System.out.println("I can't do that one :(");
		}
		else
		{
			System.out.println("Solved!");
		}
	}

	/**
	 * Recalculate the "poss" set of every blank square
	 * 
	 * @return true if anything changed
	 */
	private boolean evaluate()
	{
		boolean stuck = true; // assume we are stuck until we change something
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				stuck = eval(i, j);
				// (change stuck to false if we successfully do anything)
				// TODO as it is, this is only affected by cell 8,8. silly
			}
		}
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				check(i,j);
				// (change stuck to false if we successfully do anything)
			}
		}
		return stuck; // if we didn't do anything, we are stuck
	}

	/**
	 * Updates the cell's poss based on the other cells in that row/box/column
	 * 
	 * @param x column coord
	 * @param y row coord
	 * @return true if anything changed
	 */
	private boolean eval(int x, int y)
	{
		TreeSet<Integer> origPoss = grid[x][y].getPoss();
		ArrayList<Integer> toRemove = new ArrayList<Integer>();

		// check the rows
		for (int j = 0; j < DIM; j++)
		{
			// don't need to worry about adding 0s
			toRemove.add(grid[x][j].getValue());
		}

		// check the columns
		for (int i = 0; i < DIM; i++)
		{
			// don't need to worry about adding 0s
			toRemove.add(grid[i][y].getValue());
		}

		// check the box
		int boxID = grid[x][y].getBoxID();
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				if (grid[i][j].getBoxID() == boxID)
				{
					toRemove.add(grid[i][j].getValue());
				}
			}
		}

		for (Integer i : toRemove)
		{
			if (grid[x][y].getValue() == 0)
			{
				grid[x][y].remPoss(i);
			}
			
		}

		// returns true if anything changed
		return !origPoss.containsAll(grid[x][y].getPoss());
	}

	/**
	 * For each row/box/column, check if any one item in a cell's poss is
	 * unique.
	 * If so, this can be set to the actual definite value
	 * 
	 * @return true if anything changed
	 */
	private void check(int x, int y)
	{
		TreeSet<Integer> origPoss = grid[x][y].getPoss();
//		System.out.println("CHECK: Cell "+x+","+y);
//		System.out.println("CHECK: "+origPoss);
		// for each int in poss
		if (grid[x][y].getValue() == 0)
		{
			for (Integer i : origPoss)
			{
//				System.out.println("CHECK: "+i);
				// how many times this number occurs in the 'poss' of cells in the ROW
				int countRow = 0;
				// for each cell in row
				for (int j = 0; j < DIM; j++)
				{
					// count if it occurs in the poss set
					if (grid[x][j].getPoss().contains(i))
					{
//						System.out.println("CHECK: "+x+","+j+" could be "+i);
						countRow++;
					}
				}
//				System.out.println("CHECK: "+countRow+" potentials in row "+x+" for "+i);
				int countCol = 0;
				for (int j = 0; j < DIM; j++)
				{
					// count if it occurs in the poss set
					if (grid[j][y].getPoss().contains(i))
					{
//						System.out.println("CHECK: "+j+","+y+" could be "+i);
						countCol++;
					}
				}
//				System.out.println("CHECK: "+countCol+" potentials in col "+y+" for "+i);
				int countBox = 0;
				int boxID = grid[x][y].getBoxID();
				for (int j = 0; j < DIM; j++)
				{
					for (int k = 0; k < DIM; k++)
					{
						if (grid[j][k].getBoxID() == boxID)
						{
							if (grid[j][k].getPoss().contains(i))
							{
//								System.out.println("CHECK: "+j+","+k+" could be "+i);
								countBox++;
							}
						}
					}
				}
//				System.out.println("CHECK: "+countBox+" potentials in box "+boxID+" for "+i);

				// if it only occurred once in any factor
				if (countRow == 1 || countCol == 1 || countBox == 1)
				{
					//grid[x][y].setValue(i);
					// this should theoretically set the value too seeing as the set size is 1;
					TreeSet<Integer> newPoss = new TreeSet<Integer>();
					newPoss.add(i);
					grid[x][y].setPoss(newPoss);
					break;
				}

			}
		}
		
	}

	/**
	 * Determines if the puzzle is solved
	 * Does not check if the board is legal as that is assumed
	 * to be taken care of when solving
	 * 
	 * @return true if there are no blank spaces left
	 */
	private boolean solved()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				if (grid[i][j].getValue() == 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	private void printBoard()
	{
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				if (i == 0 && j == 0)
				{
					System.out.println("\n---------------------------------------"); // print above
				}
				if (j == 0)
				{
					System.out.print("|"); // print at the beginning of the line
				}
				String value = grid[i][j].getValue() == 0 ? " " : grid[i][j].toString();
				System.out.print(" " + value + " |"); // print after the value
				if ((j + 1) % BOX == 0 && j != DIM - 1)
				{
					System.out.print("|"); // make the double thick lines
				}
			}
			if (i == DIM - 1)
			{
				System.out.println("\n---------------------------------------"); // print at the very bottom
			}
			else if ((i + 1) % BOX == 0)
			{
				System.out.println("\n|===+===+===++===+===+===++===+===+===|"); // print below
			}
			else
			{
				System.out.println("\n|---+---+---++---+---+---++---+---+---|"); // print below
			}
		}
	}

	/**
	 * Run it
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		SudokuSolver sud = new SudokuSolver("sudoku/7.csv"); // TODO solve "sudoku/hardest_ever.csv"
		sud.solve();
	}
}
