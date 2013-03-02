package dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public void insertCell(Cell){
		
		
		
	};
	
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
		
		
		// public String getNumEquation(String pattern, String equation, JTable table) {
		    	
			 Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
			
				Matcher myMatch = MY_PATTERN.matcher(equation);
				String numEquation = equation;
				String otherCells = "";
				double cellValue = 0;
				
				while(myMatch.find()) {
					
					otherCells = myMatch.group();
					
					cellValue = (double) table.getValueAt(getCellRow(otherCells), getCellColumn(otherCells));			                			
					numEquation = numEquation.replace(otherCells, Double.toString(cellValue));
				}
				myMatch.reset();
		    	return numEquation;
		    }
		return results;
		
	}
	
	/*
	 * Should really use the generic object for these overloads
	 * meh.
	 * */
	public void insertValue(double value, int x, int y){
		Cell tempCell = getCell(x, y);
		tempCell.setValue(value);
		TGrid.setValueAt(tempCell, x, y);
	}
	public void insertValue(String formula, int x, int y){
		Cell tempCell = getCell(x, y);
		tempCell.setValue(formula);
		TGrid.setValueAt(tempCell, x, y);
	}
	
	public void sortGrid(int col, boolean decending){
		/* Some sorting logic should go here. */
	}
	
	

}