public class Cell {
    
    //Attributes
    private double value;
    private String formula;
    private int row;
    private int column;
    //constructors
    public Cell() {
        value=0.0;
        formula="";
    }
    public Cell(double value, int row, int column) {
    	this.value = value;
    	this.row = row;
    	this.column = column; 
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
    public Cell(double value, String formula) {
        super();
        this.value = value;
        this.formula = formula;
    }
    
    public void validateInput(Object o) {
    	//Need code here to validate the input
    	/*if (o instanceof Double)
    		value = (Double) o;
    	if (o instanceof String)
    		formula = o.toString();*/
    	//System.out.println("Value: " + value + "\nFormula: " + formula);
    	return;
    }
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public void setFormula(String equation) {
    	formula = equation;
    }
    
    public String getFormula() {
        return formula;
    }
    
    public boolean hasFormula()
    {
        if(formula.equals(""))
            return false;
        return true;
    }
    
    public boolean hasValue()
    {
        if(value==0.0)
            return false;
        return true;
    }
    @Override
    public String toString() {
        /*This method should determine whether or not there is a formula associated with the given cell
         * If there is a formula, determine the value
         * Then set the "value" and output it here
         */
    	//return "Cell [value=" + value + ", formula=" + formula + "]";
    	return ""+value;
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
