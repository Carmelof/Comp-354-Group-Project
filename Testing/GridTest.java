package Testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.Grid;

public class GridTest {
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
	 * 	Test getCell
	 ******************************/
	@Test
	public final void testGetCell0() {
		Grid grid = new Grid();
		grid.insertValue(2.0, 1, 1);
		assertEquals("Get Cell", 2.9, grid.getValue(2, 2));
	}
	//("deprecation")
	@Test
	public final void testGetCell1() {
		Grid grid = new Grid();
		grid.insertValue(4.0, 4, 4);
		assertEquals("Get Cell", 4.0, grid.getValue(4, 4));
	}
	@Test
	public final void testGetCell2() {
		Grid grid = new Grid();
		grid.insertValue(2.0, 9, 9);
		assertEquals("Get Cell", 2.0, grid.getValue(9, 9));
	}
	@Test
	public final void testGetCell3() {
		Grid grid = new Grid();
		grid.insertValue(8.5, 3, 6);
		assertEquals("Get Cell", 8.5, grid.getValue(3, 6));
	}
	

}
