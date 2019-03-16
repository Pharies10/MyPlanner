/**
 * 
 */
package software_masters.business_planner;



import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * @author pharies
 *
 * since the server and client are linked, testing them together tests all functions of a server and a client.
 *
 *
 */
public class ServerClientTest extends TestCase
{
	
	static Server serv;
	static Registry reg;
	static Server prox;
	static ArrayList<String> deptNames;

	/**
	 * @param args
	 * @throws AlreadyBoundException 
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * 
	 * sets up the server and registery
	 * 
	 */
	public static void main(String[] args) throws AlreadyBoundException, RemoteException, NotBoundException
	{
		
        serv = new Server();
        
        deptNames = new ArrayList<String>();
        setServer();
        
        serv.load(deptNames);
        
        
        reg = LocateRegistry.createRegistry(1099);
        
        reg.rebind("testServer", serv);
        prox = (Server) reg.lookup("testServer");
        System.out.println("server ready");
        
        
        
        
        
        
		test();

	}
	
	/**
	 * sets the server to run the test
	 * adds admins and users
	 */
	public static void setServer()
	{
		
		// also test the second constructor of business plan
		
		// creates 2 admin lists and users lists for the depts
		ArrayList<User> usersList1 = new ArrayList<User>();
		ArrayList<User> usersList2 = new ArrayList<User>();
		
		ArrayList<User> adminList1 = new ArrayList<User>();
		ArrayList<User> adminList2 = new ArrayList<User>();
		
		
		// creates the users and adds to lists
		User user1 = new User("user1", "a", "1", "CSC", false);
		User user2 = new User("user2", "b", "2", "CSC", false);
		User user3 = new User("user3", "c", "3", "CSC", false);
		usersList1.add(user1);
		usersList1.add(user2);
		usersList1.add(user3);
		User user4 = new User("user4", "d", "4", "English", false);
		User user5 = new User("user5", "e", "5", "English", false);
		User user6 = new User("user6", "f", "6", "English", false);
		usersList2.add(user4);
		usersList2.add(user5);
		usersList2.add(user6);
		
		// creats the admins and adds to list
		User admin1 = new User("admin1", "a", "1", "CSC", true);
		User admin2 = new User("admin2", "b", "2", "English", true);
		adminList1.add(admin1);
		adminList2.add(admin2);
		
		Department CSC = new Department();
		CSC.setDepartmentName("CSC");
		CSC.setAdmin(adminList1);
		CSC.setUsers(usersList1);
		Department English = new Department("English", adminList2, usersList2);
		English.setDepartmentName("English");
		English.setAdmin(adminList2);
		English.setUsers(usersList2);
		
		
		
		
		

		
		
	
		
		

		
		deptNames.add("CSC");
		deptNames.add("English");
		
		
		serv.save(CSC);
		serv.save(English);
	}
	public static void test() throws AccessException, RemoteException, NotBoundException
	{
		
		
			
		
		Client joe = new Client(prox);
		
		//test login
		joe.adminLogin("a", "1");
		
		Assert.assertEquals(joe.userName, "a");
		Assert.assertEquals(joe.password, "1");
		Assert.assertEquals(joe.login, true);
		
		
		Client bob = new Client(prox);
		
		//test login
		bob.userLogin("a", "1");
		
		Assert.assertEquals(joe.userName, "a");
		Assert.assertEquals(joe.password, "1");
		Assert.assertEquals(joe.login, true);
		
		
		// check add user
		joe.addUser("jonny", "testUser", "9", "CSC", false);
		
		Assert.assertEquals(serv.users.size(), 7);

		
		try 
		{
			bob.addUser("jonny", "testUser2", "9", "CSC", false);
		
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		
		
		// test creating a plan and getting that plan
		
		joe.createPlan("myPlan", "VMOSA", true);
		
		joe.getPlan("myPlan");
		
		Assert.assertEquals(joe.editable.getUserTemplate().getUserTemplateName(), "myPlan");
		System.out.println(joe.editable.getUserTemplate().getUserTemplateName());
		
		System.out.println(joe.editable.getCurrent().getCategory());
		joe.getEditable().setCurrent(joe.getEditable().getCurrent().getChild(0).getChild(0));
		joe.editable.addBranch();
		
		// save / copy / update unavaiable until
		//java.lang.IllegalAccessException: class sun.reflect.misc.Trampoline cannot access a member of class software_masters.business_planner.BusinessPlanner with modifiers "public"
		// is figured out
		joe.savePlan();
		
		joe.getPlan("myPlan");
		
		Assert.assertEquals(joe.editable.getUserTemplate().getUserTemplateName(), "myPlan");
		
		joe.changeEdit();
		joe.getEditable().setCurrent(joe.getEditable().getCurrent().getChild(0).getChild(0));
		TemplateSection copy2 = joe.getEditable().getCurrent();	
		Assert.assertEquals(joe.getEditable().getCurrent().getParent().getChildren().size(), 2);
		
		TemplateSection copy = joe.getEditable().addBranch();
		


		
		Assert.assertEquals(joe.getEditable().getCurrent().getParent().getChildren().size(), 3);
		
		
		
		joe.copyPlan("a");
		
		
		
		
		
		joe.getPlan("a");
		
		copy2 = joe.getEditable().getCurrent().getChild(0).getChild(1);
	
		
		Assert.assertEquals(copy2.getCategory(), "Objectives");
		System.out.println("hello");
		joe.getEditable().setCurrent(joe.getEditable().getCurrent().getChild(0).getChild(1));
		System.out.println(joe.getEditable().getCurrent().getCategory());
		Assert.assertEquals(joe.getEditable().getCurrent().getParent().getChildren().size(), 3);
		
		joe.savePlan();
		
		
		
		try 
		{
			bob.getPlan("myPlan");
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		
		try 
		{
			bob.createPlan("myPlan2", "VMOSA", true);
		} 
		catch (IllegalArgumentException e)
		{
			  e.getMessage();
		}
		
		
	}
	
	
	
}
