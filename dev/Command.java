package dev;

public class Command {

	private String command;	
	private String startPattern = "^[A-Ja-j]\\d{1,2}=.+$";			
	private String numericPattern = "^[A-Ja-j]\\d{1,2}=[^A-Za-z=]*$";			
	private String alphaNumPattern = "^.*[A-J]\\d{1,2}.*$";
	private String cellName;
	private String formula;
		
	public Command() {		
		command = "";		
	}
	
	public Command(String query) {
		command = query;
	}
	
	public void trim() {
		command.replaceAll("\\s", "");
	}
	
	public void toUpperCase() {
		command.toUpperCase();
	}
	
	public boolean isNumeric() {
		return command.matches(numericPattern);
	}
	
	public boolean isAlphaNumeric() {
		return command.matches(alphaNumPattern);
	}
	
	public boolean isLoad() {
		return command.equals("LOAD");
	}
	
	public boolean isSave() {
		return command.equals("SAVE");
	}
	
	public boolean isQuit() {
		return command.equals("QUIT"); 
	}
	
	public boolean isValid() {
		
		if(command.matches(startPattern)) {
			if(isNumeric() || isAlphaNumeric())
				return true;
			else
				return false;
		}
		else {
			if(isLoad() || isSave() || isQuit())
				return true;
			else
				return false;
		}
		
	}
	
	public String getCellName() {
		
		if(isNumeric() || isAlphaNumeric()) {
			String[] splitCommand = new String[2];
			splitCommand = command.split("=");
	    	cellName = splitCommand[0];
	    	
	    	return cellName;
		}
		
		else
			return "Cannot get cell name, the command is not valid.";
	}
	
	public String getCellName(int row, int column) {
    	
    	if(row >= 0 && column <= 9)
    		return (char)(65 + column) + "" + (row + 1);
    	else
    		return "The index is out of bound";
    }
	
	public String getFormula() {
		if(isNumeric() || isAlphaNumeric()) {
			String[] splitCommand = new String[2];
			splitCommand = command.split("=");
	    	formula = splitCommand[1];
	    	
	    	return formula;
		}
		else
			return "Cannot get formula, the command is not valid.";			
	}
	
	public int getCellRow() {
		int row = 0;
		String[] tmp = new String[2];
		cellName = getCellName();
		tmp = cellName.split("[A-J]");
		row = Integer.parseInt(tmp[1]) - 1;
		
		return row;
	}
	
	public int getCellColumn() {
		int column = 0;
		cellName = getCellName();
    	column = ( ((int) cellName.charAt(0)) - 65 );
		
		return column;
	}
	
	
	public int getTypeOfPattern() {
		return 0;
	}
}
