/**
 * 
 */
package software_masters.business_planner;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



/**
 * @author pharies
 *
 */
public class Server implements ServerInterface, Serializable
{
	private ConcurrentHashMap<String, User> admins;
	private ConcurrentHashMap<String, User> users;
	private ArrayList<Department> dept;
	private static final long serialVersionUID = 1L;
	
	
	public Server()
	{
		this.admins = new ConcurrentHashMap<String, User>(50);
		this.users = new ConcurrentHashMap<String, User>(100);
		this.dept = new ArrayList<Department>();
		
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * checks the admins hash for the key
	 * then checks if the password of that key matches the password given
	 * if it does return a true
	 * any other case return false
	 * 
	 * used for login 
	 * used for a check for certain actions that only admins can perform
	 * used to identify a user within the admin hash
	 * 
	 */
	private boolean adminAccess(String username, String password)
	{
		boolean check = admins.containsKey(username);
		if (check == true)
		{
			
			User user = admins.get(username);
			if(user.getPassword() == password)
			{
				return true;
			}
			
			
		}
		return false;
		
	}
	
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * checks the users hash for the key
	 * then checks if the password of that key matches the password given
	 * if it does return a true
	 * any other case return false
	 * 
	 * used for login
	 * 
	 */
	private boolean userAccess(String username, String password)
	{
		boolean check = users.containsKey(username);
		if (check == true)
		{
			
			User user = users.get(username);
			if(user.getPassword() == password)
			{
				return true;
			}
			
			
		}
		return false;

		
	}
	

	
	/**
	 * 
	 * @param userName
	 * @return boolean
	 * 
	 * checks if the username given is in admin hash 
	 * 
	 */
	private boolean checkAdmin(String userName)
	{
		boolean check = admins.containsKey(userName);
		return check;
	}
	
	
	
	/**
	 * 
	 * @param userName
	 * @return boolean
	 * 
	 * checks if the username is in users hash
	 * 
	 */
	private boolean checkUser(String userName)
	{
		boolean check = users.containsKey(userName);
		return check;
	}
	
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * 
	 * checks the array list dept for a certain department
	 * returns null if the department doesn't exists
	 *
	 */
	private Department getDept(String name)
	{
		for(int i=0; i<dept.size(); i++)
		{
			if(dept.get(i).getDepartmentName() == name)
			{
				return dept.get(i);
			}
		}
		return null;
	}
	
	
	
	/**
	 * @param key  =  the client username
	 * @param value  =  the client password
	 * 
	 * values of the user that is going to be made
	 * @param name
	 * @param userName
	 * @param password
	 * @param deptName
	 * @param access
	 * 
	 * Makes a new user and adds to the correct hash
	 * has to first check for the admin access
	 * then checks for an non existent department
	 * Then checks to make sure the user is not in the hash already
	 * will throw an exception if the user trying to be added is in the has 
	 * 
	 */
	public void addUser(String key, String value, String name, String userName, String password, String deptName, boolean access)
	{
		boolean lock = adminAccess(key, value);
		if(lock == false)
		{
			throw new IllegalArgumentException("You are not an admin");
		}
		
		Department department = getDept(deptName);
		if(department == null)
		{
			throw new IllegalArgumentException("there is no department with this name");
		}
		
		User user = new User(name, userName, password, deptName, access); 
		department.addUser(user);
		
		if(access == true)
		{
			boolean copy = checkAdmin(userName);
			if(copy == false)
			{
				throw new IllegalArgumentException("the user that is being added is already in the server");
			}
			else
			{
				admins.put(userName, user);
			}
			
		}
		else
		{
			boolean copy = checkUser(userName);
			if(copy == false)
			{
				throw new IllegalArgumentException("the user that is being added is already in the server");
			}
			else
			{
				users.put(userName, user);
			}
		}
	}
	


	/**
	 * 
	 * @param username 
	 * @param password
	 * 
	 * admin method called by client to login, returns boolean check
	 * 
	 */
	public boolean adminLogin(String username, String password)
	{
		boolean check = adminAccess(username, password);
		return check;
	}

	
	
	/**
	 * 
	 * @param username 
	 * @param password
	 * 
	 * user method called by client to login, returns boolean check
	 * 
	 */
	public boolean userLogin(String username, String password)
	{
		boolean check = userAccess(username, password);
		return check;
	}

	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param planName 
	 * 
	 * checks admin list to figure out whether admin or user
	 * gets the department and then grabs plan from the department
	 * checks also if plan is editable
	 * if the plan is not editable, then only admin can get it
	 * else an exception thrown
	 * 
	 */
	public BusinessPlanner getPlan(String username, String password, String planName)
	{
		boolean check = adminAccess(username, password);
		User person;
		if(check == true)
		{
			 person = admins.get(username);
		}
		else
		{
			 person = users.get(username);
		}
		

		String deptName = person.getDeptName();
		
		Department department = getDept(deptName);
		
		
		BusinessPlanner plan = department.getPlan(planName);
		
		
		boolean edit = department.getEdit(plan);
		
		if(edit == true)
		{
			return plan;
		}
		else if (check == true)
		{
			return plan;
		}
		else 
		{
			throw new IllegalArgumentException("the client cannot edit this plan");
		}
		
		
	}

	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param planName 
	 * 
	 * finds the person to find the department
	 * updates it from the update method within department
	 * 
	 */
	public void updatePlan(String username, String password, BusinessPlanner plan)
	{
		boolean check = adminAccess(username, password);
		User person;
		if(check == true)
		{
			 person = admins.get(username);
		}
		else
		{
			 person = users.get(username);
		}
		

		String deptName = person.getDeptName();
		
		Department department = getDept(deptName);
		
		department.updatePlan(plan);
		
		save(department);

	}
	
	
	

	/**
	 * 
	 * @param dept
	 * 
	 * will be called when plan is updated and saves entire department
	 * 
	 */
	public void save(Department dept)
	{
		String fileName = dept.getDepartmentName();
		
		XMLEncoder encoder=null;
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(dept);
		encoder.close();
		
		
		
		
		
	}

	
	
	/**
	 * 
	 * @param names
	 * 
	 * loads the plans at start
	 * will not be able to do is the dept list is full
	 * 
	 */
	public void load(ArrayList<String> names)
	{
		boolean empty = dept.isEmpty();
		if(empty == false)
		{
			return;
		}
		else
		{
			for(int i = 0; i < names.size(); i++)
			{
				String fileName = names.get(i);
				
				XMLDecoder decoder=null;
				
				try 
				{
					decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
				} 
					catch (FileNotFoundException e) 
				{
						System.out.println("ERROR: File dvd.xml not found");
				}
				Department department = (Department)decoder.readObject();
				
				dept.add(department);
				
				
			}

			
		}
			
	}
	
}
