package griddlerOld;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * 
 * @author Oliver
 *
 */

public class Griddler 
{
	public static final int BLANK = 0;
	public static final int FILL = -1;
	public static final int NOT = -2;
	public static final int NULL = -3; // maybe use this for the blank squares outside the main grid (with clues)
	
	private int gw; 			// grid width
	private int gh; 			// grid height
	
	private int w;
	private int h;
	
	private int[][] grid;		// grid
	private int[][] col;		// column clues
	private int[][] row;		// row clues
	
	private int rcl; 			// max row clue length
	private int ccl;			// max col clue length
	
	public Griddler(int[][] row, int[][]col)
	{
		this.rcl = row[0].length;
		this.ccl = col[0].length;
		this.gh = row.length + ccl;
		this.gw = col.length + rcl;
		this.h = row.length;
		this.w = col.length;
		this.row = row;
		this.col = col;
		
		//System.out.println(this.gw + " by " + this.gh);
		
		drawGrid();	
	}
	
	public void drawGrid()
	{
		this.grid = new int[gw][gh];
		
		for (int x=0; x<gw; x++) // width
		{
			for (int y=0; y<gh; y++) // height
			{
				// The board is split into 4 parts: 
				if (y < ccl)
				{
					// 1) The null bit (top left)
					if (x < rcl)
					{
						grid[x][y] = NULL;
					}
					// 2) The column clues (top right)
					else
					{
						if (col[x-rcl][y] != 0)
						{
							grid[x][y] = col[x-rcl][y];
						}
						else
						{
							grid[x][y] = NULL;
						}
						
					}
				}
				else
				{
					// 3) The row clues (bottom left)
					if (x < rcl)
					{
						if (row[y-ccl][x] != 0)
						{
							grid[x][y] = row[y-ccl][x]; 
						}
						else
						{
							grid[x][y] = NULL;
						}
					}
					// 4) The main puzzle (bottom right, starts as all blank)
					else
					{
						grid[x][y] = BLANK;
					}
				}
			}
		}
	}
	
	public void solve()
	{
		rowMethod1();
	}
	
	private void rowMethod1()
	{
		System.out.println("SOLVING");
		for (int clues=0; clues<h; clues++)
		{
			ArrayList<Integer> rowStrip = stripZeroes(row[clues]);
			int sum = IntStream.of(row[clues]).sum();
			
			// Check if the row can be filled straight away
			if (sum + rowStrip.size() - 1 == w)
			{
				fillRow(0,w,clues);
				System.out.println("filling all of row " + clues);
			}
			else if (rowStrip.size() == 1 && rowStrip.get(0) >= (w/2))
			{
				fillRow(w-rowStrip.get(0)+1,rowStrip.get(0),clues);
				System.out.println("filling middle of row " + clues);
			}
			
		}
	}
	
	private void fillRow(int start, int end, int clueIndex)
	{
		// DOES NOT INPUT CROSSES! so it's useless
		
		// for the number of clues (including 0s)
		for (int c=0; c<row[0].length;c++)
		{
			if (row[clueIndex][c] != 0)
			{
				if (start == end) // lazy solution to filling only one square
				{
					for (int i=start;i<=end;i++)
					{
						grid[i+rcl][clueIndex+ccl] = FILL;
						/*if (i==end)
						{
							grid[i+rcl][clueIndex+ccl] = NOT;
						}*/
					}
				}
				for (int i=start;i<end;i++)
				{
					grid[i+rcl][clueIndex+ccl] = FILL;
					/*if (i == end-1)
					{
						grid[i+rcl][clueIndex+ccl] = NOT;
					}*/
				}
			}
		}
	}
	
	/*private void fillCol(int start, int end, int clueIndex)
	{
		for (int c=0; c<col[0].length;c++)
		{
			if (col[clueIndex][c] != 0)
			{
				if (start == end) // lazy solution to filling only one square
				{
					for (int i=start;i<=end;i++)
					{
						grid[i+ccl][clueIndex+rcl] = FILL;
					}
				}
				for (int i=start;i<end;i++)
				{
					grid[i+ccl][clueIndex+rcl] = FILL;
				}
			}
		}
	}*/
	
	public int get(int x, int y)
	{
		return grid[x][y];
	}
	
	public int getWidth()
	{
		return this.gw;
	}
	
	public int getHeight()
	{
		return this.gh;
	}
	
	/**
	 * Takes an array and returns an equivalent array list but stripped of 0s
	 * @param input the array
	 * @return the stripped array list
	 */
	private ArrayList<Integer> stripZeroes(int[] input)
	{
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (int i=0; i< input.length; i++)
		{
			if (input[i] != 0)
			{
				output.add(input[i]);
			}
		}
		return output;
	}
	
	public void printGrid()
	{
		for (int i=0; i<gh; i++)
		{
			for (int j=0; j<gw; j++)
			{
				System.out.print(grid[j][i]+"\t");
			} System.out.println();
		} System.out.println();
	}
}
