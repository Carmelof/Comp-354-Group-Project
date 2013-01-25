public class Cell {
    
    //Attributes
    private double value;
    private String formula;
    
    //constructors
    public Cell() {
        value=0.0;
        formula="";
        
    }
    public Cell(float value, String formula) {
        super();
        this.value = value;
        this.formula = formula;
    }
    

    public double getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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
        return "Cell [value=" + value + ", formula=" + formula + "]";
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
