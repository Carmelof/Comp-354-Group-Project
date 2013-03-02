package dev;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	
	private JTextField textField;
	private Grid grid;
	private DefaultTableModel model;
	
	public MainFrame(String title) {
		super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(150, 150);
        setVisible(true);	
        toFront();
	    
	    initMenu();
	    
	    textField = new JTextField();
	    
	    initTable();
        
        pack();
	    
	}
	
	private void initMenu() {
        JMenuBar jmb = new JMenuBar();
	    setJMenuBar(jmb);
	    
	    JMenu file = new JMenu("File");
	    jmb.add(file);
	    
	    JMenuItem load = new JMenuItem ("Load");
	    load.addMouseListener(new MouseListener() {
			@Override public void mouseClicked(MouseEvent e) {
				// insert load command here
			}
			// these other events need to be overridden but can remain empty
	    	@Override public void mouseReleased(MouseEvent e) { }
			@Override public void mouseEntered(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }
			@Override public void mousePressed(MouseEvent e) { }
	    });
	    file.add(load);
	    
	    JMenuItem save = new JMenuItem ("Save");
	    save.addMouseListener(new MouseListener() {
			@Override public void mouseClicked(MouseEvent e) {
				// insert save command here
			}
			// these other events need to be overridden but can remain empty
	    	@Override public void mouseReleased(MouseEvent e) { }
			@Override public void mouseEntered(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }
			@Override public void mousePressed(MouseEvent e) { }
	    });
	    file.add(save);
	    
	    JMenuItem quit = new JMenuItem ("Quit");
	    quit.addMouseListener(new MouseListener() {
			@Override public void mouseClicked(MouseEvent e) {
				// insert quit command here
			}
			// these other events need to be overridden but can remain empty
	    	@Override public void mouseReleased(MouseEvent e) { }
			@Override public void mouseEntered(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }
			@Override public void mousePressed(MouseEvent e) { }
	    });
	    file.add(quit);
	}
	
	private void initTable(){
		grid = new Grid();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(grid.getModel());
        grid.setRowSorter(sorter);
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
        
        grid.getRowSorter().addRowSorterListener(new RowSorterListener() {

            @Override
            public void sorterChanged(RowSorterEvent e) {
                model.fireTableDataChanged();
            }
        });
        grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.fireTableRowsUpdated(0, model.getRowCount() - 1);
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
