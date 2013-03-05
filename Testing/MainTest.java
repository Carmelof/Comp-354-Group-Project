/*How to see the tests:
 * 
 * For those of you not familiar with JUnit, it comes already installed if using eclipse
 * to see the tests right click on the Testing_it1.java then "Run As"-> JUnit Test
 * 
 * These tests are not automatic and need some edits to get them working (for all our parts)
 * 1st all the tested classes in the main methods ( and some variables) need their visibility
 * changed to public so we can access them
 * 2nd the while loop in the main needs to be commented out so the tests will run without
 * interruption 
 * 
 * This is due to the application's design
 */
 
package Testing;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.omg.PortableInterceptor.SUCCESSFUL;
import com.sun.net.httpserver.Authenticator.Success;
import dev.Main;

public class MainTest {
	// Acceptable error for comparing doubles
	public final double ACCEPTED_ERROR = 1E-10;
	
    @Rule
   	public ExpectedException exceptionThrown = ExpectedException.none();

	@Test
	public final void testMain() {
		Main application = new Main();
		// No errors expected
	}
@Test
	public final void testUpdateTable() {
		fail("Not yet implemented"); // TODO
		//Unable to test, no returnable data by method is testable.
	}
}
