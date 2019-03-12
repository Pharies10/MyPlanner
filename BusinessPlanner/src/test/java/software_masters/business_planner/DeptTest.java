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
public class DeptTest extends TestCase
{
	public static void test() 
	{
		// creates department
		Department dept = new Department("CSC");
		
		// checks department name
		Assert.assertEquals("CSC", dept.getDepartmentName());
		
		// add three different planners to the dept
		BusinessPlanner planner = new BusinessPlanner("VMOSA", "test1");
		dept.addPlan(planner);
		BusinessPlanner planner2 = new BusinessPlanner("OKR", "test2");
		dept.addPlan(planner2);
		BusinessPlanner planner3 = new BusinessPlanner("Centre_Assessment", "test3");
		dept.addPlan(planner3);
		
		
		// test to make sure you can get the plans
		BusinessPlanner test1 = dept.getPlan("test1");
		Assert.assertEquals(test1, planner);
		BusinessPlanner test2 = dept.getPlan("test2");
		Assert.assertEquals(test2, planner2);
		BusinessPlanner test3 = dept.getPlan("test3");
		Assert.assertEquals(test3, planner3);
		
		
		
		test1.setCurrent(planner.getCurrent().getChild(0).getChild(0)); // objective level
		
		
		// test update 
		
		// add branch
		TemplateSection edit = test1.addBranch();
		edit.setName("CHECK");
		
		// update the plan within the dept
		dept.updatePlan(test1);
		
		// get copy
		BusinessPlanner testUpdate = dept.getPlan("test1");
		
		//test
		Assert.assertEquals(testUpdate, test1);
		
		
		
		// test users
		
		//create 2 unique users
		User John = new User("john", "Jonny", "1234", "CSC", true);
		User Bob = new User("bob", "Bobby", "4321", "CSC", false);
		
		// add users too dept
		dept.addUser(John);
		dept.addUser(Bob);
		
		// test getting the users
		User testAdmin = dept.getUser("john", true);
		User testUser = dept.getUser("bob", false);
		
		
		//test adding and getting
		Assert.assertEquals(testAdmin, John);
		Assert.assertEquals(testUser, Bob);
		
		
		
		
		
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		test();

	}

}
