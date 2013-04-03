package dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JTable;

public class Grid extends JTable {
	static int DefaultTableSize = 10;
	int poop=0;//WTF?
	Stack<Grid> history= new Stack<Grid>();
	Stack<Grid> future= new Stack<Grid>();
	public Grid(){
		/**********************
		 * Default grid size 10
		 * *********************/
		this(DefaultTableSize, DefaultTableSize);
		
	}
	public Grid(int x, int y) {
		super(x,y);
		 for (int i = 0; i < this.getRowCount(); i++) {
	        	for (int j = 0; j < this.getColumnCount(); j++) {  
	        		Cell tempCell = new Cell();
	        		this.setValueAt(tempCell, i, j);
	        		
	        	}
		 }
	}
	
	public void addRow(){
		
	}
	
	public void addCol(){
		
	}
	
	public void insertCell(Cell myCell, boolean isFormatted, char formatType){
		myCell.setIsFormatted(isFormatted);
		myCell.setFormatType(formatType);
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
	
	public double evaluteCell(Cell iCell){
		double results = 0.0;
		
		if(iCell.isPrimitive())
		{
			return iCell.getValue();
		}
		else
		{
			if(iCell.isFormatted()) {
				String format = ":" + iCell.getFormatType();
				String tmpFormula = iCell.getFormula().replace(format , ""); //remove the formatting from the formula
				results = numericInput(alphanumericInput(tmpFormula)); //evaluate without format
			}
			else {
				
				String tmpFormula = iCell.getFormula();
				
				if(iCell.getFormula().contains(":I")) {
					iCell.setIsFormatted(true);
					iCell.setFormatType('I');
					tmpFormula = tmpFormula.replace(":I", "");
				}
				else if(iCell.getFormula().contains(":M")) {
					iCell.setIsFormatted(true);
					iCell.setFormatType('M');
					tmpFormula = tmpFormula.replace(":M", "");
				}
				else if(iCell.getFormula().contains(":S")) {
					iCell.setIsFormatted(true);
					iCell.setFormatType('S');
					tmpFormula = tmpFormula.replace(":S", "");
				}
				
				results = numericInput(alphanumericInput(tmpFormula));
			}
			
			iCell.setValue(results);
			
			return results;
		}
		/*
		 * If Cell contains a primitive value return the double. 
		 * Elseif Cell contains a formual, evaluate the formula and retrun
		 * the evaluated double
		 * */
		/*
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
		*/
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
	public double getValue(int x, int y) {
		Cell tempCell = getCell(x, y);
		return tempCell.getValue();
	}
	public void insertValue(String formula, int x, int y){
		Cell tempCell = getCell(x, y);
		tempCell.setValue(formula);
		this.setValueAt(tempCell, x, y);
	}
	
	public void sortGrid(int col, boolean decending){
		// TODO
		/* Some sorting logic should go here. */
	}
    public int getCellRow (String cellName) {
    	
    	int row = 0;
    	String[] tempArray = new String[2];
    	
    	tempArray = cellName.split("[A-J]");
    	row = Integer.parseInt(tempArray[1]) - 1;
    	
    	return row;
    }
    
    //retrieves the cell row index from the cell name i.e. A1 => column index is 0 (65 - 65); ASCII(A) = 65
    public int getCellColumn (String cellName) {
    	
    	int column = 0;
    	
    	column = ( ((int) cellName.charAt(0)) - 65 );
    	
    	return column;
    }
	public double numericInput(String expression){
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");

		try {
			return (Double) engine.eval(expression); //evaluate the arithmetic expression
			
		} catch (ScriptException e) {
		// TODO Auto-generated catch block
			System.out.println("The arithmetic equation is not valid");
			e.printStackTrace();
		}//end catch
		return -1;
	}
	public String alphanumericInput(String equation){
		//check if there are cell names after the "=" sign i.e check for C4 & F7 in "A1 = C4 + 5 - F7"
		Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
		Matcher myMatch = MY_PATTERN.matcher(equation);
		                		
		String otherCells = "";
		String newEquation = equation;                 	
		
		//every time you find a cell name i.e A1, retrieve its index by using getCellRow & getCellColumn
		//if you find C4 its index is row = 4 & column = 3
		//once you find the index of the cell, retrieve its value and replace the name of the cell by its value
		//in the newEquation string i.e if newEquation = "B1 + 4 + C2" and B1=7, C2=9; then newEquation becomes "7 + 4 + 9"
		while(myMatch.find()) {
			otherCells = myMatch.group();                			
			int row = getCellRow(otherCells);
			int column = getCellColumn(otherCells);
			Cell iCell = (Cell) this.getValueAt(row, column);
			double cellValue = iCell.getValue();

			newEquation = newEquation.replace(otherCells, Double.toString(cellValue));
			
		}  
		return newEquation;
	}
	
	public void addToHistory(){//Saves the current grid into history stack
		Grid temp = new Grid(this.getRowCount(),this.getColumnCount());
		for(int x=0;x<this.getRowCount();x++)
			for(int y=0;y<this.getColumnCount();y++)
				temp.setValueAt(this.getCell(x, y), x, y);
		history.push(temp);		
	}
	
	public void undo(){
		if(history.size()==1){}
		else{
			future.push(history.pop());//this will go to redo stack
			Grid temp = history.peek();
			for(int x=0;x<this.getRowCount();x++)
				for(int y=0;y<this.getColumnCount();y++)
					this.setValueAt(temp.getCell(x, y), x, y);	
		}
	}
	
	public void clearFuture(){
		future.removeAllElements();
	}
	
	public void clearHistory(){
		history.removeAllElements();
	}
	
	public void redo(){
		if(future.empty()){}
		else{
			Grid temp = future.pop();
			history.push(temp);
			for(int x=0;x<this.getRowCount();x++)
				for(int y=0;y<this.getColumnCount();y++)
					this.setValueAt(temp.getCell(x, y), x, y);	
		}
	}
	
	public void clearGrid(){
		Cell tempCell = new Cell();
		for (int i = 0; i < this.getRowCount(); i++)
        	for (int j = 0; j < this.getColumnCount(); j++)  
        		this.setValueAt(tempCell, i, j);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grid other = (Grid) obj;
		if (other.getRowCount() != this.getRowCount() || other.getColumnCount() != this.getColumnCount())
			return false;
		for (int i=0; i<this.getRowCount(); ++i){
			for (int j=0; j<this.getColumnCount(); ++j){
				if (!this.getValueAt(i, j).equals(other.getValueAt(i, j)))
					return false;
			}
		}
		return true;
	}
	

}