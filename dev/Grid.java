package dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;

public class Grid extends JTable {
	static int DefaultTableSize = 10;

	public Grid(){
		/**********************
		 * Default grid size 10
		 * *********************/
		this(DefaultTableSize, DefaultTableSize);
		
	}
	public Grid(int x, int y) {
		/*TODO Auto-generated method stub
		 * 
		 * Create a grid (jTable of size X, Y using the Cell class )
		 * */
		super(x,y);
		
		 for (int i = 0; i < this.getRowCount(); i++) {
	        	for (int j = 0; j < this.getColumnCount(); j++) {  
	        		Cell tempCell = new Cell();
	        		this.setValueAt(tempCell, i, j);
	        		
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
	
	public void insertCell(Cell myCell){
		this.setValueAt(myCell, myCell.getRow(), myCell.getColumn());
	}
	
	public Cell getCell(int x, int y)
	{
		Cell tempCell = new Cell();
		/*
		 * return the the Cell at the x,y position
		 * */
		tempCell = (Cell) this.getValueAt(x, y);
		
		return tempCell;
	}
	
	private double evaluteCell(Cell iCell){
		double results = 0.0;
		Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
		/*
		 * If Cell contains a primitive value return the double. 
		 * Elseif Cell contains a formual, evaluate the formula and retrun
		 * the evaluated double
		 * */
		
		if(iCell.isPrimitive())
		{
			return iCell.getValue();
		}
		else
		{
			Matcher myMatch = MY_PATTERN.matcher(iCell.getFormula());
			String otherCells = "";
			Cell nextCell;
			// A1+32+c3-2
			
			while(myMatch.find()) {
				
				otherCells = myMatch.group();
				
				nextCell = (Cell) this.getValueAt(getCellRow(otherCells), getCellColumn(otherCells));			                			
				
				//iCell.getFormula() = iCell.getFormula().replace(otherCells, Double.toString(cellValue));
			}
			myMatch.reset();
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
		this.setValueAt(tempCell, x, y);
	}
	public void insertValue(String formula, int x, int y){
		Cell tempCell = getCell(x, y);
		tempCell.setValue(formula);
		this.setValueAt(tempCell, x, y);
	}
	
	public void sortGrid(int col, boolean decending){
		/* Some sorting logic should go here. */
	}
    private int getCellRow (String cellName) {
    	
    	int row = 0;
    	String[] tempArray = new String[2];
    	
    	tempArray = cellName.split("[A-J]");
    	row = Integer.parseInt(tempArray[1]) - 1;
    	
    	return row;
    }
    
    //retrieves the cell row index from the cell name i.e. A1 => column index is 0 (65 - 65); ASCII(A) = 65
    private int getCellColumn (String cellName) {
    	
    	int column = 0;
    	
    	column = ( ((int) cellName.charAt(0)) - 65 );
    	
    	return column;
    }
	

}