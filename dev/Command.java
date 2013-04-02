/********************************************** 
 * 											  *
 *           Author: Simone Ovilma  		  *	
 * 											  *			
 **********************************************/

package dev;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Command {
	
/*******************************************************
 * Attributes						  
 *******************************************************/
	private String command;				
	private final String ALPHANUM_PATTERN = "^.*(?<!\\w)[A-Ja-j](?!0+[0-9]*)(?![1][1-9]+)(?![10][0-9][0-9]+)(?![1-9][1-9]+)(([1-9]{1})+|(10)).*$";
	//"^.*[A-Ja-j]\\d{1,2}.*$"
	//private final String NOT_IN = "";
	
	
	
/*******************************************************
 * Constructors						 
 *******************************************************/
	public Command() {	
		
		command = "";	
	}
	
	public Command(String inputStr) {
		
		command = inputStr.toUpperCase();		
	}
	
	
/*******************************************************
 * Public methods						 
 *******************************************************/
	public void setCommand(String command) {		
			
		this.command = command.toUpperCase();				
	}
	
	public String getCommand() {
		
		return command;
	}
	
	public void trim() {
		
		command = command.replaceAll("\\s+", " ");
		command = command.replaceAll("^\\s+|\\s+$", "");
	}
	
	public int evaluate(Grid grid, int x, int y, String itself) {
		Cell newCell = new Cell(command, x, y, false, ' ');
		//int typeOfCommand = 3; //invalid input
		if(containsFormat()) {
			String original = command;
			String tmp = command.replaceFirst("^.*(:(I|M|S))$", ""); //replace "formula:I" with "formula"
			
			command = tmp;
			
			if(isValidEquation()) {
				if(isCircular(grid, x, y, itself)) {
					command = original;
					return 2; //circular
				}
				else if(containsDivisionByZero())
					return 4; //division by zero
				else {
					command = original;
					
					if(isIntegerFormat())
						grid.insertCell(newCell, true, 'I');
					else if(isMonetaryFormat())
						grid.insertCell(newCell, true, 'M');
					else if(isScientificFormat())
						grid.insertCell(newCell, true, 'S');
					
					grid.evaluteCell(grid.getCell(x, y));					
	                updateGrid(grid, itself);	                
	                return 0; //formatted equation
				}
			}
			else
				return 3; //invalid input
		}
		else if(isValidEquation()) {
			if(isCircular(grid, x, y, itself))
				return 2; //circular
			else if(containsDivisionByZero())
				return 4; //division by zero
			else {
				grid.insertCell(newCell, false, ' ');					
				grid.evaluteCell(grid.getCell(x, y));					
	            updateGrid(grid, itself);
	            return 1; //regular equation
			}
			
		}
		else {
			return 3; //invalid input
		}
		
	}
	
	
/*******************************************************
 * Private methods	 -- Changed to public for test case use					 
 *******************************************************/
	public boolean isAlphaNumeric() {
		
		return (command.matches(ALPHANUM_PATTERN));
	}
	
	public boolean containsSpecialChars() {
		return command.matches("^(?![K-Z~`!@\\&#\\$\\{\\}])$");
	}
	
	public boolean containsDivisionByZero() {
		return command.matches("^.*(/\\s*0).*$");
	}
	
	public boolean containsFormat(){
		return command.matches("^.*([0-9]:(I|M|S))$");
	}
	
	public boolean isIntegerFormat() {
		return command.contains(":I");
	}
	
	public boolean isMonetaryFormat() {
		return command.contains(":M");
	}
	
	public boolean isScientificFormat() {
		return command.contains(":S");
	}
	
	public int getCellRow(String cellName) {		
		int row = 0;
		String[] tmp = new String[2];
		
		tmp = cellName.split("[A-J]");
		row = Integer.parseInt(tmp[1]) - 1;
		
		return row;
	}
	
	public int getCellColumn(String cellName) {		
		int column = 0;	
		
    	column = ( ((int) cellName.charAt(0)) - 65 );
		
		return column;
	}
	
	/*
	 * if equation = "A1 + 4 - 8 * C3", then replaceCellNamesByValue(equation, 1.0)
	 * returns "1.0 + 4 - 8 * 1.0"
	 */
	public String replaceCellNamesByValue(String equation, double value) {
		
		Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
		Matcher myMatch = MY_PATTERN.matcher(equation);
		String myEquation = equation;
		
		while(myMatch.find()) {
			String cellName = myMatch.group();   
			myEquation = myEquation.replace(cellName, "" + value);			
		}  
		
		return myEquation;
	}
	
	public void updateGrid(Grid grid, String cellName) {
		Cell tmpCell;;
		double tmpValue =  0.0;
		
		for(int i = 0; i < grid.getRowCount(); i++) {
			for(int j = 0; j < grid.getColumnCount(); j++) {
				if(grid.getCell(i, j).getFormula().contains(cellName)) {
					tmpCell = new Cell(grid.getCell(i, j).getFormula(), i, j, grid.getCell(i, j).isFormatted(), grid.getCell(i, j).getFormatType());
					tmpValue = grid.evaluteCell(tmpCell);
					tmpCell.setValue(tmpValue);
					grid.insertCell(tmpCell, tmpCell.isFormatted(), tmpCell.getFormatType());					
				}
			}
		}
	}
	
	/*
	 * Check if the command is a valid equation by calling engine.eval
	 * if it's alphanumeric i.e. "E3 - 7 + C5" replace E3 and C5 by an arbitrary value
	 * if engine.eval catches an exception, then it means that it wasn't able to evaluate the expression,
	 * therefore the command is invalid
	 */
	public boolean isValidEquation() {
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
	    String numericEquation = command;
	    	    
	   /* if(containsSpecialChars())
	    	return false;
	    
	    else {*/
	    	if(isAlphaNumeric())
		    	numericEquation = replaceCellNamesByValue(command, 1.0);
		    
		    
		    try {
				engine.eval(numericEquation);			
			} 	    
		    catch (ScriptException e) {		
				//e.printStackTrace();
		    	return false;
			}
		    		
			return true;	
	   // }
	    			
	}
	
	public boolean isCircular(Grid grid, int x, int y, String itself) {
		if(command.contains(itself))
			return true;
		else {
			Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
			Matcher myMatch = MY_PATTERN.matcher(command);		
			
			while(myMatch.find()) {
				String cellName = myMatch.group();   
				int row = getCellRow(cellName);
				int col = getCellColumn(cellName);
				
				if(grid.getCell(row, col).getFormula().contains(itself))
					return true;				
			}
		}
		return false;
	}
	public boolean isValid() {
		return true;
	}
	public boolean isInRange(){
		return true;
	}
}
