package Testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.Grid;
import dev.Main;

public class GridTest {
	public final double ACCEPTED_ERROR = 1E-10;
	/*=====================================================================
	 * Tester: ADDISON 
	 *=====================================================================
	 */
	
	/******************************
	 * 	Test getCellRow
	 ******************************/
	@Test
	public final void testGetCellRow0() {
		Grid grid = new Grid();
		assertEquals("Get Cell Row", 5, grid.getCellRow("B6"));
	}
	@Test
	public final void testGetCellRow1() {
		Grid grid = new Grid();
		assertEquals("Get Cell Row", 5, grid.getCellRow("A6"));
	}
	@Test
	public final void testGetCellRow2() {
		Grid grid = new Grid();
		assertEquals("Get Cell Row", 6, grid.getCellRow("C7"));
	}
	@Test
	public final void testGetCellRow3() {
		Grid grid = new Grid();
		assertEquals("Get Cell Row", 0, grid.getCellRow("D1"));
	}
	/******************************
	 * 	Test getCellColumn
	 ******************************/
	@Test
	public final void testGetCellColumn0() {
		Grid grid = new Grid();
		assertEquals("Get Cell Column", 3, grid.getCellColumn("D9"));
	}
	@Test
	public final void testGetCellColumn1() {
		Grid grid = new Grid();
		assertEquals("Get Cell Column", 0, grid.getCellColumn("A1"));
	}
	@Test
	public final void testGetCellColumn2() {
		Grid grid = new Grid();
		assertEquals("Get Cell Column", 2, grid.getCellColumn("C4"));
	}
	@Test
	public final void testGetCellColumn3() {
		Grid grid = new Grid();
		assertEquals("Get Cell Column", 6, grid.getCellColumn("G9"));
	}
	/******************************
	 * 	Test getCell  --- THESE ARE FAILING, COME BACK TO
	 ******************************/
	@Test
	public final void testGetCell0() {
		Grid grid = new Grid();
		grid.insertValue(2.0, 1, 1);
		assertEquals("Get Cell", 2.9, grid.getValueAt(1, 1));
	}
	//("deprecation")
	@Test
	public final void testGetCell1() {
		Grid grid = new Grid();
		grid.insertValue(4.0, 4, 4);
		assertEquals("Get Cell", 4.0, grid.getValueAt(4, 4));
	}
	@Test
	public final void testGetCell2() {
		Grid grid = new Grid();
		grid.insertValue(2.0, 9, 9);
		assertEquals("Get Cell", 2.0, grid.getValueAt(9, 9));
	}
	@Test
	public final void testGetCell3() {
		Grid grid = new Grid();
		grid.insertValue(8.5, 3, 6);
		assertEquals("Get Cell", 8.5, grid.getValueAt(3, 6));
	}
	/******************************
	 * 	Test AlphaNumericalInput
	 ******************************/
	@Test
	public final void testAlphanumericInput0() {
		Grid grid= new Grid();
		grid.insertValue(1.0, 0, 0);
		String equation = grid.alphanumericInput("A1 + B1");
		assertEquals("Test Alpha", "1.0 + 0.0", equation);
	}
	@Test
	public final void testAlphanumericInput1() {
		Grid grid= new Grid();
		grid.insertValue(4.0, 0, 0);
		String equation = grid.alphanumericInput("A1 + C1");
		assertEquals("Test Alpha", "4.0 + 0.0", equation);
	}
	@Test
	public final void testAlphanumericInput2() {
		Grid grid= new Grid();
		String equation = grid.alphanumericInput("D1 + E1");
		assertEquals("Test Alpha", "0.0 + 0.0", equation);
	}
	@Test
	public final void testAlphanumericInput3() {
		Grid grid= new Grid();
		String equation = grid.alphanumericInput("F1 + C2");
		assertEquals("Test Alpha", "0.0 + 0.0", equation);
	}
	/******************************
	 * 	Test NumericalInput
	 ******************************/
	@Test
	public final void testNumericInput0() {
		Grid grid= new Grid();
		//double number = grid.numericInput("5 + 5");
		assertEquals("Testing Numerical Input", (double)5 , (double)grid.numericInput("5"), ACCEPTED_ERROR);
	}
	@Test
	public final void testNumericInput1() {
		Grid grid= new Grid();
		//double number = grid.numericInput("5 + 5");
		assertEquals("Testing Numerical Input", (double)10 , (double)grid.numericInput("5 + 5"), ACCEPTED_ERROR);
	}
	@Test
	public final void testNumericInput2() {
		Grid grid= new Grid();
		//double number = grid.numericInput("5 + 5");
		assertEquals("Testing Numerical Input", (double)2 , (double)grid.numericInput("0 + 2"), ACCEPTED_ERROR);
	}
	@Test
	public final void testNumericInput3() {
		Grid grid= new Grid();
		//double number = grid.numericInput("5 + 5");
		assertEquals("Testing Numerical Input", (double)6 , (double)grid.numericInput("9 - 3"), ACCEPTED_ERROR);
	}
}
