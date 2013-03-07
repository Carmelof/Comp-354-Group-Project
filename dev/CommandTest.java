package dev;

import static org.junit.Assert.*;

import org.junit.Test;



public class CommandTest {

	public final double ACCEPTED_ERROR = 1E-10;
	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public final void testIsValid() 
	{
		Command command= new Command();
		assertEquals("testIsValid", true, command.isValid());
	} 

	
	@Test
	public final void testUpdateGrid() 
	{
		Grid grid= new Grid();
		Command command= new Command();
		grid.insertValue(5, 1, 1);
		grid.insertValue("A1 + 10", 1, 2);
		grid.insertValue(10, 1, 1);
		
		assertEquals("testUpdateGrid",20.0,grid.getCell(1, 2).getValue(), ACCEPTED_ERROR);
	}

	@Test
	public final void testIsAlphaNumeric() 
	{
		Command command= new Command();
		assertEquals("testIsAlphaNumeric", false, command.isAlphaNumeric());
	}

	
	@Test
	public final void testReplaceCellNamesByValue1() 
	{
		Command command= new Command();
		command.replaceCellNamesByValue("A2", 0.0);
		assertEquals("testIsAlphaNumeric","0.0",command.replaceCellNamesByValue("A2", 0.0));
	}
	@Test
	public final void testReplaceCellNamesByValue2() 
	{
		Command command= new Command();
		command.replaceCellNamesByValue("A3", 0.0);
		assertEquals("testIsAlphaNumeric","0.0",command.replaceCellNamesByValue("A3", 0.0));
	}
	@Test
	public final void testReplaceCellNamesByValue3() 
	{
		Command command= new Command();
		command.replaceCellNamesByValue("B4", 0.0);
		assertEquals("testIsAlphaNumeric","0.0",command.replaceCellNamesByValue("B4", 0.0));
	}
	
	@Test
	public final void testIsInRange() 
	{
		Command command= new Command();
		assertEquals("testIsInRange", true, command.isInRange());
	}
	
	@Test
	public final void testIsValidEquation() 
	{
		Command command= new Command();
		assertEquals("testIsValidEquation", true, command.isValidEquation());
	}
	

	

}

