package dev;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;

//import java
//-------------------------------
// Source code acquired from 
// http://stackoverflow.com/questions/8002445/trying-to-create-jtable-with-proper-row-header
//-------------------------------

public class Main {
	main2()
	retrun;
    private JFrame frame = new JFrame("Comp 354-Excel Document");
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private JTable headerTable;
    //private Cell cell[][] = new Cell[10][10];
    //contains the formula for each cell if there are any
    private String[][] formulas = new String[10][10];
    private String[][] cellEntries = new String[10][10];
    private boolean quit;
    private boolean saved;
    Scanner inputCommand;
    
    
	//Equation starts with a cell name followed by "=" i.e "A1="
	String startPattern = "^[A-Ja-j]\\d{1,2}=.+$";
	//Equation with numbers only i.e. (A1= 4.5), (A1= 5 + 6.7 - 8); NOT VALID => (A1= B1), (A1=B1*C4), (A1= B5 - 3.4)
	String numericPattern = "^[A-Ja-j]\\d{1,2}=[^A-Za-z=]*$";
	//Equation contains cell names i.e A1 = B1 + 4 - C1; DOESN'T APPLY TO => A1 = 4.7 + 90 (no cell names after "=");
	String alphaNumPattern = "^.*[A-J]\\d{1,2}.*$"; 
    
