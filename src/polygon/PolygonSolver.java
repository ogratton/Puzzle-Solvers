package polygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PolygonSolver
{
	private final static String FILEPATH = "dict/super.txt"; // "OED" gives a Telegraph reader's efforts, "super" gives a cheater's efforts
	
	// the first item in LETTERS is the centre in the puzzle
//	private final static String[] LETTERS = new String[] { "m", "c", "a", "p", "i", "a", "d", "r", "e" };
//	private final static String[] LETTERS = new String[] { "p", "t", "o", "s", "h", "s", "o", "r", "t" };
//	private final static String[] LETTERS = new String[] { "a", "m", "a", "s", "w", "o", "r", "o", "n" };
//	private final static String[] LETTERS = new String[] { "a", "m", "l", "r", "d", "a", "f", "n" };
//	private final static String[] LETTERS = new String[] { "k", "l", "r", "a", "c", "e", "p", "o", "w" };
	private final static String[] LETTERS = new String[] { "l", "l", "u", "e", "o", "e", "c", "a", "g" };

	// rules on word length
	private final static int SMALL = 4, LARGE = LETTERS.length;

	private static BufferedReader br;
	private static String word = "";
	static ArrayList<String> dictionary;

	private static void readDictionary()
	{
		dictionary = new ArrayList<String>();
		try
		{
			br = new BufferedReader(new FileReader(FILEPATH));
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

	/**
	 * Refine the dictionary
	 * 
	 * @param length target length of the word
	 * @return all words of length between SMALL and LARGE which contain CENTRE
	 */
	private static ArrayList<String> partition()
	{
		ArrayList<String> reduced = new ArrayList<String>();
		for (String word : dictionary)
		{
			if (word.toLowerCase().length() <= LARGE && word.toLowerCase().length() >= SMALL && word.indexOf(LETTERS[0].toLowerCase()) != -1)
			{
				reduced.add(word);
			}
		}
		return reduced;
	}
	
	/**
	 * makes an array list version of LETTERS 
	 * @return array list of the letters needed
	 */
	private static ArrayList<String> copyLetters()
	{
		ArrayList<String> letters = new ArrayList<String>();
		for (int i = 0; i < LETTERS.length; i++)
		{
			letters.add(LETTERS[i].toLowerCase());
		}
		return letters;
	}
	
	/**
	 * Return true if the word contains only the allowed letters
	 * @param word
	 * @return
	 */
	private static boolean wordLegal(String word)
	{
		ArrayList<String> letters = copyLetters();
		
		char[] wordChars = word.toCharArray();
		for (char letter : wordChars)
		{
			if (letters.contains(String.valueOf(letter)))
			{
				// letters can only be used once per word
				letters.remove(String.valueOf(letter));
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		readDictionary();
		ArrayList<String> solutions = new ArrayList<String>();
		ArrayList<String> anagram = new ArrayList<String>();
		ArrayList<String> shortlist = partition();
		for (String word : shortlist)
		{
			if (wordLegal(word))
			{
				solutions.add(word);
				if (word.length() == LARGE)
				{
					anagram.add(word);
				}
			}
		}
		for (String word : solutions)
		{
			System.out.println(word);
		}
		System.out.println("Found "+solutions.size()+" solutions");
		if (!anagram.isEmpty())
		{
			System.out.println("Anagram solutions found:");
			for (String word : anagram)
			{
				System.out.println(word);
			}
		}
		else
		{
			System.out.println("No perfect anagram found :(");
		}

	}
}
