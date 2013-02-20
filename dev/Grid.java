package dev;

import javax.swing.JTable;

public class Grid{
	int DefaultTableSize = 10;
	JTable TGrid;

	public Grid(){
		/**********************
		 * Default grid size 10
		 * *********************/
		MakeGrid(DefaultTableSize, DefaultTableSize);
		
	}
	public Grid(int x, int y){
		MakeGrid(x,y);
	}
	private void MakeGrid(int x, int y) {
		/*TODO Auto-generated method stub
		 * 
		 * Create a grid (jTable of size X, Y using the Cell class )
		 * */
		TGrid = new JTable(x,y);
		
		 for (int i = 0; i < TGrid.getRowCount(); i++) {
	        	for (int j = 0; j < TGrid.getColumnCount(); j++) {  
	        		Cell tempCell = new Cell();
	        		TGrid.setValueAt(tempCell, i, j);
	        	}
		 }
	}
	
	public void addRow(int CurrentRow, boolean Above){
	/*
	 * If Above = True create a new row above CurrentRow, else below
	 * NOTE: You will need to create a new table that is size + 1 and copy
	 * the entries of current table into new table. Will also need some 
	 * logic that will update any cell ref. to thier new values.
	 * 
	 * */
		
	}
	
	public void addCol(int CurrentCol, boolean Left){
		/*
		 * You guessed it.
		 * */
	}
	
	public Cell getCell(int x, int y)
	{
		Cell tempCell = new Cell();
		/*
		 * return the the Cell at the x,y position
		 * */
		tempCell = (Cell) TGrid.getValueAt(x, y);
		
		return tempCell;
	}
	
	private double evaluteCell(Cell iCell){
		double results = 0.0;
		/*
		 * If Cell contains a primitive value return the double. 
		 * Elseif Cell contains a formual, evaluate the formula and retrun
		 * the evaluated double
		 * */
		return results;
	}

}