    public Main() {
        prepareVars();
        //printTable(table);

        
        //*******************************************************my code (Simone)**************************************************//
    	inputCommand = new Scanner(System.in);
    	String inputStr = "";
    	quit = false;
    	saved = true;
    	String greeting = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n"
    					+ "***SPREADSHEET***"
                        + "\n"
                        + "\nCOMMANDS:"
                        + "\n"
                        + "\ncellname = numeric expression:\t( i.e. A1 = 35 + 4 * (9 / 3 - 4) )"
    					+ "\ncellname = alphanumeric expression\t( i.e. A1 = B1 + 5 + C4 * 6)"
                        + "\ncellname = [constant][operator]cellname:\t"
    					+ "( i.e. A1 = B1; A1 = -4 * B1)"
                        + "\nload:\tLoad the spreadsheet"
                        + "\nsave:\tSave the spreadsheet"
                        + "\nquit:\tQuit the spreadsheet"
    					+ "\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
 
    	System.out.println(greeting);
    	Command cmd = new Command;
    	while(!quit) {
    	    		
    		while(true) {// As long as the input from the console isn't "quit" do...
    			System.out.println("\nEnter a command: ");
    			inputStr = inputCommand.nextLine();
    			
    			
    			//executeCommand(inputStr);
            	
    			if (quit)
    				break;
    		}
    		 //end while(!quit)
    }
    	
    private void executeCommand(String inputStr) {
    	String noSpaceCommand = inputStr.replaceAll("\\s", ""); //take out any whitespace chars
    	noSpaceCommand = noSpaceCommand.toUpperCase(); //put the char in the cell name to uppercase i.e b1 => B1
    	
    	if(noSpaceCommand.matches(startPattern)) {//if the command starts correctly i.e "A1=" continue
    	    
	    	String cellName = getEquationElement(noSpaceCommand, "cellname");
	    	String equation = getEquationElement(noSpaceCommand, "equation");
	    	
	    	//if the equation only contains numbers
    		if(noSpaceCommand.matches(numericPattern)) {
        		numericInput(cellName, equation);
        		storeEntries(cellEntries, cellName, noSpaceCommand);        		
            	printTable(table);            	
        		saved = false;
        	}
    		//if the equation contains cell names
        	else if(equation.matches(alphaNumPattern)) {
        		alphanumericInput(cellName, equation);
        		storeEntries(cellEntries, cellName, noSpaceCommand);
        		printTable(table);
        		saved = false;
        	} else {
        		System.out.println("The command is not valid");
        	}
    	} else {
    		 if(noSpaceCommand.equals("QUIT")) {
    			 frame.setVisible(true);	
    		        frame.toFront();
    			 if (checkSaved()){
	    			 System.out.println("Quitting...");
	                 // if unsaved, prompt to save
	                 System.exit(0);
    			 }
    		 }            		 	
    		 else if(noSpaceCommand.equals("LOAD")) {
    			 frame.setVisible(true);	
    		        frame.toFront();
    			 if (checkSaved()){
	    			 loadFile();
    			 }
    		 }
    		 else if(noSpaceCommand.equals("SAVE")) {
    			 //String[][] saveTest = {{"1", "2"}, {"3", "4"}};
    			 //saveFile(saveTest);
    			 saveFile(cellEntries);
    		 }
    		 else {
    			 System.out.println("The command is not valid!");
    		 }
    	}
	}
    private void saveFile(String[][] entries){
    	System.out.println(
    			"Enter a filename to save to "
    			+ "\n(the .csv extension will be automatically appended.");
    	String filename = inputCommand.nextLine();
    	try {
    		FileWriter fstream = new FileWriter(filename + ".csv");
    		BufferedWriter out = new BufferedWriter(fstream);
    		for (String[] row : entries){
    			String line = "";
    			for (String col : row)
    				line += col + ",";
    			out.write(line + "\n");
    		}
    		out.close();
    	} catch (Exception e) {
    		System.err.println("There was an error trying to write to file:");
    		e.printStackTrace();
    	}
    	System.out.println("File successfully saved as " + filename + ".csv");
    	saved = true;
    }
    private void loadFile(){
		JFileChooser fileopen = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
		fileopen.addChoosableFileFilter(filter);
		int ret = fileopen.showDialog(new JPanel(), "Open file");
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File file = fileopen.getSelectedFile();
	        try {
	            Scanner in = new Scanner(file).useDelimiter(",\n");
	            ArrayList<String[]> cellArray = new ArrayList<String[]>();
	            while (in.hasNext()){
	            	cellArray.add(in.next().split(","));
	            }
	            in.close();
	            assert cellArray.size() > 0;
	            int rows = cellArray.size();
	            int cols = cellArray.get(0).length;
	            cellEntries = new String[rows][cols];
	    		System.out.println("Data successfully loaded from file:\n\t" + file);
	            for (int i = 0; i < cellArray.size(); ++i){
	            	String[] row = cellArray.get(i);
	            	if (i < cellArray.size()-1)
	            		assert row.length == cellArray.get(i+1).length;
	            	for (int j=0; j<row.length; j++){
	            		cellEntries[i][j] = row[j];
	            		
	            		/*if(row[j].equals("0.0"))
	            			System.out.print(row[j] + ",\t");
	            		else {
		            			String cellname = getEquationElement(row[j], "cellname");
		            			String equation = getEquationElement(row[j], "equation");
		            			ScriptEngineManager manager = new ScriptEngineManager();
		            		    ScriptEngine engine = manager.getEngineByName("JavaScript");
		            		    
		            			if(row[j].matches(alphaNumPattern)) {
		            				try {
										System.out.print(engine.eval(equation) + ",\t");
									} catch (ScriptException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		            			}
		            			else {
		            				System.out.print("test,\t");
		            			}
		            			
	            		}*/
	            	}
	            	//System.out.println();
	            }
	        } catch (IOException e) {
	            System.err.println("There was a problem trying to load the file.");
	        } catch (AssertionError e) {
	        	System.err.println("The file could not be loaded because of its "
	        			+ "formatting. It may be corrupted.");
	        }
		}
		restoreEntries(cellEntries);
		printTable(table);
		//model.fireTableDataChanged();
		saved = true;
    }
    
