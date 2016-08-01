package codeword;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A map of coded words to their possibilities in a codeword
 * @author Oliver
 *
 */
public class WordGuessTable
{
	private ConcurrentMap<String[], ArrayList<String>> guessTable = new ConcurrentHashMap<String[], ArrayList<String>>();
	
	/**
	 * Add a "word" and a list of the words it could be
	 * @param word coded word made up of numbers
	 * @param guesses list of words it could be
	 */
	public void addWord(String[] word, ArrayList<String> guesses)
	{
		guessTable.put(word, guesses);
	}
	
	/**
	 * Retrieve the guess list for a "word"
	 * @param word coded word made up of numbers
	 * @return the words it could be
	 */
	public ArrayList<String> getWord(String[] word)
	{
		return guessTable.get(word);
	}
	
	/**
	 * Remove the first guess for a word (for when it has been tested)
	 * @param word coded word made up of numbers
	 */
	public WordGuessTable remHyp(String[] word)
	{
		ArrayList<String> guesses = guessTable.get(word);
		guesses.remove(0);
		if (guesses.isEmpty())
		{
			guessTable.remove(word);
		}
		guessTable.put(word, guesses);
		return this;
	}
	
	/**
	 * return a clone of itself
	 * TODO hopefully the clone won't sync changes with this. that could break stuff
	 */
	public WordGuessTable clone()
	{
		return this;
	}
}
