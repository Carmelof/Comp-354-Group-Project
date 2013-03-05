package dev;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {

    private boolean saved;
    private JFileChooser fileChooser;
    private Component component;
    
    public FileHandler(Component component) {
    	this.component = component;
    	fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setName("chooser");
		saved = true;
    }

    public void saveFile(Grid grid){
    	int ret = fileChooser.showSaveDialog(component);
    	if (ret == JFileChooser.APPROVE_OPTION) {
        	saveFile(grid, fileChooser.getSelectedFile().getAbsolutePath());
    	}
    }
    
    // this version of the function is used for testing purposes only
    public void saveFile(Grid grid, String filename){
    	// TODO: fix for variable grid size
    	String[][] entries = new String[10][10];
    	storeEntries(grid, entries);
    	try {
    		FileWriter fstream = new FileWriter(filename);
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
    	System.out.println("File successfully saved as " + filename);
    	saved = true;
    }
    
    public void loadFile(Grid grid){
		int ret = fileChooser.showOpenDialog(component);
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    loadFile(grid, file);
		}
    }

    public void loadFile(Grid grid, File file){
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
            String[][] cellEntries = new String[rows][cols];
    		System.out.println("Data successfully loaded from file:\n\t" + file);
            for (int i = 0; i < cellArray.size(); ++i){
            	String[] row = cellArray.get(i);
            	if (i < cellArray.size()-1)
            		assert row.length == cellArray.get(i+1).length;
            	for (int j=0; j<row.length; j++){
            		cellEntries[i][j] = row[j];
            	}
            }
    		restoreEntries(grid, cellEntries);
    		saved = true;
        } catch (IOException e) {
            System.err.println("There was a problem trying to load the file.");
        } catch (AssertionError e) {
        	System.err.println("The file could not be loaded because of its "
        			+ "formatting. It may be corrupted.");
        }
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
    
    public void setSaved(boolean saved) {
    	this.saved = saved;
    }
   
    //store the command in a 2D array, use the array to save entries, to load them
    private void storeEntries(Grid grid, String[][] entries) {
    	for(int i = 0; i < entries.length; i++) {
			for(int j = 0; j < entries[0].length; j++) {
				String formula = grid.getCell(i, j).getFormula();
				entries[i][j] = formula.length() > 0 ? formula : Double.toString(grid.getCell(i, j).getValue());
			}
    	}
    }
    
    private void restoreEntries(Grid grid, String[][] entries) {
    	for(int i = 0; i < entries.length; i++) {
			for(int j = 0; j < entries[0].length; j++) {
				grid.insertValue(entries[i][j], i, j);
			}
    	}
    	for(int i = 0; i < entries.length; i++) {
			for(int j = 0; j < entries[0].length; j++) {
				grid.evaluteCell(grid.getCell(i, j));
			}
    	}
    }
}
