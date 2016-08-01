package codeword;

import java.util.ArrayList;

/**
 * The immutable data structure in which the solving shall take place
 * 
 * @author Oliver
 *
 */
public class Solver
{
	private HypothesisTable hypTable = new HypothesisTable();
	private WordGuessTable guessTable = new WordGuessTable();
	private ArrayList<String[]> words;
	private String[] word;

	private WordGuesser wg = new WordGuesser("dict/super.txt");

	public Solver(HypothesisTable ht, WordGuessTable wgt, ArrayList<String[]> ws)
	{
		hypTable = ht;
		guessTable = wgt;
		words = ws;
		//word = w; // it should work out the word for itself
	}

	public void apply()
	{
		// TODO
		// apply the hypothesis of "word"
		// refresh the guess table with the new hyp table
	}

	public void genGhild()
	{
		// TODO
		// for the first guess in the most guessable word
		// make a child Solver with that word
		// (I'm not doing all of them at once for efficiency reasons as this will be a depth first search
		// so generating all the children is a waste of time)
	}

}
