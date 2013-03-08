package dev;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Cell {
	
	/*************************************************************
	 * This class is done.  Kcameron
	/*********************************************************** */
    
    //Attributes
    private double value;
    private String formula;
    private int row;
    private int column;
    boolean isPrimitive = true;
    boolean isFormatted = false;
    char formatType = ' ';
    //constructors
    public Cell() {
    	isPrimitive = true;
    	newCell(0.0, "", -1, -1, false, ' ');
    }
    public Cell(double value, int row, int column, boolean isFormatted, char formatType) {
    	isPrimitive = true;
    	newCell(value, "", row, column, isFormatted, formatType);
    }
    public Cell(String Formula, int row, int column, boolean isFormatted, char formatType){
    	isPrimitive = false;
    	newCell(0.0, Formula, row, column, isFormatted, formatType);
    }
    
    private void newCell(double value, String Formula, int row, int column, boolean isFormatted, char formatType){
    	/*Assumes that the formula is already validated*/
    	this.value = value;
    	this.row = row;
    	this.column = column; 
    	this.formula = Formula;
    	this.isFormatted = isFormatted;
    	this.formatType = formatType;
    }
    public int getRow() {
    	return row;
    }
    public int getColumn() {
    	return column;
    }
    public boolean isFormatted() {
    	return isFormatted;
    }
    public char getFormatType() {
    	return formatType;
    }
    public void setRow(int i) {
    	row = i;
    }
    public void setColumn(int i) {
    	column = i; 
    }
    public void setIsFormatted(boolean formatted) {
    	isFormatted = formatted;
    }
    public void setFormatType(char format) {
    	formatType = format;
    }
    
/******************************************************************
* Get Overloads for the values
******************************************************************/
    
    public double getValue() {
        return value;
    }
    public String getFormula() {
        return formula;
    }
    
/******************************************************************
 * Set overload
******************************************************************/
    
    private void setPrimitive(double value) {
    	//this.formula = "";
        this.value = value;
    }
    
    private void setFormula(String equation) {
    	
    	if (equation=="")
    	{
    		isPrimitive=true;
    		value = 0;
    	}
    	else{
    	this.isPrimitive = false;
    	this.formula = equation;
    	}
    }
        
    /*setValue Overloads for String and double*/
    public void setValue(String equation)
    {
    	isPrimitive = false;
    	setFormula(equation);
    }
    public void setValue(double primitive)
    {
    	isPrimitive = true;
    	setPrimitive(primitive);
    }
    
    public boolean isPrimitive()
    {
        return isPrimitive;
    }

    /*Functions used for Test cases*/
    public boolean hasFormula()
    {
    	return !isPrimitive;
    }
    public boolean hasValue()
    {
    	return isPrimitive;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (formula == null) {
            if (other.formula != null)
                return false;
        } else if (!formula.equals(other.formula))
            return false;
        if (Double.doubleToLongBits(value) != Double
                .doubleToLongBits(other.value))
            return false;
        return true;
    } 
    @Override
    public String toString()
    {
    	if(formatType == 'I') {
    		int i = (int) value;
    		return Integer.toString(i);
    	}    		
    	else if(formatType == 'M') {
    		NumberFormat formatter = NumberFormat.getCurrencyInstance();
    		return formatter.format(value);
    	}
    	else if(formatType == 'S') {
    		DecimalFormat df = new DecimalFormat("0.#####E0");    		
    		return df.format(value);
    	}
    	else
    		return Double.toString(value);
    }
}
