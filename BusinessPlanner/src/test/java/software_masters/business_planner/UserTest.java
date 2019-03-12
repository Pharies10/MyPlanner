/**
 * 
 */
package software_masters.business_planner;


import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * @author pharies
 *
 */
public class UserTest extends TestCase
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		test();

	}

	public static void test()
	{
		// make User
		User John = new User("john", "Jonny", "1234", "CSC", true);
		
		// make variables for every get method
		String Name = John.getName();
		String UserName = John.getUserName();
		String Password = John.getPassword();
		String Dept = John.getDeptName();
		boolean admin = John.isAdmin();
		
		// test them
		Assert.assertEquals("john", Name);
		Assert.assertEquals("Jonny", UserName);
		Assert.assertEquals("1234", Password);
		Assert.assertEquals("CSC", Dept);
		Assert.assertEquals(true, admin);
		
				
	}
	
}