    private boolean checkSaved(){
    	if (saved)
    		return true;
    	int n = JOptionPane.showConfirmDialog(new JFrame(),
    			"You have unsaved changes. Are you sure you want to continue?",
    			"Warning",
    			JOptionPane.WARNING_MESSAGE);
    	if (n==0)
    		return true;
    	return false;
    }
	private void numericInput(String cellName, String equation){
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
		int row = getCellRow(cellName);
		int column = getCellColumn(cellName);
		try {
			Object result = engine.eval(equation); //evaluate the arithmetic expression
<<<<<<< HEAD
			double cellValue = (double) result;
			
=======
			double cellValue = (Double) result;
			//store the value in the correct cell of the JTable
>>>>>>> 99c14796d29e55c871d8cdb31e0f4b0a50353eeb
			table.getModel().setValueAt(cellValue, row, column);
			
			formulas[row][column] = equation;
			updateTable(table, formulas, cellName); //||||||******** ADDED CODE *********||||||//
		} catch (ScriptException e) {
		// TODO Auto-generated catch block
			System.out.println("The arithmetic equation is not valid");
			e.printStackTrace();
		}//end catch
	}
	private void alphanumericInput(String cellName, String equation){
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
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
			double cellValue = (Double) table.getValueAt(row, column);

			newEquation = newEquation.replace(otherCells, Double.toString(cellValue));
		}  		
		try {
			Object result = engine.eval(newEquation);								
			table.getModel().setValueAt(result, getCellRow(cellName), getCellColumn(cellName));
			//set the value of the cell and its formula i.e A1= 4.5 & formula of A1=> "4.5 - B1 * 2 - E5"
			//cell[getCellRow(cellName)][getCellColumn(cellName)].setValue((double) result);
			//cell[getCellRow(cellName)][getCellColumn(cellName)].setFormula(equation);
			//store the formula of the cell in an array i.e formula of A1=> "4.5 - B1 * 2 - E5"
			formulas[getCellRow(cellName)][getCellColumn(cellName)] = equation;
			updateTable(table, formulas, cellName); //||||||******** ADDED CODE *********||||||//
		} catch (ScriptException e) {
		// TODO Auto-generated catch block
			System.out.println("The arithmetic equation is not valid");
			e.printStackTrace();
		}//end catch
	}
	// HERE ON IS GUI ---------------------------------------------------------------------- !!
	private void prepareVars(){
		table = new JTable(10, 10);
        //Fill the table with empty values
        for (int i = 0; i < table.getRowCount(); i++) {
        	for (int j = 0; j < table.getColumnCount(); j++) {        		
        		table.setValueAt(0.0, i, j); //************** NEEDED TO BE CHANGED TO WORK (from "cell" to 0.0 ***********/
        		formulas[i][j] = "";
        		cellEntries[i][j] = "0.0";
        		//cell[i][j].setValue(0.0);
        	}
        }
        sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);
        model = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return table.getRowCount();
            }

            @Override
            public Class<?> getColumnClass(int colNum) {
                switch (colNum) {
                    case 0:
                        return String.class;
                    default:
                        return super.getColumnClass(colNum);
                }
            }
            
        };
        headerTable = new JTable(model);
        for (int i = 0; i < table.getRowCount(); i++) {
            headerTable.setValueAt((i + 1), i, 0);
        }
        headerTable.setShowGrid(true);
        headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        headerTable.setPreferredScrollableViewportSize(new Dimension(50, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                boolean selected = table.getSelectionModel().isSelectedIndex(row);
                Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, -2);
                ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
                if (selected) {
                    component.setFont(component.getFont().deriveFont(Font.BOLD));
                    component.setForeground(Color.red);
                } else {
                    component.setFont(component.getFont().deriveFont(Font.PLAIN));
                }
                return component;
            }
        });
        
        table.getRowSorter().addRowSorterListener(new RowSorterListener() {

            @Override
            public void sorterChanged(RowSorterEvent e) {
                model.fireTableDataChanged();
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.fireTableRowsUpdated(0, model.getRowCount() - 1);
            }
        });
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getModel().addTableModelListener(new TableModelListener() {
        	@Override
        	//Track changes to individual cells here.
        	public void tableChanged(TableModelEvent e) {
            	TableModel model = (TableModel)e.getSource();
            	Object data = model.getValueAt(e.getFirstRow(), e.getColumn());
            	
            	//System.out.println("Changing cell " + getCellName(e.getFirstRow(), e.getColumn()) + " to " + model.getValueAt(e.getFirstRow(), e.getColumn()));
            	//System.out.println("Changing cell (" + (e.getFirstRow()+1) + ", " + (e.getColumn()+1) +") to " + model.getValueAt(e.getFirstRow(), e.getColumn()));
            	//cell[e.getFirstRow()][e.getColumn()].validateInput(model.getValueAt(e.getFirstRow(), e.getColumn()));
        	}
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocation(150, 150);
        frame.setVisible(true);	
        frame.toFront();
        
	}
    // END OF HANDLING GUI 
	
    //retrieves the cell row index from the cell name i.e. A1 => row index is 0 (1 - 1)
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
    
    //get the cell name by providing its index, getCellName(0,0) returns A1
    public String getCellName(int row, int column) {
    	
    	if(row >= 0 && column <= 9)
    		return (char)(65 + column) + "" + (row + 1);
    	else
    		return "The index is out of bound";
    }
    
    //Returns either the first or second portion of an equation, if A1 = 34 + 5, it returns either "A1" or "34 + 5"
    //depending on the element that is asked
    public String getEquationElement(String fullEquation, String element) {
    	
    	String[] splitCommand = new String[2];    	
    	splitCommand = fullEquation.split("=");
    	String cellName = splitCommand[0]; //name of cell that will be modified
    	String equation = splitCommand[1]; //equation associated with the cell, also stored in cell.setFormula(formula)
    	
    	if(element.equals("cellname"))
    		return cellName;
    	else if(element.equals("equation"))
    		return equation;
    	else
    		return "Bad argument";
    	    	
    }
    
    //converts an alphanumeric equation to a numerical equation, "B1 + 35 - C1" where B1=20 & C1=87 becomes "20 + 35 - 87"
    public String getNumEquation(String pattern, String equation, JTable table) {
    	
    	Pattern MY_PATTERN = Pattern.compile(pattern);
		Matcher myMatch = MY_PATTERN.matcher(equation);
		String numEquation = equation;
		String otherCells = "";
		double cellValue = 0;
		
		while(myMatch.find()) {
			
			otherCells = myMatch.group();
			
			cellValue = (Double) table.getValueAt(getCellRow(otherCells), getCellColumn(otherCells));			                			
			numEquation = numEquation.replace(otherCells, Double.toString(cellValue));
		}
		myMatch.reset();
    	return numEquation;
    }
    
   
    //store the command in a 2D array, use the array to save entries, to load them
    public void storeEntries(String[][] entries, String cellname, String fullEquation) {
    	
    	int row = getCellRow(cellname);
    	int column = getCellColumn(cellname);
    	
    	entries[row][column] = fullEquation;
    	
    }
    
    public void restoreEntries(String[][] entries) {
    	
    	//String pattern = "^[A-Ja-j]\\d{1,2}=.+$";
    	String equation = "";
    	String cellname = "";
    	
    	for(int i = 0; i < entries.length; i++) {
			for(int j = 0; j < entries[0].length; j++) {
				
				if(entries[i][j].matches(startPattern)) {
					cellname = getEquationElement(entries[i][j], "cellname");
					equation = getEquationElement(entries[i][j], "equation");
					
					if(entries[i][j].matches(numericPattern)) {
						numericInput(cellname, equation);
					}
					else if(entries[i][j].matches(alphaNumPattern)) {
						alphanumericInput(cellname, equation);
					}
					else {
						System.out.println("The command is not valid.");
					}
				}
				else {
					if(entries[i][j].equals("0.0")){
						table.setValueAt(0.0, i, j);
					}
					else
						System.out.println("The command is not valid (2).");
				}
			}
		}
    }
    
    //check the table after each command to see if other cells are affected by the latest command
    public void updateTable(JTable table, String[][] formulas, String cellname) {
    	
    	ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
    	String newEquation = "";
    	char n = 'J';
    	String pattern = "[A-" + n +"]\\d{1,2}";
    	
    	for(int i = 0; i < formulas.length; i++) {
    		
    		for(int j = 0; j < formulas[0].length; j++) {
    			
    			if(!formulas[i][j].equals("")) {
    				if(formulas[i][j].contains(cellname)) {
    					
    					newEquation = getNumEquation(pattern, formulas[i][j], table);
    					
    					try {
							Object result = engine.eval(newEquation);
							table.getModel().setValueAt(result, i, j);
	        				
						} catch (ScriptException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        			}
    			}
    			
    		}
    	}
    }
    
    //Print the cell values as a formatted table
    public void printTable(JTable table) {
    	int counter = 0;
    	char header = ' ';
    	String padding = "%8s";
    	
    	System.out.println();
    	
    	while(counter < 11) {
    		
    		if(counter != 0) {
    			header = (char)((65 + counter)-1);
    			System.out.format(padding, header);
    		}
    		else
    			System.out.format("%6s", " ");
    		counter++;
    	}
    	    	
    	System.out.println();
    	String entry = "";
    	for(int i = 0; i < table.getRowCount(); i++) {
    		for(int j = 0; j < table.getColumnCount(); j++) {
    			
    			if(j == 0){
    				entry = (i+1) + "";
					System.out.format(padding, entry);    					
    			}
    			
    			System.out.format(padding, table.getValueAt(i, j));
    			
    		}
    		System.out.println();
    	}
    }
  //****************************************************my code (Simone)*****************************************************//
    
    public static void main(String[] args) {
    	try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    	
    	Main TestTableRowHeader = new Main();
    }
}