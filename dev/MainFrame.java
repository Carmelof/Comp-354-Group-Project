package dev;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MainFrame extends JFrame {

	private FileHandler fileHandler;
	private ActionListener menuListener;
	private JMenuItem new_f, load, save, quit;
	private JMenuItem cut, copy, paste, undo, redo;
	private JMenuItem add_c, add_r, form_int, form_mon, form_sci;
	private JMenuItem fun_help, about;
	private JLabel selectedCellLabel;
	private JTextField textField;
	private Grid grid;
	private DefaultTableModel model;
	private JLabel statusBar;
	private String clipboard;
	
	public MainFrame(String title) {
		super(title);
		// if the program should not quit when the window is closed, revert to DISPOSE_ON_CLOSE
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(150, 150);
        
        fileHandler = new FileHandler(this);
	    
	    initMenu();
	    
	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    
	    selectedCellLabel = new JLabel("", JLabel.LEADING);
	    selectedCellLabel.setMaximumSize(new Dimension(100, 30));
	    panel.add(selectedCellLabel);
	    textField = new JTextField("");
	    textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	    textField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(grid.getSelectedRow() == -1 || grid.getSelectedColumn() == -1) {
					statusBar.setForeground(Color.red);
					statusBar.setText("Warning! You must select a cell before entering a command.");
				}
				else {
					performCommand(new Command(textField.getText()));
				}				
                grid.clearFuture();
                fileHandler.setSaved(false);
			}
	    });
	    panel.add(textField);
	    
	    add(panel);
	    
	    initTable();
	    
	    statusBar = new JLabel("status", JLabel.LEADING);
	    statusBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	    add(statusBar);
        
        pack();

        setVisible(true);	
        //setResizable(false);
        toFront();
        grid.addToHistory();
	}
	
	private void initMenu() {
        JMenuBar jmb = new JMenuBar();
	    setJMenuBar(jmb);
	    
	    JMenu file = new JMenu("File");
	    jmb.add(file);
	    // Stub
	    new_f = new JMenuItem ("New");
	    file.add(new_f);
	    
	    load = new JMenuItem ("Load");
	    file.add(load);
	    
	    save = new JMenuItem ("Save");
	    file.add(save);
	    
	    file.addSeparator();
	    
	    quit = new JMenuItem ("Quit");
	    file.add(quit);
	    
	    JMenu edit = new JMenu("Edit");
	    jmb.add(edit);
	    
	    cut = new JMenuItem ("Cut");
	    edit.add(cut);
	    
	    copy = new JMenuItem ("Copy");
	    edit.add(copy);
	    
	    paste = new JMenuItem ("Paste");
	    edit.add(paste);
	    
	    edit.addSeparator();
	    
	    undo = new JMenuItem ("Undo");
	    edit.add(undo);
	    
	    redo = new JMenuItem ("Redo");
	    edit.add(redo);
	    
	    JMenu format = new JMenu("Format");
	    jmb.add(format);
	    
	    add_r = new JMenuItem ("Add Row");
	    format.add(add_r);
	    
	    add_c = new JMenuItem ("Add Column");
	    format.add(add_c);
	    
	    format.addSeparator();
	    
	    form_int = new JMenuItem ("Integer Format");
	    format.add(form_int);
	    
	    form_mon = new JMenuItem ("Monetary Format");
	    format.add(form_mon);
	    
	    form_sci = new JMenuItem ("Scientific Format");
	    format.add(form_sci);
	    
	    JMenu help = new JMenu("Help");
	    jmb.add(help);
	    
	    fun_help = new JMenuItem ("View FunSheets Help");
	    help.add(fun_help);
	    
	    about = new JMenuItem ("About FunSheets");
	    help.add(about);
	    
	    
	    menuListener = new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	 if (e.getSource() == new_f){
				    	if (fileHandler.checkSaved()) {
				    		grid.clearFuture();
				    		grid.clearHistory();
				    		grid.clearGrid();
				    		grid.addToHistory();
				    		statusBar.setForeground(Color.black);
				    		statusBar.setText("New Grid Loaded.");
				    	}
				    }
				    else if (e.getSource() == load) {
			    	if (fileHandler.checkSaved()) {
			    		fileHandler.loadFile(grid);
			    		statusBar.setForeground(Color.black);
			    		statusBar.setText("The file has been loaded successfully.");
			    	}
			                  
			    } else if(e.getSource() == save) {
			             fileHandler.saveFile(grid);
			             statusBar.setForeground(Color.black);
				    	 statusBar.setText("The file has been saved successfully.");
			    }
			    else if(e.getSource() == quit) {
			        	 System.exit(0);
			    }
			    else if(e.getSource() == undo){
			    	grid.undo();
			    }
			    else if(e.getSource() == redo){
			    	grid.redo();
			    }
			    else if(e.getSource() == cut) {
			    	if(grid.getSelectedRow() == -1 || grid.getSelectedColumn() == -1) {
						statusBar.setForeground(Color.red);
						statusBar.setText("Warning! You must select a cell before cutting.");
					} else {
						clipboard = textField.getText();
						performCommand(new Command("0.0"));
					}
			    }
			    else if(e.getSource() == copy) {
			    	if(grid.getSelectedRow() == -1 || grid.getSelectedColumn() == -1) {
						statusBar.setForeground(Color.red);
						statusBar.setText("Warning! You must select a cell before copying.");
					} else {
						clipboard = textField.getText();
					}
			    }
			    else if(e.getSource() == paste) {
			    	if(grid.getSelectedRow() == -1 || grid.getSelectedColumn() == -1) {
						statusBar.setForeground(Color.red);
						statusBar.setText("Warning! You must select a cell before pasting.");
					} else {
						performCommand(new Command(clipboard));
					}
			    }
			    else if(e.getSource() == add_r){
			    	//adds a row
			    }
			    else if(e.getSource() == add_c){
			    	//adds a column 
			    }
			    else if(e.getSource() == form_int){
			    	//formats selected cell to int form
			    }
			    else if(e.getSource() == form_mon){
			    	//formats selected cell to monetary form
			    }
			    else if(e.getSource() == form_sci){
			    	//formats selected cell to scientific form
			    }
			    else if(e.getSource() == fun_help){
			    	//a mini tutorial for the program
			    }
			    else if(e.getSource() == about){
			    	//makes a box that shows group members names and mini description of program
			    }
		    }
		};
		new_f.addActionListener(menuListener);	
	    load.addActionListener(menuListener);
	    save.addActionListener(menuListener);
	    quit.addActionListener(menuListener);
	    
	    cut.addActionListener(menuListener);
	    copy.addActionListener(menuListener);
	    paste.addActionListener(menuListener);
	    undo.addActionListener(menuListener);
	    redo.addActionListener(menuListener);
	    
	    add_r.addActionListener(menuListener);
	    add_c.addActionListener(menuListener);
	    form_int.addActionListener(menuListener);
	    form_mon.addActionListener(menuListener);
	    form_sci.addActionListener(menuListener);
	    
	    fun_help.addActionListener(menuListener);
	    about.addActionListener(menuListener);
	}
	
	private void initTable(){
		//-------------------------------
		// Source code acquired from 
		// http://stackoverflow.com/questions/8002445/trying-to-create-jtable-with-proper-row-header
		//-------------------------------
		grid = new Grid(){
		    public boolean isCellEditable(int rowIndex, int colIndex) {
		        return false;   //Disallow the editing of any cell
		    }
		};
		grid.setColumnSelectionAllowed(true);
	    grid.setRowSelectionAllowed(true);
	    
        //TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(grid.getModel());
        //grid.setRowSorter(sorter);
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
                return grid.getRowCount();
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
        JTable headerTable = new JTable(model);
        for (int i = 0; i < grid.getRowCount(); i++) {
            headerTable.setValueAt((i + 1), i, 0);
        }
        headerTable.setShowGrid(true);
        headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        headerTable.setPreferredScrollableViewportSize(new Dimension(50, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                boolean selected = grid.getSelectionModel().isSelectedIndex(row);
                Component component = grid.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(grid, value, false, false, -1, -2);
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
        
        /*grid.getRowSorter().addRowSorterListener(new RowSorterListener() {

            @Override
            public void sorterChanged(RowSorterEvent e) {
                model.fireTableDataChanged();
            }
        });*/
        /*grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.fireTableRowsUpdated(0, model.getRowCount() - 1);
                int x = grid.getSelectedRow();
                int y = grid.getSelectedColumn();
                String formula = grid.getCell(x, y).getFormula();
                textField.setText(formula.length() > 0 ? formula : Double.toString(grid.getCell(x, y).getValue()));
            }
        });*/
        grid.addMouseListener(new java.awt.event.MouseAdapter() {        	
        	@Override
        	public void mouseClicked(java.awt.event.MouseEvent e){
            	int x = grid.rowAtPoint(e.getPoint());
                int y = grid.columnAtPoint(e.getPoint());
                selectedCellLabel.setText("  " + (char)('A' + y) + "" + (x + 1) + " = ");
                String formula = grid.getCell(x, y).getFormula();
                textField.setText(formula.length() > 0 ? formula : Double.toString(grid.getCell(x, y).getValue()));
            }        	
        });
        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setRowHeaderView(headerTable);
        grid.setPreferredScrollableViewportSize(grid.getPreferredSize());
        grid.getModel().addTableModelListener(new TableModelListener() {
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
        add(scrollPane);      
	}
	
	private void performCommand(Command command) {
		int x = grid.getSelectedRow();
        int y = grid.getSelectedColumn();
        String cellName = (char)(('A') + y) + "" + (x + 1) + "";
		command.trim();
		int typeOfCommand = command.evaluate(grid, x, y, cellName);
							
		if(typeOfCommand == 0){ //formatted formula
			textField.setText(command.getCommand());
			statusBar.setForeground(Color.black);
			statusBar.setText("Cell " + cellName + " has been updated successfully with the " + grid.getCell(x, y).getFormatType() +" format");
			grid.addToHistory();
		}
		else if(typeOfCommand == 1) { //regular formula
			textField.setText(command.getCommand());
			statusBar.setForeground(Color.black);
			statusBar.setText("Cell " + cellName + " has been updated successfully.");
			grid.addToHistory();
		}
		else if(typeOfCommand == 2) { //circular reference, invalid input
			statusBar.setForeground(Color.red);
			statusBar.setText("Warning! Circular references are not permitted. Please try again.");
		}
		else if(typeOfCommand == 3) { //invalid input
			statusBar.setForeground(Color.red);
			statusBar.setText("The command is invalid, please try again.");
		}
		else if(typeOfCommand == 4) { //division by 0, invalid input
			statusBar.setForeground(Color.red);
			statusBar.setText("Error! Division by 0, please try again.");
		}	
	}
}
