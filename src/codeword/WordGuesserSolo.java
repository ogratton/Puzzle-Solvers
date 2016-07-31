package codeword;

import java.util.ArrayList;

public class WordGuesserSolo
{
	
	private static HypothesisTable hypTable = new HypothesisTable();

	public static void main(String[] args)
	{
		WordGuesser wg = new WordGuesser("dict/super.txt");
		//		ArrayList<String> guesses = wg.guess(new String[]{"18","3","A","17","7","7","19","17","R"});

		hypTable.makeHyp("26", "k");
//		ArrayList<String> guesses = wg.guess(new String[] { "10", "15", "20", "6", "1", "5", "8", "2", "10", "13", "1" }, hypTable);
		ArrayList<String> guesses = wg.guess(new String[] { "20","17","8","3","3","20","6","8","11","20","2","25","21" }, hypTable);

		for (int i = 0; i < guesses.size(); i++)
		{
			System.out.println(guesses.get(i));
		}

	}

}
