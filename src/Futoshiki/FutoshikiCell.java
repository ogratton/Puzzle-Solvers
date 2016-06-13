package Futoshiki;

import java.util.TreeSet;

/**
 * TODO make an abstract class that both this and SudokuCell can extend
 * as this is exactly the same code minus the box stuff
 * @author Oliver
 *
 */
public class FutoshikiCell
{
	private int value;
	private TreeSet<Integer> poss = new TreeSet<Integer>();

	/**
	 * Contruct a cell that could be anything (full poss)
	 * Also assign it a value
	 * 
	 * @param value the definite value of the cell
	 */
	public FutoshikiCell(int dim, int value)
	{
		this.value = value;
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
	
	public String toString()
	{
		return String.valueOf(value);
	}
}
