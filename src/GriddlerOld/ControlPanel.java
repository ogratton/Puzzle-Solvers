package GriddlerOld;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Contains all the buttons that will be on screen
	 * @param model MineModel object
	 */
	public ControlPanel(GriddlerModel model)
	{
		super();
		
		JButton solve = new JButton("Solve");
		solve.addActionListener(e -> model.solve());
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> System.exit(0));
		
		add(solve);
		add(exit);
	}
}