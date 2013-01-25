import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.print.PrinterException;

public class Main {

	
	public static void main(String[] args) throws Exception {
		//Initialize columns A-K
		String[] columns = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
		//Initialize The contents to 0
		Object[][] content = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    		, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }};

    JTable table = new JTable(content, columns);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel jPanel = new JPanel(new GridLayout(2, 2));
    jPanel.setOpaque(true);
    table.setPreferredScrollableViewportSize(new Dimension(500, 500));
    jPanel.add(new JScrollPane(table));
    /* Add the panel to the JFrame */
    frame.add(jPanel);
    /* Display the JFrame window */
    frame.pack();
    frame.setVisible(true);

    table.print();
  }
}