package GriddlerOld;
import javax.swing.JFrame;

public class GriddlerGUI {

	public static void main(String[] args)
	{
		//User inputs:
		// row = numbers down the sides (top to bottom, left to right)
		// col = numbers along the top (left to right, top to bottom)
		int[][] row = new int[][]{ {0,0,0,15},{0,0,10,2},{0,1,7,1},{0,1,1,1},{0,0,0,1},{0,1,3,1},{0,1,5,2},{0,2,6,3},{0,3,5,3},{0,0,4,3},{6,1,3,2},{0,6,1,5},{0,0,5,4},{0,0,0,15},{0,0,0,15} };
		int[][] col = new int[][]{ {0,0,0,15},{0,0,2,8}, {0,0,3,7},{0,0,3,6},{0,3,2,5},{3,3,2,3},{0,3,3,2},{3,4,2,2},{0,3,4,2},{2,3,2,2},{0,1,2,2},{0,0,1,6},{1,1,3,4},{0,0,2,9}, {4,4,1,3}  };
		
		Griddler g = new Griddler(row, col);
		GriddlerComponent comp = new GriddlerComponent(g);
		
		JFrame frame = new JFrame("Griddler");
		frame.setSize(1000,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(comp);
		
		frame.setVisible(true);

	}

}

/* PUZZLE BANK:
 * Heart:
int[][] row = new int[][]{ {1,1},{0,5},{0,5},{0,3},{0,1} };
int[][] col = new int[][]{ {2},  {4},  {4},  {4},  {2} };
 * House:
int[][] row = new int[][]{ {0,0,0,1},{0,0,0,3},{0,0,2,2},{0,0,2,2},{0,0,2,2},{0,0,1,1},{1,2,2,1},{1,2,2,1},{0,1,2,1},{0,0,0,9} };
int[][] col = new int[][]{ {0,0,6},  {0,2,1},  {0,2,4},  {0,2,4},  {0,2,1},  {2,2,1},  {2,2,1},  {0,2,1},  {0,0,6}             };
 * Inverse Chicken
int[][] row = new int[][]{ {0,0,0,15},{0,0,10,2},{0,1,7,1},{0,1,1,1},{0,0,0,1},{0,1,3,1},{0,1,5,2},{0,2,6,3},{0,3,5,3},{0,0,4,3},{6,1,3,2},{0,6,1,5},{0,0,5,4},{0,0,0,15},{0,0,0,15} };
int[][] col = new int[][]{ {0,0,0,15},{0,0,2,8}, {0,0,3,7},{0,0,3,6},{0,3,2,5},{3,3,2,3},{0,3,3,2},{3,4,2,2},{0,3,4,2},{2,3,2,2},{0,1,2,2},{0,0,1,6},{1,1,3,4},{0,0,2,9}, {4,4,1,3}  };
*/
