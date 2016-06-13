package Sudoku;

import java.util.TreeSet;

/**
 * The smallest squares in Sudoku
 * Can contain EITHER a definite value or a list of possible values based on the
 * board (i.e., if value == 0, check poss)
 * TODO the cell may to know its coordinates
 * 
 * @author Oliver
 *
 */
public class SudokuCell
{
	private int value;
	private int boxID;
	private TreeSet<Integer> poss = new TreeSet<Integer>();

	/**
	 * Contruct a cell that could be anything (full poss)
	 * Also assign it a value
	 * 
	 * @param value the definite value of the cell
	 */
	public SudokuCell(int dim, int value, int boxID)
	{
		this.value = value;
		this.boxID = boxID;
		if (value == 0)
		{
			for (int i = 1; i < dim+1; i++)
			{
				poss.add(Integer.valueOf(i));
			}
		}
		else
		{
			poss.add(value);
		}

	}

	/**
	 * Set the actual value of the cell
	 * 
	 * @param value the real value of the cell
	 */
	public void setValue(int value)
	{
		this.value = value;
	}

	/**
	 * @return The definite value of the cell
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Remove a possibility from the set
	 * This will be because it exists in another cell in the same row, column or
	 * box
	 * 
	 * @param v Value being removed
	 */
	public void remPoss(int v)
	{
		poss.remove(Integer.valueOf(v));
		// if, having removed the above possibility, we are left with but one
		// in our set, we can say for sure that this is the real value
		if (poss.size() == 1)
		{
			value = poss.first();
		}
	}
	
	public void setPoss(TreeSet<Integer> newPoss)
	{
		poss = newPoss;
		if (poss.size() == 1)
		{
			value = poss.first();
		}
	}

	/**
	 * @return The full set of possibilities for this cell
	 */
	public TreeSet<Integer> getPoss()
	{
		return poss;
	}
	
	public int getBoxID()
	{
		return boxID;
	}
	
	public String toString()
	{
		return String.valueOf(value);
	}
}
