package codeword;

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
	private String noHyp = "_"; // the string to indicate no hypothesis

	public HypothesisTable()
	{
		// set up the table with 26 entries all with the noHyp symbol
		for (int i = 1; i <= 26; i++)
		{
			hypTable.put(Integer.toString(i), noHyp);
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
	 * Access the hypothesis table for "number"
	 * @param nubmer the key for the hyp table
	 * @return current hypothesis for "number"
	 */
	public String getHyp(String number)
	{
		return hypTable.get(number);
	}
	
	/**
	 * Copy mappings of 'that' onto 'this'
	 * @param that
	 */
	public void copy(HypothesisTable that)
	{
		for (int i = 1; i <= 26; i++)
		{
			String num = Integer.toString(i);
			hypTable.put(num, that.getHyp(num));
		}
	}
	
}
