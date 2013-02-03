import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.event.*;
import javax.swing.table.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java
//-------------------------------
// Source code acquired from 
// http://stackoverflow.com/questions/8002445/trying-to-create-jtable-with-proper-row-header
//-------------------------------
public class Main {

    private JFrame frame = new JFrame("Comp 354-Excel Document");
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    private JTable headerTable;
    //private Cell cell[][] = new Cell[10][11];
    //contains the formula for each cell if there are any
    private String[][] formulas = new String[10][11]; 
    public Main() {
        table = new JTable(10, 11);
        //Fill the table with empty values
        for (int i = 0; i < table.getRowCount(); i++) {
        	for (int j = 0; j < table.getColumnCount(); j++) {        		
        		table.setValueAt(0.0, i, j); //************** NEEDED TO BE CHANGED TO WORK (from "cell" to 0.0 ***********/
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
            	
            	System.out.println("Changing cell (" + (e.getFirstRow()+1) + ", " + (e.getColumn()+1) +") to " + model.getValueAt(e.getFirstRow(), e.getColumn()));
            	//cell[e.getFirstRow()][e.getColumn()].validateInput(model.getValueAt(e.getFirstRow(), e.getColumn()));
        	}
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocation(150, 150);
        frame.setVisible(true);
        
      //*******************************************************my code (Simone)**************************************************//
    	Scanner inputCommand = new Scanner(System.in);
    	String inputStr = "";
    	String noSpaceCommand = "";
    	boolean quit = false;
    	//Equation starts with a cell name followed by "=" i.e "A1="
    	String startPattern = "^[A-Ka-k]\\d{1,2}=.+$";
    	//Equation with numbers only i.e. (A1= 4.5), (A1= 5 + 6.7 - 8); NOT VALID => (A1= B1), (A1=B1*C4), (A1= B5 - 3.4)
    	String numericPattern = "^[A-Ka-k]\\d{1,2}=[^A-Za-z=]*$";
    	//Equation contains cell names i.e A1 = B1 + 4 - C1; DOESN'T APPLY TO => A1 = 4.7 + 90 (no cell names after "=");
    	String alphaNumPattern = "^.*[A-K]\\d{1,2}.*$"; 
    	String greeting = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n"
    					+ "***SPREADSHEET***\n\nCOMMANDS:\n\ncellname = numeric expression:\t( i.e. A1 = 35 + 4 * (9 / 3 – 4) )"
    					+ "\ncellname = alphanumeric expression\t( i.e. A1 = B1 + 5 + C4 * 6)\ncellname = [constant][operator]cellname:\t"
    					+ "( i.e. A1 = B1; A1 = -4 * B1)\nload:\tLoad the spreadsheet\nsave:\tSave the spreadsheet\nquit:\tQuit the spreadsheet program"
    					+ "\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
 
    	System.out.println(greeting);
    	
    	while(!quit) {
    	    		
    		while(!noSpaceCommand.equals("QUIT")) {// As long as the input from the console isn't "quit" do...
    			System.out.println("\nEnter a command: ");
    			inputStr = inputCommand.nextLine();
            	noSpaceCommand = inputStr.replaceAll("\\s", ""); //take out any whitespace chars
            	noSpaceCommand = noSpaceCommand.toUpperCase(); //put the char in the cell name to uppercase i.e b1 => B1
            	
            	int row = 0;
        		int column = 0;
        		double cellValue = 0;
        		
            	if(noSpaceCommand.matches(startPattern)) {//if the command starts correctly i.e "A1=" continue
            		
            		//This will call the eval function from javascript to evaluate an arithmetic expression
            		ScriptEngineManager manager = new ScriptEngineManager();
            	    ScriptEngine engine = manager.getEngineByName("JavaScript");
            	    String[] splitCommand = new String[2];
        	    	String equation = "";
        	    	String cellName = "";
        	    	//split the equation in two halves i.e A1 = 4.5 + 30 => part1: A1, part2: 4.5 + 30
        	    	splitCommand = noSpaceCommand.split("=");
        	    	cellName = splitCommand[0]; //name of cell that will be modified
        	    	equation = splitCommand[1]; //equation associated with the cell, also stored in cell.setFormula(formula)
        	    	
        	    	//if the equation only contains numbers
            		if(noSpaceCommand.matches(numericPattern)) {
                		
            			row = getCellRow(cellName);
                		column = getCellColumn(cellName);
                		cellValue = 0;
            			
            			try {
								Object result = engine.eval(equation); //evaluate the arithmetic expression
								cellValue = (double) result;
								
								//store the value in the correct cell of the JTable
								table.getModel().setValueAt(cellValue, row, column);
								
								//set the value of the cell and its formula i.e A1= 4.5 & formula of A1=> "4.5 - 8 * 2"
								//cell[row][column].setValue(cellValue);
								//cell[row][column].setFormula(equation);
								
								//store the formula of the cell in an array i.e formula of A1=> "4.5 - 8 * 2"
								formulas[row][column] = equation;
								
								
            				} catch (ScriptException e) {
							// TODO Auto-generated catch block
            					System.out.println("The arithmetic equation is not valid");
            					e.printStackTrace();
            				}//end catch
                	    
                	}//end if
                	
            		//if the equation contains cell names
                	else if(equation.matches(alphaNumPattern)) {
                		//check if there are cell names after the "=" sign i.e check for C4 & F7 in "A1 = C4 + 5 - F7"
                		Pattern MY_PATTERN = Pattern.compile("[A-K]\\d{1,2}");
                		Matcher myMatch = MY_PATTERN.matcher(equation);
                		                		
                		String otherCells = "";
                		String newEquation = equation;                 	
            			
                		//every time you find a cell name i.e A1, retrieve its index by using getCellRow & getCellColumn
                		//if you find C4 its index is row = 4 & column = 3
                		//once you find the index of the cell, retrieve its value and replace the name of the cell by its value
                		//in the newEquation string i.e if newEquation = "B1 + 4 + C2" and B1=7, C2=9; then newEquation becomes "7 + 4 + 9"
                		while(myMatch.find()) {
                			
                			otherCells = myMatch.group();                			
                			row = getCellRow(otherCells);
                			column = getCellColumn(otherCells);
                			cellValue = (double) table.getValueAt(row, column);
                			                			
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
							
        				} catch (ScriptException e) {
						// TODO Auto-generated catch block
        					System.out.println("The arithmetic equation is not valid");
        					e.printStackTrace();
        				}//end catch
                		
                	
                	}//end else if
            		
                	else {
                		System.out.println("The command is not valid");
                	}
                		
                	
            	}//end if
            	
            	else {
            		
            		
            		  
            		 if(noSpaceCommand.equals("QUIT")) {
            			 /* 
            			  * 
            			  * Max's code
            			  * 
            			  */
            			 System.out.println("Quitting..."); 
            			 inputCommand.close();
            		 }            		 	
            		 	
            		 else if(noSpaceCommand.equals("LOAD")) {
            			 /* 
            			  * 
            			  * Max's code
            			  * 
            			  */
            		 }
            		 
            		 else if(noSpaceCommand.equals("SAVE")) {
            			 /* 
            			  * 
            			  * Max's code
            			  * 
            			  */
            		 }
            		 
            		 else {
            			 System.out.println("The command is not valid!");
            		 }
            		             		             		
            		
            	}
            		
    		}//end while
    		
    		if(noSpaceCommand.equals("QUIT"))
    			quit = true;
    		
    	} //end while(!quit)
    	
        
    }
    
    //retrieves the cell row index from the cell name i.e. A1 => row index is 0 (1 - 1)
    public int getCellRow (String cellName) {
    	
    	int row = 0;
    	String[] tempArray = new String[2];
    	
    	tempArray = cellName.split("[A-K]");
    	row = Integer.parseInt(tempArray[1]) - 1;
    	
    	return row;
    }
    
    //retrieves the cell row index from the cell name i.e. A1 => column index is 0 (65 - 65); ASCII(A) = 65
    public int getCellColumn (String cellName) {
    	
    	int column = 0;
    	
    	column = ( ((int) cellName.charAt(0)) - 65 );
    	
    	return column;
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