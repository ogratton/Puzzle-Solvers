package griddlerOld;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GriddlerComponent extends JPanel
{
	private static final long serialVersionUID = 1L;

	public GriddlerComponent(Griddler g)
	{
		super();
		
		// Make Model
		GriddlerModel model = new GriddlerModel(g);
		
		// Make Views
		GridView grid = new GridView(model, model.getWidth(), model.getHeight());
		ControlPanel controls = new ControlPanel(model);
		
		// Make Model Observe View
		model.addObserver(grid);
		
		setLayout(new BorderLayout());
		
		add(grid, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
		
	}
}
