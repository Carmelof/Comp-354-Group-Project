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
	private JMenuItem load, save, quit;
	private JLabel selectedCellLabel;
	private JTextField textField;
	private Grid grid;
	private DefaultTableModel model;
	private JLabel statusBar;
	private Command cmd;
	
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
				cmd = new Command(textField.getText());
				if(grid.getSelectedRow() == -1 || grid.getSelectedColumn() == -1) {
					statusBar.setText("You must select a cell before entering a command.");
				}
				else {
					if(cmd.isValid()) {
						int x = grid.getSelectedRow();
		                int y = grid.getSelectedColumn();
		                cmd.trim();
		                grid.insertValue(cmd.getCommand(), x, y);
		                grid.evaluteCell(grid.getCell(x, y));
		                textField.setText(cmd.getCommand());
		                String cellName = (char)(('A') + y) + "" + (x + 1) + "";
		                statusBar.setText("Cell " + cellName + " has been updated successfully.");
					}
					else {
						statusBar.setText("The command is invalid, please try again.");
					}
				}				
                
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
	}
	
	private void initMenu() {
        JMenuBar jmb = new JMenuBar();
	    setJMenuBar(jmb);
	    
	    JMenu file = new JMenu("File");
	    jmb.add(file);
	    
	    load = new JMenuItem ("Load");
	    file.add(load);
	    
	    save = new JMenuItem ("Save");
	    file.add(save);
	    
	    quit = new JMenuItem ("Quit");
	    file.add(quit);

	    menuListener = new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
			    if (e.getSource() == load) {
			    	if (fileHandler.checkSaved())
			                  fileHandler.loadFile(grid);
			    } else if(e.getSource() == save) {
			             fileHandler.saveFile(grid);
			    } else if(e.getSource() == quit) {
			        	 System.exit(0);
			    }
		    }
		};

	    load.addActionListener(menuListener);
	    save.addActionListener(menuListener);
	    quit.addActionListener(menuListener);
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
        grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.fireTableRowsUpdated(0, model.getRowCount() - 1);
                int x = grid.getSelectedRow();
                int y = grid.getSelectedColumn();
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
}
