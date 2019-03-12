package software_masters.business_planner;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * This class tests the program by using example business plans
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * @since 2019-02-23
 */

public class BusinessPlannerTest extends TestCase
{

	/**
	 * This method: demonstrates load from xml get and set current template section
	 * demonstrates structure navigation demonstrates add and remove constraints
	 * demonstrates adding a branch for the VMOSA business plan
	 */
	public void testVMOSA()
	{
		BusinessPlanner planner = new BusinessPlanner("VMOSA","myVMOSA", true);

		// this method loads template from xml
		//planner.chooseTemplate("VMOSA", "myVMOSA");

		// tests adding an extra vision. should return null
		Assert.assertNull(planner.getCurrent().deepCopy());

		// tests removing mission
		Assert.assertFalse(planner.getCurrent().removeChild(planner.getCurrent().getChild(0)));

		String[] cats = new String[] { "Mission", "Objectives", "Strategies", "Action Plans" };
		Assert.assertEquals("Vision", planner.getCurrent().getCategory());
		for (String cat : cats)
		{
			planner.setCurrent(planner.getCurrent().getChild(0));
			Assert.assertEquals(cat, planner.getCurrent().getCategory());
		}

		// tests the addition of a new branch
		planner.setCurrent(planner.getCurrent().getParent().getParent());// objective level
		TemplateSection curCopy = planner.addBranch();
		curCopy.setName("Objective 2");
		planner.getCurrent().setName("Objective 1");
		
		planner.setCurrent(planner.getCurrent().getParent());
		Assert.assertEquals(planner.getCurrent().getChild(0).getName(), "Objective 1");
		Assert.assertEquals(planner.getCurrent().getChild(1).getName(), "Objective 2");

	}

	/**
	 * This method: demonstrates load from xml get and set current template section
	 * demonstrates structure navigation demonstrates add and remove constraints
	 * demonstrates adding a branch for the centre assessment plan
	 */
	public void testCentreAssessment()
	{
		BusinessPlanner planner = new BusinessPlanner("Centre_Assessment", "myCentre_Assessment", true);

		// this method loads template from xml
		//planner.chooseTemplate("Centre_Assessment", "myCentre_Assessment");

		// tests adding an extra college mission statement. should return null
		Assert.assertNull(planner.getCurrent().deepCopy());

		// tests removing department mission statement
		Assert.assertFalse(planner.getCurrent().removeChild(planner.getCurrent().getChild(0)));

		String[] cats = new String[] { "Program Mission", "Goals", "Student Learning Objectives", "Tools", "Targets" };
		Assert.assertEquals("College Mission", planner.getCurrent().getCategory());
		for (String cat : cats)
		{
			planner.setCurrent(planner.getCurrent().getChild(0));
			Assert.assertEquals(cat, planner.getCurrent().getCategory());
		}

		// tests the addition of a new branch
		planner.setCurrent(planner.getCurrent().getParent().getParent());// student learning level
		TemplateSection curCopy = planner.addBranch();
		curCopy.setName("SLOs 2");
		planner.getCurrent().setName("SLOs 1");
		planner.setCurrent(planner.getCurrent().getParent());
		Assert.assertEquals(planner.getCurrent().getChild(0).getName(), "SLOs 1");
		Assert.assertEquals(planner.getCurrent().getChild(1).getName(), "SLOs 2");
	}

	/**
	 * This method tests: demonstrates load from xml get and set current template
	 * section demonstrates structure navigation demonstrates add and remove
	 * constraints demonstrates adding a branch for objectives and key results plan
	 */
	public void testOKR()
	{
		BusinessPlanner planner = new BusinessPlanner("OKR","myOKR", true);

		// this method loads template from xml
		//planner.chooseTemplate("OKR", "myOKR");

		// tests adding an extra vision. should return null
		Assert.assertNull(planner.getCurrent().deepCopy());

		// tests removing mission
		Assert.assertFalse(planner.getCurrent().removeChild(planner.getCurrent().getChild(0)));

		String[] cats = new String[] { "Mission", "Long Term Goals", "Short Term Goals", "Objectives", "Key Results" };
		Assert.assertEquals("Vision", planner.getCurrent().getCategory());
		for (String cat : cats)
		{
			planner.setCurrent(planner.getCurrent().getChild(0));
			Assert.assertEquals(cat, planner.getCurrent().getCategory());
		}

		// tests the addition of a new branch
		planner.setCurrent(planner.getCurrent().getParent().getParent());// short term goals
		TemplateSection curCopy = planner.addBranch();
		curCopy.setName("STG 2");
		planner.getCurrent().setName("STG 1");
		planner.setCurrent(planner.getCurrent().getParent());
		Assert.assertEquals(planner.getCurrent().getChild(0).getName(), "STG 1");
		Assert.assertEquals(planner.getCurrent().getChild(1).getName(), "STG 2");

	}
}
