import java.io.*;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.util.*;

public class tryout2 {
// tryouts by Long Wang, id 9547967
	/**
	 * @param args
	 */
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		
		String CC;    
		 int CCLen;
		   char c;
	      Scanner na = new Scanner(System.in);
	      System.out.print("Enter a cellname or formula: ");
	      CC = na.next();   // use next() to read String
	   // CCLen = CC.length();
	    
	//	byte b[] = CC.getBytes(); // changing the string into array of bytes
	//	ByteArrayInputStream in = new ByteArrayInputStream(b);
		
		//if (CC.length()=2){
			// first assuming for example A6, will have to get first index, then 2nd index
			// then setting to a specific cell
			// then add a star, first using a get bla bla, get value and add a *
		
		if (CC.matches("^[A-K][1-10]$"))
		{
			return CellTable.getCell(CC).setValue(CC+"*");
			
			
			
		}
		
		else if (CC.matches("[A-K][1-10]=[A-K][1-10]+[A-K][1-10]$")){
			
			CC.split("=");
			CC.split("+");
			
			return    TableCell.getCell(CC[0]).setValue()= TableCell.getCell(CC[1]).getValue() + TableCell.getCell(CC[2]).getValue();
							
		//for(int i=0; i<1; i++){
		//	char d=in.read();
		//	for((d=in.read())>="A" && (d=in.read())<="K"){    nvm about this
			// tryouts by Long Wang, id 9547967
			
		}
		
		
		else if (CC.matches("^[A-K][1-10]=[A-K][1-10]-[A-K][1-10]$")){
			
			CC.split("=");
			CC.split("+");
			
			return    TableCell.getCell(CC[0]).setValue()= TableCell.getCell(CC[1]).getValue() - TableCell.getCell(CC[2]).getValue();
							
		
		}
		else if (CC.matches("^[A-K][1-10]=[A-K][1-10]*[A-K][1-10]$")){
			
			CC.split("=");
			CC.split("+");
			
			return    TableCell.getCell(CC[0]).setValue()= TableCell.getCell(CC[1]).getValue() * TableCell.getCell(CC[2]).getValue();
							
		
		}
			
		else if (CC.matches("^[A-K][1-10]=[A-K][1-10]/[A-K][1-10]$")){
			
			CC.split("=");
			CC.split("+");
			
			return    TableCell.getCell((CC[0])).setValue()= TableCell.getCell(CC[1]).getValue() / TableCell.getCell(CC[2]).getValue();
							// how to make this number a float?
		
		}
		
		else if (CC.matches("Exit")){
			System.out.println("You ar enow exiting....");
			System.exit(0);
			
		}
		
		
		else {
			
			System.out.println("You have entered it wrong, please remember not to put spaces nor lower case letters.");
			 CC = na.next();
			 // maybe this should be done in a while loop hmmmm
		}
		
		
		
		
	    // sample 
	//	String tmp = "abc";
	//	byte b[] = tmp.getBytes();
	//	ByteArrayInputStream in = new ByteArrayInputStream(b);
	//	for (int i=0; i<2; i++) {
	//	int c;
	//	while ((c = in.read()) != -1) {
	//	if (i == 0) {
	//	System.out.print((char) c);
	//	} else {
	//	System.out.print(Character.toUpperCase((char) c));
	//	}
	//	}
	//	System.out.println();
	//	in.reset();
		
		// tryouts by Long Wang, id 9547967
		// so far worked it by myself, hopefully Simon and I will confer
		// about this and work out the bugs together on 30/01/2013
		
		
		
	}

}
