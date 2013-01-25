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
    String[] columns = { "A", "B"};

    Object[][] content = { { "R", new Integer(24) }, { "A", new Integer(25) },
        { "J", new Integer(30) }, { "A", new Integer(32) }, { "S", new Integer(27) } };

    JTable table = new JTable(content, columns);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel jPanel = new JPanel(new GridLayout(2, 0));
    jPanel.setOpaque(true);
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    jPanel.add(new JScrollPane(table));
    /* Add the panel to the JFrame */
    frame.add(jPanel);
    /* Display the JFrame window */
    frame.pack();
    frame.setVisible(true);

    table.print();
  }
}