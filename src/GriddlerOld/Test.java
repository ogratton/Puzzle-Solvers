package GriddlerOld;
public class Test {

	public static void main(String[] args)
	{
		//User inputs:
			// numbers down the sides (top to bottom, left to right)
			// numbers along the top (left to right, top to bottom)
		int[][] row = new int[][]{ {0,0,0,1},{0,0,0,3},{0,0,2,2},{0,0,2,2},{0,0,2,2},{0,0,1,1},{1,2,2,1},{1,2,2,1},{0,1,2,1},{0,0,0,9} };
		int[][] col = new int[][]{ {0,0,6},  {0,2,1},  {0,2,4},  {0,2,4},  {0,2,1},  {2,2,1},  {2,2,1},  {0,2,1},  {0,0,6}             };
		//Procedural		
		Griddler g = new Griddler(row,col);
		
		g.printGrid();
		
		//System.out.println(g.get(1, 2));
		
		g.solve();
		
		g.printGrid();
		
	}

}
