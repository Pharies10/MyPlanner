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
		Template planner = new Template("VMOSA", "test1",null,  true);
		dept.addPlan(planner);
		Template planner2 = new Template("OKR", "test2",null,  false);
		dept.addPlan(planner2);
		Template planner3 = new Template("Centre_Assessment", "test3",null,  true);
		dept.addPlan(planner3);
		
		
		
		// check edit methods
		
		boolean check = dept.getEdit(planner);
		Assert.assertEquals(true, check);
		
		dept.edit(planner);
		check = dept.getEdit(planner);
		Assert.assertEquals(false, check);
		
		
		// test to make sure you can get the plans
		Template test1 = dept.getPlan("test1");
		Assert.assertEquals(test1, planner);
		Template test2 = dept.getPlan("test2");
		Assert.assertEquals(test2, planner2);
		Template test3 = dept.getPlan("test3");
		Assert.assertEquals(test3, planner3);
		
		BusinessPlanner plan = new BusinessPlanner(test1.getDeveloperTemplateName(), test2, null);
		
		plan.setCurrent(plan.getCurrent().getChild(0).getChild(0)); // objective level
		
		
		// test update 
		
		// add branch
		TemplateSection edit = plan.addBranch();
		edit.setName("CHECK");
		
		// update the plan within the dept
		dept.updatePlan(plan.getUserTemplate());
		
		// get copy
		Template testUpdate = dept.getPlan("test1");
		
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
