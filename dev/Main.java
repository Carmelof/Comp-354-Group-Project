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

    private JFrame frame = new JFrame("Comp 354-Excel Document");
    private JScrollPane scrollPane;
    public JTable table;
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
    	
    	while(!quit) {
    	    		
    		while(true) {// As long as the input from the console isn't "quit" do...
    			System.out.println("\nEnter a command: ");
    			inputStr = inputCommand.nextLine();
    			executeCommand(inputStr);
            	
    			if (quit)
    				break;
    		}
    		
    	} //end while(!quit)
    	
        
    }
    	
    public void executeCommand(String inputStr) {
    	String noSpaceCommand = inputStr.replaceAll("\\s", ""); //take out any whitespace chars
    	noSpaceCommand = noSpaceCommand.toUpperCase(); //put the char in the cell name to uppercase i.e b1 => B1
    	
    	if(noSpaceCommand.matches(startPattern)) {//if the command starts correctly i.e "A1=" continue
    	    String[] splitCommand = new String[2];
	    	//split the equation in two halves i.e A1 = 4.5 + 30 => part1: A1, part2: 4.5 + 30
	    	splitCommand = noSpaceCommand.split("=");
	    	String cellName = splitCommand[0]; //name of cell that will be modified
	    	String equation = splitCommand[1]; //equation associated with the cell, also stored in cell.setFormula(formula)
	    	
	    	//if the equation only contains numbers
    		if(noSpaceCommand.matches(numericPattern)) {
        		numericInput(cellName, equation);
        		saved = false;
        	}
    		//if the equation contains cell names
        	else if(equation.matches(alphaNumPattern)) {
        		alphanumericInput(cellName, equation);
        		saved = false;
        	} else {
        		System.out.println("The command is not valid");
        	}
    	} else {
    		 if(noSpaceCommand.equals("QUIT")) {
    			 if (checkSaved()){
	    			 System.out.println("Quitting...");
	                 // if unsaved, prompt to save
	                 System.exit(0);
    			 }
    		 }            		 	
    		 else if(noSpaceCommand.equals("LOAD")) {
    			 if (checkSaved()){
	    			 loadFile();
    			 }
    		 }
    		 else if(noSpaceCommand.equals("SAVE")) {
    			 String[][] saveTest = {{"1", "2"}, {"3", "4"}};
    			 saveFile(saveTest);
    		 }
    		 else {
    			 System.out.println("The command is not valid!");
    		 }
    	}
	}
    public void saveFile(String[][] entries){
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
    public void loadFile(){
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
	            		System.out.print(row[j] + ",\t");
	            	}
	            	System.out.println();
	            }
	        } catch (IOException e) {
	            System.err.println("There was a problem trying to load the file.");
	        } catch (AssertionError e) {
	        	System.err.println("The file could not be loaded because of its "
	        			+ "formatting. It may be corrupted.");
	        }
		}
		saved = true;
    }
    
    public boolean checkSaved(){
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
    public void numericInput(String cellName, String equation){
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
		int row = getCellRow(cellName);
		int column = getCellColumn(cellName);
		try {
			Object result = engine.eval(equation); //evaluate the arithmetic expression
			double cellValue = (double) result;
			//store the value in the correct cell of the JTable
			table.getModel().setValueAt(cellValue, row, column);
			//set the value of the cell and its formula i.e A1= 4.5 & formula of A1=> "4.5 - 8 * 2"
			//cell[row][column].setValue(cellValue);
			//cell[row][column].setFormula(equation);
			//store the formula of the cell in an array i.e formula of A1=> "4.5 - 8 * 2"
			formulas[row][column] = equation;
			updateTable(table, formulas, cellName); //||||||******** ADDED CODE *********||||||//
		} catch (ScriptException e) {
		// TODO Auto-generated catch block
			System.out.println("The arithmetic equation is not valid");
			e.printStackTrace();
		}//end catch
	}
    public void alphanumericInput(String cellName, String equation){
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
			double cellValue = (double) table.getValueAt(row, column);

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

    public void prepareVars(){
		table = new JTable(10, 10);
        //Fill the table with empty values
        for (int i = 0; i < table.getRowCount(); i++) {
        	for (int j = 0; j < table.getColumnCount(); j++) {        		
        		table.setValueAt(0.0, i, j); //************** NEEDED TO BE CHANGED TO WORK (from "cell" to 0.0 ***********/
        		formulas[i][j] = "";
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
        headerTable.setShowGrid(false);
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
            	
            	System.out.println("Changing cell " + getCellName(e.getFirstRow(), e.getColumn()) + " to " + model.getValueAt(e.getFirstRow(), e.getColumn()));
            	//System.out.println("Changing cell (" + (e.getFirstRow()+1) + ", " + (e.getColumn()+1) +") to " + model.getValueAt(e.getFirstRow(), e.getColumn()));
            	//cell[e.getFirstRow()][e.getColumn()].validateInput(model.getValueAt(e.getFirstRow(), e.getColumn()));
        	}
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocation(150, 150);
        frame.setVisible(true);	
	}
    
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
    
    //converts an alphanumeric equation to a numerical equation, "B1 + 35 - C1" where B1=20 & C1=87 becomes "20 + 35 - 87"
    public String getNumEquation(String pattern, String equation, JTable table) {
    	
    	Pattern MY_PATTERN = Pattern.compile(pattern);
		Matcher myMatch = MY_PATTERN.matcher(equation);
		String numEquation = equation;
		String otherCells = "";
		double cellValue = 0;
		
		while(myMatch.find()) {
			
			otherCells = myMatch.group();
			
			cellValue = (double) table.getValueAt(getCellRow(otherCells), getCellColumn(otherCells));			                			
			numEquation = numEquation.replace(otherCells, Double.toString(cellValue));
		}
		myMatch.reset();
    	return numEquation;
    }
    
    //check the table after each command to see if other cells are affected by the latest command
    public void updateTable(JTable table, String[][] formulas, String cellname) {
    	
    	ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
    	String newEquation = "";
    	String pattern = "[A-J]\\d{1,2}";
    	
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
  //****************************************************my code (Simone)*****************************************************//
    
    public static void main(String[] args) {
    	try {// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    	
    	
    	
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main TestTableRowHeader = new Main();
            }
        });
    }
}