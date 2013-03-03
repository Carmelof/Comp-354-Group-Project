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
	private final String ALPHANUM_PATTERN = "^.*[A-Ja-j]\\d{1,2}.*$";
	
	
	
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
		
	public boolean isValid() {
		return isValidEquation();
	}
			
	
/*******************************************************
 * Private methods						 
 *******************************************************/
	private boolean isAlphaNumeric() {
		
		return (command.matches(ALPHANUM_PATTERN));
	}
	
	/*
	 * if equation = "A1 + 4 - 8 * C3", then replaceCellNamesByValue(equation, 1.0)
	 * returns "1.0 + 4 - 8 * 1.0"
	 */
	private String replaceCellNamesByValue(String equation, double value) {
	
		Pattern MY_PATTERN = Pattern.compile("[A-J]\\d{1,2}");
		Matcher myMatch = MY_PATTERN.matcher(equation);
		String myEquation = equation;
		
		while(myMatch.find()) {
			String cellName = myMatch.group();   
			myEquation = myEquation.replace(cellName, "" + value);			
		}  
		
		return myEquation;
	}
	
	/*
	 * Check if the command is a valid equation by calling engine.eval
	 * if it's alphanumeric i.e. "E3 - 7 + C5" replace E3 and C5 by an arbitrary value
	 * if engine.eval catches an exception, then it means that it wasn't able to evaluate the expression,
	 * therefore the command is invalid
	 */
	private boolean isValidEquation() {
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
	    String numericEquation = command;
	    
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
	}
}
