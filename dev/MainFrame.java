package dev;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {
	
	public MainFrame(String title) {
		super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(150, 150);
        setVisible(true);	
        toFront();
        
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

}
