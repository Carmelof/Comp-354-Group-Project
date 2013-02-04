/*How to see the tests:
 * 
 * For those of you not familiar with JUnit, it comes already installed if using eclipse
 * to see the tests right click on the Testing_it1.java then "Run As"-> JUnit Test
 * 
 */
 
package Testing;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.sun.net.httpserver.Authenticator.Success;

import dev.Cell;
import dev.Main;

public class MainTest {
	// Acceptable error for comparing doubles
	public final double ACCEPTED_ERROR = 1E-10;
	
	/*=====================================================================
	 * Tester: Karim
	 * the save checks halt the automatic testing
	 * im not sure how to make the main's window close so they keep going
	 *=====================================================================
	 */
	@Test
	public final void testCheckSaved0() {
		Main a= new Main();
		assertEquals("File Saved?", true, a.checkSaved());
	}
	@Test
	public final void testCheckSaved1() {
		Main a= new Main();
		a.executeCommand("a1=23");
		assertEquals("File Saved?", false, a.checkSaved());
	}
	@Test
	public final void testCheckSaved2() {
		Main a= new Main();
		a.executeCommand("a1=23");
		a.executeCommand("save");
		assertEquals("File Saved?", true, a.checkSaved());
	}
	@Test
	public final void testCheckSaved3() {
		Main a= new Main();
		a.executeCommand("not legal input");
		assertEquals("File Saved?", true, a.checkSaved());
	}

	@Test
	public final void testNumericInput() {
		Main a= new Main();
		a.executeCommand("");
		a.numericInput("a1", "3");
		assertEquals("File Saved?", (double)3,(double)((a.table).getModel().getValueAt(0,0)),ACCEPTED_ERROR);
	}

	@Test
	public final void testAlphanumericInput() {
		Main a= new Main();
		a.executeCommand("b2=3");
		a.alphanumericInput("a1", "3+b2");
		assertEquals("File Saved?", (double)6,(double)((a.table).getModel().getValueAt(0,0)),ACCEPTED_ERROR);
	}

	@Test //Not really testable just to see if it crashes or not.
	public final void testPrepareVars() {
		Main a= new Main();
		a.executeCommand("b2=3");
		a.alphanumericInput("a1", "3+b2");
		a.prepareVars();
		return;
	}
	
	/*=====================================================================
	 * Tester: Dragos
	 * Note: due to the application's design, these tests cannot be fully automated.
	 *=====================================================================
	 */
    	@Rule
   	public ExpectedException exceptionThrown = ExpectedException.none();

	@Test
	public final void testMain() {
		Main application = new Main();
		// No errors expected
	}

	@Test
	public final void testExecuteCommandCellNameNumExpr() {
		Main application = new Main();
		application.executeCommand("A1 = 35 + 4 * (9 / 3 - 4)");
	}

	@Test
	public final void testExecuteCommandCellNameNumExprRowOOB() {
		exceptionThrown.expect(IndexOutOfBoundsException.class);
		Main application = new Main();
		application.executeCommand("A52 = 25");
	}

	@Test
	public final void testExecuteCommandCellNameNumExprColOOB() {
		exceptionThrown.expect(IndexOutOfBoundsException.class);
		Main application = new Main();
		application.executeCommand("Z2 = 25");
	}

	@Test
	public final void testExecuteCommandCellNameAlphaNumExpr() {
		Main application = new Main();
		application.executeCommand("A1 = B1 + 5 + C4 * 6");
	}

	@Test
	public final void testExecuteCommandCellNameAlphaNumExprLeftOOB() {
		exceptionThrown.expect(IndexOutOfBoundsException.class);
		Main application = new Main();
		application.executeCommand("A31 = B1 + 5");
	}

	@Test
	public final void testExecuteCommandCellNameAlphaNumExprRightOOB() {
		exceptionThrown.expect(IndexOutOfBoundsException.class);
		Main application = new Main();
		application.executeCommand("A1 = B21 + 5");
	}

	@Test
	public final void testExecuteCommandCellNameConstOpExpr() {
		Main application = new Main();
		application.executeCommand("A1 = B1");
	}

	@Test
	public final void testExecuteCommandCellNameConstOpExprOOB() {
		exceptionThrown.expect(IndexOutOfBoundsException.class);
		Main application = new Main();
		application.executeCommand("A1 = Z1");
	}

	@Test
	public final void testExecuteCommandCellNameConstOpExprAlt() {
		Main application = new Main();
		application.executeCommand("A1 = -4 * B1");
	}

	@Test
	public final void testExecuteCommandLoad() {
		Main application = new Main();
		application.executeCommand("load");
	}

	@Test
	public final void testExecuteCommandSave() {
		Main application = new Main();
		application.executeCommand("save");
	}

	@Test
	public final void testExecuteCommandQuit() {
		Main application = new Main();
		application.executeCommand("quit");
	}

	@Test
	public final void testSaveFile() {
		Main application = new Main();
		String[][] data = {{"1", "2"}, {"3", "4"}};
		application.saveFile(data);
	}

	@Test
	public final void testLoadFile() {
		Main application = new Main();
		application.loadFile();
	}
	
	/*=====================================================================
	 * Tester: Carmelo
	 * Note: In order for the JUnits to function, the while loop in main
	 * must be commented out.
	 *=====================================================================
	 */
	@Test
	public final void testGetCellRow() {
		Main main = new Main();
		assertEquals("Get Cell Row", 5, main.getCellRow("B6"));
	}

	@Test
	public final void testGetCellColumn() {
		Main main = new Main();
		assertEquals("Get Cell Row", 3, main.getCellColumn("D9"));
	}

	@Test
	public final void testGetCellName() {
		Main main = new Main();
		assertEquals("Get Cell Row", "E3", main.getCellName(2, 4));
	}
	
	@Test
	public final void testGetNumEquation() {
		Main main = new Main();
		String pattern = "[A-J]\\d{1,2}";
		
		JTable table = new JTable(10, 10);
		table.getModel().setValueAt(20.0, 0, 1);
		table.getModel().setValueAt(87.0, 0, 2);
		
		assertEquals("Get Num Equation", "20.0 + 35 - 87.0", main.getNumEquation(pattern, "B1 + 35 - C1", table));
	}

	@Test
	public final void testUpdateTable() {
		fail("Not yet implemented"); // TODO
		//Unable to test, no returnable data by method is testable.
	}
}
