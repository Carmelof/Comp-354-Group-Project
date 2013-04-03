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
import dev.Cell;

public class CellTest {

	// Acceptable error for comparing doubles
	public final double ACCEPTED_ERROR = 1E-10;
	/*=====================================================================
	 * Tester: DRAGOS, ADDISON 
	 *=====================================================================
	 */
    @Rule
    public ExpectedException exceptionThrown = ExpectedException.none();
    
	/**
	 * Test method for the default constructor {@link dev.Cell#Cell()}.
	 */
	@Test
	public void testCellValue() {
		Cell cell = new Cell();
		assertEquals("Initial cell value", 0.0, cell.getValue(), ACCEPTED_ERROR);
	}

	/**
	 * Test method for the default constructor {@link dev.Cell#Cell()}.
	 */
	@Test
	public void testCellFormula() {
		Cell cell = new Cell();
		assertEquals("Initial cell formula", "", cell.getFormula());
	}

	/**
	 * Test method for the default constructor {@link dev.Cell#Cell()}.
	 */
	@Test
	public void testCellRow() {
		Cell cell = new Cell();
		assertEquals("Initial cell row", -1, cell.getRow());
	}

	/**
	 * Test method for the default constructor {@link dev.Cell#Cell()}.
	 */
	@Test
	public void testCellCol() {
		Cell cell = new Cell();
		assertEquals("Initial cell column", -1, cell.getColumn());
	}

	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntValue() {
		Cell cell = new Cell(0.0, 0, 0);
		assertEquals("Cell value", 0.0, cell.getValue(), ACCEPTED_ERROR);
	}

	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntValueNegative() {
		Cell cell = new Cell(-5.0, 0, 0);
		assertEquals("Cell value", -5.0, cell.getValue(), ACCEPTED_ERROR);
	}

	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntValueInt() {
		Cell cell = new Cell(5, 0, 0);
		assertEquals("Cell value", 5.0, cell.getValue(), ACCEPTED_ERROR);
	}

	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntRow() {
		Cell cell = new Cell(0.0, 0, 0);
		assertEquals("Cell row", 0, cell.getRow());
	}

	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntRowPositive() {
		Cell cell = new Cell(0.0, 3, 0);
		assertEquals("Cell row", 3, cell.getRow());
	}


	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntCol() {
		Cell cell = new Cell(0.0, 0, 0);
		assertEquals("Cell column", 0, cell.getColumn());
	}
	
	/**
	 * Test method for {@link dev.Cell#Cell(double, int, int)}.
	 */
	@Test
	public void testCellDoubleIntIntColPositive() {
		Cell cell = new Cell(0.0, 0, 4);
		assertEquals("Cell column", 4, cell.getColumn());
	}


	/**
	 * Test method for {@link dev.Cell#getRow()}.
	 */
	@Test
	public void testGetRow() {
		Cell cell = new Cell(5.0, 3, 4);
		assertEquals("Cell row getter", 3, cell.getRow());
	}

	/**
	 * Test method for {@link dev.Cell#getColumn()}.
	 */
	@Test
	public void testGetColumn() {
		Cell cell = new Cell(5.0, 3, 4);
		assertEquals("Cell column getter", 4, cell.getColumn());
	}

	/**
	 * Test method for {@link dev.Cell#setRow(int)}.
	 */
	@Test
	public void testSetRow() {
		Cell cell = new Cell(5.0, 3, 4);
		cell.setRow(1);
		assertEquals("Cell row setter/getter", 1, cell.getRow());
	}

	/**
	 * Test method for {@link dev.Cell#setColumn(int)}.
	 */
	@Test
	public void testSetColumn() {
		Cell cell = new Cell(5.0, 3, 4);
		cell.setColumn(1);
		assertEquals("Cell column setter/getter", 1, cell.getColumn());
	}



	/*=====================================================================
	 * Tester: CARMELO
	 *=====================================================================
	 */
	@Test
	public void testCellDoubleString() {
		Cell cell = new Cell("A1=2*9", 0, 0);
		assertEquals("Cell Get Formula", "A1=2*9", cell.getFormula());
	}

	@Test
	public void testGetValue() {
		Cell cell = new Cell(74.2234, 34, 20);
		assertEquals("Cell Set Value", 74.2234, cell.getValue(), ACCEPTED_ERROR);
	}

