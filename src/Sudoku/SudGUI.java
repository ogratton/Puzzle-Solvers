package Sudoku;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class SudGUI extends JFrame
{
	private int GRID_SIZE = 9;
	private JTextField[][] tfCells;
	public static final int CELL_SIZE = 60; // Cell width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = new Color(200,200,200);
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	
	public SudGUI(String filename)
	{
		SudokuSolver sud = new SudokuSolver(filename);
		sud.solve(); // TODO replace with button
		SudokuCell[][] grid = sud.getGrid();
		GRID_SIZE = grid.length;
		tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
		
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				tfCells[i][j] = new JTextField();
				cp.add(tfCells[i][j]);
				String value = grid[i][j].getValue() == 0 ? " " : grid[i][j].toString();
				tfCells[i][j].setText(value);
				if (grid[i][j].getValue() == 0)
				{
					tfCells[i][j].setEditable(true);
					tfCells[i][j].setBackground(OPEN_CELL_BGCOLOR);
				}
				else
				{
					tfCells[i][j].setEditable(false);
				}
				tfCells[i][j].setHorizontalAlignment(JTextField.CENTER);
				tfCells[i][j].setFont(FONT_NUMBERS);
			}
		}
		
		
		
		
		cp.setPreferredSize(new Dimension(CELL_SIZE*GRID_SIZE, CELL_SIZE*GRID_SIZE));
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window closing
		setTitle("Sudoku");
		setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		new SudGUI("sudoku/6.csv");
	}

}
