package codeword;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A table of the numbers 1-26 to map with letters a-z for codewords
 * 
 * @author Oliver
 *
 */
public class HypothesisTable
{
	private ConcurrentMap<String, String> hypTable = new ConcurrentHashMap<String, String>();
	private final String NO_HYP = "_"; // the string to indicate no hypothesis

	public HypothesisTable()
	{
		// set up the table with 26 entries all with the noHyp symbol
		for (int i = 1; i <= 26; i++)
		{
			hypTable.put(Integer.toString(i), NO_HYP);
		}
	}

	/**
	 * Make a hypothesis for "number" to stand for "letter"
	 * 
	 * @param number the number from the clue
	 * @param letter the letter from the word
	 */
	public void makeHyp(String number, String letter)
	{
			hypTable.put(number, letter);
	}
	
	/**
	 * Make a hypothesis for "number" to stand for "letter"
	 * Also checks if "letter" is already assigned to a "number"
	 * @param number the number from the clue
	 * @param letter the letter from the word
	 * @throws InvalidHypothesisException
	 */
	public void makeHyp2(String number, String letter) throws InvalidHypothesisException 
	{
		ArrayList<String> existingHyps = new ArrayList<String>();
		hypTable.forEach((k, v) -> existingHyps.add(v));
		if (existingHyps.contains(letter))
		{
			throw new InvalidHypothesisException("Invalid hyp");
		}
		else
		{
			hypTable.put(number, letter);
		}
	}

	/**
	 * Access the hypothesis table for "number"
	 * 
	 * @param nubmer the key for the hyp table
	 * @return current hypothesis for "number"
	 */
	public String getHyp(String number)
	{
		return hypTable.get(number);
	}

	/**
	 * @return is every letter hypothesised to something?
	 */
	public boolean isFull()
	{
		for (int i = 1; i <= 26; i++)
		{
			String num = Integer.toString(i);
			if (hypTable.get(num).equals(NO_HYP))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Copy mappings of 'that' onto 'this'
	 * 
	 * @param that
	 */
	public HypothesisTable copy()
	{
		HypothesisTable newHT = new HypothesisTable();
		for (int i = 1; i <= 26; i++)
		{
			String num = Integer.toString(i);
			newHT.makeHyp(num, hypTable.get(num));
		}
		return newHT;
	}

	/**
	 * print the mappings to console
	 */
	public void print()
	{
		hypTable.forEach((k, v) -> System.out.println(k + "\t= " + v));
	}

}