	@Test
	public void testSetValue() {
		Cell cell = new Cell(92.42123, 5, 10);
		cell.setValue(422.4928);
		assertEquals("Cell Set Value", 422.4928, cell.getValue(), ACCEPTED_ERROR);
	}
	
	@Test
	public void testGetCellRow() {
		Cell cell = new Cell(0.0, 10, 10);
		assertEquals("Cell Row", 10, cell.getRow());
	}
	
	@Test
	public void testGetCellColumn() {
		Cell cell = new Cell(99.99, 99, 99);
		assertEquals("Cell Column", 99, cell.getColumn());
	}


	/*=====================================================================
	 * Tester: KARIM
	 *=====================================================================
	 */
	 
	/**
	 * Test method for {@link dev.Cell#getFormula()}.
	 */
	@Test
	public void testGetFormula0() {
		Cell a=new Cell();
		assertEquals("Equal Formula", "", a.getFormula());
	}
	/**
	 * Test method for {@link dev.Cell#getFormula()}.
	 */
	@Test
	public void testGetFormula1() {
		Cell a=new Cell("testing", 0, 0);
		assertEquals("Equal Formula", "testing", a.getFormula());
	}
	/**
	 * Test method for {@link dev.Cell#getFormula()}.
	 */
	@Test
	public void testGetFormula2() {
		Cell a=new Cell(0,1,2);
		assertEquals("Equal Formula", "", a.getFormula());
	}

	/**
	 * Test method for {@link dev.Cell#hasFormula()}.
	 */
	@Test
	public void testHasFormula0() {
		Cell a=new Cell();
		assertEquals("Equal Formula", false, a.hasFormula());
	}
	/**
	 * Test method for {@link dev.Cell#hasFormula()}.
	 */
	@Test
	public void testHasFormula1() {
		Cell a=new Cell("testing", 0, 0);
		assertEquals("Equal Formula", true, a.hasFormula());
	}
	/**
	 * Test method for {@link dev.Cell#hasFormula()}.
	 */
	@Test
	public void testHasFormula2() {
		Cell a=new Cell(0,1,2);
		assertEquals("Equal Formula", false, a.hasFormula());
	}

	/**
	 * Test method for {@link dev.Cell#hasValue()}.
	 */
	@Test
	public void testHasValue0() {
		Cell a=new Cell();
		assertEquals("Value",true,a.hasValue());
	}
	/**
	 * Test method for {@link dev.Cell#hasValue()}.
	 */
	@Test
	public void testHasValue1() {
		Cell a=new Cell("", 0, 0);
		assertEquals("Value", false, a.hasValue());
	}
	/**
	 * Test method for {@link dev.Cell#hasValue()}.
	 */
	@Test
	public void testHasValue2() {
		Cell a=new Cell(0,1,2);
		assertEquals("Value",true,a.hasValue());
	}
	/**
	 * Test method for {@link dev.Cell#toString()}.
	 */
	@Test
	public void testToString0() {
		Cell a=new Cell();
		assertEquals("Value String",""+((double)0),a.toString());
	}
	/**
	 * Test method for {@link dev.Cell#toString()}.
	 */
	@Test
	public void testToString1() {
		Cell a= new Cell("", 0, 0);
		assertEquals("Value String",""+((double)0),a.toString());
	}
	/**
	 * Test method for {@link dev.Cell#toString()}.
	 */
	@Test
	public void testToString2() {
		Cell a=new Cell(1,2,3);
		assertEquals("Value String",""+((double)1),a.toString());
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals0() {
		Cell a=new Cell();
		Cell b=new Cell();
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals1() {
		Cell a=new Cell();
		Cell b=a;
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals2() {
		Cell a=new Cell();
		Cell b=new Cell("",0, 0);
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals3() {
		Cell a=new Cell("", -1, -1);
		Cell b=new Cell("", -1, -1);
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals4() {
		Cell a=new Cell("test",0, 0);
		Cell b=new Cell();
		assertEquals("Cell Object",false,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals5() {
		Cell a=new Cell(0,1,2);
		Cell b=new Cell();
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals6() {
		Cell a=new Cell(123,4,5);
		Cell b=new Cell(123,6,7);
		assertEquals("Cell Object",true,a.equals(b));
	}
	/**
	 * Test method for {@link dev.Cell#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals7() {
		Cell a=new Cell("",0, 0);
		Cell b=new Cell(0,1,2);
		assertEquals("Cell Object",true,a.equals(b));
	}
}
