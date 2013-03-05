package Testing;

import static org.junit.Assert.*;

import org.junit.Test;
import dev.*;
import java.io.*;
import java.util.Scanner;

//	To be implemented 
public class FileHandlerTest {

	// Control test: grid
	@Test
	public void control() {
		Grid a = new Grid();
		a.insertValue(79.1, 1, 2);
		Grid b = new Grid();
		b.insertValue(79.1, 1, 2);
		assertEquals("Control check: grid", a, b);
	}
	
	// Check consistency between saved and loaded files
	@Test
	public void consistencyEmpty() {
		Grid a = new Grid();
		Grid b = new Grid();
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.saveFile(a, "test");
		System.out.println(a);
		System.out.println(b);
		fh.loadFile(b, new File("test"));
		assertEquals("Consistency check", a, b);
	}
	@Test
	public void consistencyDouble() {
		Grid a = new Grid();
		a.insertValue(79.1, 1, 2);
		Grid b = new Grid();
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.saveFile(a, "test");
		fh.loadFile(b, new File("test"));
		assertEquals("Consistency check", a, b);
	}
	@Test
	public void consistencyFormula() {
		Grid a = new Grid();
		a.insertValue("B1+9", 1, 1);
		Grid b = new Grid();
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.saveFile(a, "test");
		fh.loadFile(b, new File("test"));
		assertEquals("Consistency check", a, b);
	}
	@Test
	public void consistencyBoth() {
		Grid a = new Grid();
		a.insertValue(79.1, 1, 2);
		a.insertValue("B1+9", 1, 1);
		Grid b = new Grid();
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.saveFile(a, "test");
		fh.loadFile(b, new File("test"));
		assertEquals("Consistency check", a, b);
	}
	
	// Check that files are saved correctly
	@Test
	public void saveTest() {
		String a = "";
		String b = "";
		Grid x = new Grid();
		x.insertValue(79.1, 1, 2);
		x.insertValue("B1+9", 1, 1);
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.saveFile(x, "test");
        Scanner in;
		try {
			in = new Scanner(new File("test")).useDelimiter("\n");
	        while (in.hasNext())
	        	a += in.next();
			in = new Scanner(new File("Testing/sample_file.csv")).useDelimiter("\n");
	        while (in.hasNext())
	        	b += in.next();
		} catch (FileNotFoundException e) {
			assertEquals("File was missing for saveTest", 0, 1);
		}
		assertEquals("Save check", a, b);
	}
	
	
	// Check that files load correctly
	@Test
	public void loadTest() {
		Grid a = new Grid();
		a.insertValue(79.1, 1, 2);
		a.insertValue("B1+9", 1, 1);
		Grid b = new Grid();
		FileHandler fh = new FileHandler(new MainFrame("test"));
		fh.loadFile(b, new File("Testing/sample_file.csv"));
		assertEquals("Load check", a, b);
	}

	
}
