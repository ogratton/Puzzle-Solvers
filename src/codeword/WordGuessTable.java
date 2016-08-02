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
	 * Return a clone of itself
	 */
	public WordGuessTable copy()
	{
		WordGuessTable newWGT = new WordGuessTable();
		guessTable.forEach((k,v) -> newWGT.addWord(k,v));
		return newWGT;
	}
}
