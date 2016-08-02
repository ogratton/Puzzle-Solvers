package codeword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Given, e.g.
 * _EA_, will return
 * SEAL
 * PEAR
 * GEAR
 * etc.
 * goal functionality: https://codewordsolver.com/
 * 
 * @author Oliver
 *
 */
public class WordGuesser
{
	private HypothesisTable hypTable;
	private ArrayList<String> dictionary;
	private BufferedReader br;
	private String word = "";

	public WordGuesser(String path)
	{
		readDictionary(path);
	}

	public ArrayList<String> guess(String[] clue, HypothesisTable hypTable)
	{
		this.hypTable = hypTable;
		ArrayList<String> guesses = new ArrayList<String>();

		// for every word
		for (int i = 0; i < dictionary.size(); i++)
		{
			String word = dictionary.get(i);
			if (word.length() == clue.length)
			{
				// if the word matches the clue's pattern add it to the guess pile
				if (matches(word, clue))
				{
					guesses.add(word);
				}
			}
		}

		return guesses;
	}

	private boolean matches(String word, String[] clue)
	{
		word = word.toLowerCase();
		clue = stringArrayToLowerCase(clue);

		HypothesisTable htCopy = hypTable.copy(); // copy of the hypTable to which we can make experimental changes

		if (word.length() != clue.length)
		{
			// should never happen
			System.out.println("FAILURE: length mismatch");
			return false;
		}

		for (int i = 0; i < word.length(); i++)
		{
			try
			{
				if (isLetter(clue[i]))
				{
					if (word.substring(i, i + 1).equals(clue[i]))
						continue;
					else
						return false;
				}
				else
				{
					String hyp = htCopy.getHyp(clue[i]);
					// if no hypothesis exists for this number yet
					if (hyp.equals("_"))
					{
						htCopy.makeHyp2(clue[i], word.substring(i, i + 1));
					}
					else
					{
						if (word.substring(i, i + 1).equals(hyp))
							continue;
						else
							return false;
					}
				}
			}
			catch (InvalidHypothesisException e)
			{
				continue;
			}
		}
		return true;
	}

	/**
	 * Returns true if clue bit is a plain letter
	 * Will not happen in codeword solving, only crossword use when WordGuesser
	 * is used on its own
	 * 
	 * @param string
	 * @return
	 */
	private boolean isLetter(String string)
	{
		return string.chars().allMatch(x -> Character.isLetter(x));
	}

	private String[] stringArrayToLowerCase(String[] clueString)
	{
		for (int i = 0; i < clueString.length; i++)
		{
			clueString[i] = clueString[i].toLowerCase();
		}
		return clueString;
	}

	/**
	 * Read the dictionary file and put all the words in an array list
	 * 
	 * @param path filepath of the dictionary
	 */
	private void readDictionary(String path)
	{
		dictionary = new ArrayList<String>();
		try
		{
			br = new BufferedReader(new FileReader(path));
			while ((word = br.readLine()) != null)
			{
				dictionary.add(word.toLowerCase());
			}
		}
		catch (IOException e)
		{
			System.err.println("Dictionary missing :(");
			System.exit(1);
		}
	}

}
