package griddlerOld;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Observer;
import java.util.Observable;
import java.awt.GridLayout;

public class GridView extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	private GriddlerModel model;
	private JButton[][] grid;

	private int gw;
	private int gh;

	public GridView(GriddlerModel model, int gw, int gh)
	{
		super();

		this.model = model;
		this.gw = gw;
		this.gh = gh;
		grid = new JButton[gw][gh];
		setLayout(new GridLayout(gh, gw));

		this.label();
	}

	public void label()
	{
		for (int i = 0; i < gh; i++)
		{
			for (int j = 0; j < gw; j++)
			{
				grid[j][i] = new JButton("");
				if (model.get(j, i) != Griddler.BLANK)
				{
					if (model.get(j, i) != Griddler.NULL)
					{
						grid[j][i].setText("" + model.get(j, i));
					}
					grid[j][i].setEnabled(false);
				}
				add(grid[j][i]);
			}
		}
	}

	public void update(Observable obs, Object obj)
	{
		for (int i = 0; i < gh; i++)
		{
			for (int j = 0; j < gw; j++)
			{
				if (model.get(j, i) == Griddler.FILL)
				{
					grid[j][i].setText("O");
					//grid[j][i].setEnabled(false);
				}
				if (model.get(j, i) == Griddler.NOT)
				{
					grid[j][i].setText("X");
					//grid[j][i].setEnabled(false;)
				}
			}
		}

		repaint();
		System.out.println("update");
	}
}
