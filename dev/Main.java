package dev;
import javax.swing.*;
import javax.swing.UIManager.*;

public class Main {
	
    private static MainFrame frame;
    
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
    	
        frame = new MainFrame("Comp 354 - FunSheets Document");
    }
}