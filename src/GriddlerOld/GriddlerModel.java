package GriddlerOld;
import java.util.Observable;

public class GriddlerModel extends Observable
{
	private Griddler g;
	
	public GriddlerModel(Griddler g)
	{
		this.g = g;
	}
	
	public void drawGrid()
	{
		g.drawGrid();
		System.out.println("drawing grid");
		setChanged();
		notifyObservers();
	}
	
	public void solve()
	{
		g.solve();
		setChanged();
		notifyObservers();
	}
	
	public int get(int x, int y)
	{
		return g.get(x, y);
	}
	
	public int getWidth()
	{
		return g.getWidth();
	}
	
	public int getHeight()
	{
		return g.getHeight();
	}
	
	public void printGrid()
	{
		g.printGrid();
	}
}
