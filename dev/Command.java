/********************************************** 
 * 											  *
 *           Author: Simone Ovilma  		  *	
 * 											  *			
 **********************************************/

package dev;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Command {
	
	/*************************************
	 * Attributes						  
	 *************************************/
	private String command;
	private final String START_PATTERN = "^[A-Ja-j]\\d{1,2}=.+$";			
	private final String NUMERIC_PATTERN = "^[A-Ja-j]\\d{1,2}=[^A-Za-z=]*$";			
	private final String ALPHANUM_PATTERN = "^.*[A-Ja-j]\\d{1,2}.*$";	
	
	
	/*************************************
	 * Constructors						 
	 *************************************/
	public Command() {		
		command = "";	
	}
	
	public Command(String inputStr) {
		command = inputStr.replaceAll("\\s", "").toUpperCase();
	}
	
	
	/*************************************
	 * Public methods						 
	 *************************************/
	public void setCommand(String command) {
		this.command = command.replaceAll("\\s", "").toUpperCase();		
	}
	
	public boolean isEquation() {
		if(command.matches(START_PATTERN)) {
			if(isNumeric() || isAlphaNumeric())
				return true;
			else
				return false;
		}		
		return false;
	}
	
	public boolean isValid() {		
		if(isEquation())
			return true;
		else {
			if(isLoad() || isSave() || isQuit())
				return true;
			else
				return false;
		}		
	}
	
	public void executeCommand(Grid grid) {		
		if(isValid()) {
			
			if(isEquation()) {				
				int x = getCellRow();
				int y = getCellColumn();				
				Cell cell;
			
				if(isNumeric()) {
					double value = calculateNumericFormula();
					cell = new Cell(value, x, y);
				}			
				else {					
					String formula = getFormula();
					cell = new Cell(formula, x, y);
				}
				
				grid.insertCell(cell);
				
			}
			else {
				
				if(isLoad()) {
					//call load function here and/or in the GUI File menu					
				}
				else if(isSave()) {
					//call save function here and/or in the GUI File menu
				}
				else {
					//call quit function here and/or in the GUI File menu 												
				}	
			}			
		}		
		else {
			System.out.println("The command is not valid!");
		}
	}
	
	
	/*************************************
	 * Private methods						 
	 *************************************/
	private boolean isNumeric() {
		return command.matches(NUMERIC_PATTERN);
	}
	
	private boolean isAlphaNumeric() {
		
		return getFormula().matches(ALPHANUM_PATTERN);
	}
	
	private boolean isLoad() {
		return command.equalsIgnoreCase("load");
	}
	
	private boolean isSave() {
		return command.equalsIgnoreCase("save");
	}
	
	private boolean isQuit() {
		return command.equalsIgnoreCase("quit"); 
	}
					
	private String getCellName() {		
		String[] splitCommand = new String[2];
		splitCommand = command.split("=");
    	String cellName = splitCommand[0];
    	
    	return cellName;
	}

	private String getFormula() {		
		String[] splitCommand = new String[2];
		splitCommand = command.split("=");
    	String formula = splitCommand[1];
    	
    	return formula;				
	}
	
	private int getCellRow() {		
		int row = 0;
		String[] tmp = new String[2];
		String cellName = getCellName();
		tmp = cellName.split("[A-J]");
		row = Integer.parseInt(tmp[1]) - 1;
		
		return row;
	}
	
	private int getCellColumn() {		
		int column = 0;
		String cellName = getCellName();
    	column = ( ((int) cellName.charAt(0)) - 65 );
		
		return column;
	}
	
	private double calculateNumericFormula() {		
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
	    double value = 0.0;
	    
	    try {
			value = (Double) (engine.eval(getFormula()));			
		} 	    
	    catch (ScriptException e) {		
			e.printStackTrace();
		}
	    
		return value;
	}		
}
