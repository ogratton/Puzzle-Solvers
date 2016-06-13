package auxil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads a Comma Separated Value file and returns it as an ArrayList of lines
 * 
 * @author Oliver
 */
public class CSVReader
{
	private BufferedReader br;
	private String line = "";
	private String cvsSplitBy = ",";

	public ArrayList<String[]> readContents(String filename)
	{
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try
		{
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null)
			{

				// use comma as separator
				String[] items = line.split(cvsSplitBy);
				lines.add(items);

			}
			return lines;
		}
		catch (IOException e)
		{
			System.out.println("Couldn't find file " + filename);
			return null;
		}
	}
}
