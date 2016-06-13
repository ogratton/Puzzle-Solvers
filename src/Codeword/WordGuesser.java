package Codeword;

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
 * 
 * TODO change to array input so next point is easier
 * TODO cope better with _EA_ where _ is the same letter both times (use more symbols like '?' or '@')
 * TODO if we assume it is a codeword then the letters we have must be the only occurrences in the word (__NT cannot be TENT as we have T)
 * 
 * @author Oliver
 *
 */
public class WordGuesser
{

	private ArrayList<String> dictionary;
	private BufferedReader br;
	private String word = "";

	private WordGuesser(String path)
	{
		readDictionary(path);
	}

	public ArrayList<String> guess(String[] clue)
	{
		// add all x letter long words to temp dictionary
		ArrayList<String> guesses = allWordsOfLength(clue.length);
		for (int i = guesses.size()-1; i > -1; i--)
		{
			if (!matches(guesses.get(i), clue))
			{
				guesses.remove(i);
			}
			else
			{
				System.out.println(guesses.get(i));
			}
		}
		

		return guesses;
	}
	
	private boolean matches(String word, String[] clue)
	{		
		word = word.toLowerCase();
		clue = stringArrayToLowerCase(clue);
		
		if (word.length() != clue.length)
		{
			System.out.println("length mismatch");
			return false;
		}
		
		for (int i = 0; i < clue.length; i++)
		{
			if (clue[i] != "_") // TODO expand to be any number from 1-26
			{
				if (!clue[i].equals( String.valueOf(word.charAt(i))) )
				{
					return false;
				}
			}
		}
		
		return true;
	}

	private String[] stringArrayToLowerCase(String[] clueString)
	{
		for (int i = 0; i < clueString.length; i++)
		{
			clueString[i] = clueString[i].toLowerCase();
		}
		return clueString;
	}

	private ArrayList<String> allWordsOfLength(int x)
	{
		ArrayList<String> excl = new ArrayList<String>();
		
		for (int i = 0; i < dictionary.size(); i++)
		{	
			if (dictionary.get(i).length() == x)
			{
				excl.add(dictionary.get(i));
			}
		}
		return excl;
	}
	
	/**
	 * Read the dictionary file and put all the words in an array list
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

	public static void main(String[] args)
	{
		WordGuesser wg = new WordGuesser("dict/super.txt");
		wg.guess(new String[]{"i","m","a","_","o"});

	}

}
