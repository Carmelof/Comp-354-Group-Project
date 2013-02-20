package dev;
public class Cell {
    
    //Attributes
    private double value;
    private String formula;
    private int row;
    private int column;
    boolean isPrimitive = true;
    //constructors
    public Cell() {
    	isPrimitive = true;
    	newCell(0.0, "", -1, -1);
    }
    public Cell(double value, int row, int column) {
    	isPrimitive = true;
    	newCell(value, "", row, column);
    }
    public Cell(String Formula, int row, int column){
    	isPrimitive = false;
    	newCell(1.0, Formula, row, column);
    }
    
    private void newCell(double value, String Formula, int row, int column){
    	/*Assumes that the formula is already validated*/
    	this.value = value;
    	this.row = row;
    	this.column = column; 
    	this.formula = Formula;
    }
    public int getRow() {
    	return row;
    }
    public int getColumn() {
    	return column;
    }
    public void setRow(int i) {
    	row = i;
    }
    public void setColumn(int i) {
    	column = i; 
    }
    
    public double getValue() {
        return value;
    }

    private void setPrimitive(double value) {
        this.value = value;
    }
    
    private void setFormula(String equation) {
    	isPrimitive = false;
    	formula = equation;
    }
    
    public String getFormula() {
        return formula;
    }
    public void setValue(String equation)
    {
    	setFormula(equation);
    }
    public void setValue(double primitive)
    {
    	setPrimitive(primitive);
    }
    
    public boolean hasFormula()
    {
        if(isPrimitive)
            return false;
        return true;
    }
    
    public boolean hasValue()
    {
        if(!isPrimitive)
            return false;
        return true;
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
}
