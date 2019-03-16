/**
 * 
 */
package software_masters.business_planner;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.beans.*;
import java.rmi.Remote;

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
	public ConcurrentHashMap<String, User> admins;
	public ConcurrentHashMap<String, User> users;
	public ArrayList<Department> dept;
	private static final long serialVersionUID = 1L;
	public ServerInterface proxy;
	
	
	public Server() throws RemoteException 
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
	public boolean adminAccess(String username, String password)
	{
		boolean check = admins.containsKey(username);
		if (check == true)
		{
			
			User user = admins.get(username);
			if(user.getPassword().equals(password))
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
	public boolean userAccess(String username, String password)
	{
		boolean check = users.containsKey(username);
		if (check == true)
		{
			
			User user = users.get(username);
			if(user.getPassword().equals(password))
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
	public boolean checkAdmin(String userName)
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
	public Department getDept(String name)
	{
		for(int i=0; i<dept.size(); i++)
		{
			if(dept.get(i).getDepartmentName().equals(name))
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
			if(copy == true)
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
			if(copy == true)
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
		
		
		Template temp = department.getPlan(planName);
		
		BusinessPlanner plan = new BusinessPlanner(temp.getDeveloperTemplateName(), temp, null);
		
		boolean edit = temp.isEdit();
		
		if(edit == true)
		{
			return plan;
		}
		else if (check == true && edit == false)
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
	 * @param user
	 * @param dev
	 * @param access
	 * 
	 * 
	 * makes a new businessplanner and adds to list
	 * 
	 */
	public void createPlan(String username, String password, String user, String dev, boolean access)
	{
		boolean check = adminAccess(username, password);
		User person;
		if(check == true)
		{
			 person = admins.get(username);
		}
		else
		{
			 throw new IllegalArgumentException("the client cannot make k plan");
		}
		
		Template planner = new Template(dev, user, null, access);
		planner = Template.loadDeveloperTemplate(dev);
		planner.setEdit(access);
		planner.setUserTemplateName(user);
		
		String deptName = person.getDeptName();
		
		Department department = getDept(deptName);
		
		department.addPlan(planner);
		
		save(department);
		
		
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
		
		Template temp = plan.getUserTemplate();
		
		String deptName = person.getDeptName();
		
		Department department = getDept(deptName);
		
		department.updatePlan(temp);
		
		save(department);

	}
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param Plan
	 * @param name  =  the new name of the business plan
	 * 
	 * takes the plan given and makes a copy, 
	 * since a deep copy is sent back and forth can just assign variable to it
	 * 
	 */
	public void copyPlan(String username, String password, BusinessPlanner plan, String name)
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
		
		plan.getUserTemplate().save();
		Template newTemp = new Template();
		newTemp = Template.userLoad(plan.getUserTemplate().getUserTemplateName());
		newTemp.setUserTemplateName(name);
		
		
		
		
		department.addPlan(newTemp);
		
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
				Department department = (Department) decoder.readObject();
				
				dept.add(department);
				
				makeHashes(department);
				
				
			}

			
		}
			
	}
	
	/**
	 * 
	 * @param department
	 * 
	 * makes the hashes at the beginning of the server when the departments are loaded.
	 * 
	 */
	public void makeHashes(Department department)
	{
		for(int i = 0; i < department.getAdmin().size(); i++)
		{
	
			
			admins.put(department.getAdmin().get(i).getUserName(), department.getAdmin().get(i));

			
		}
		
		for(int i = 0; i < department.getUsers().size(); i++)
		{
			
			users.put(department.getUsers().get(i).getUserName(), department.getUsers().get(i));

			
		}
		
		
		
		
		
	}
	
	
	/**
	 *
	 * @param username
	 * @param password
	 * @param plan
	 * 
	 * 
	 * sets the plan editability to the opposite of what it is now
	 * since edit is a private variable within the plan, it has to be changed by the department.
	 * 
	 */
	public void setEdit(String username, String password, BusinessPlanner plan)
	{
		
		boolean check = adminAccess(username, password);
		User person;
		if(check == true)
		{
			 person = admins.get(username);
		}
		else
		{
			throw new IllegalArgumentException("you are not an admin");
		}
		
		Template temp = plan.getUserTemplate();
		
		String deptName = person.getDeptName();
		
		Department department = getDept(deptName);
		
		department.edit(temp);
		
		save(department);
	}
	
	
	
	
	
	
	
	
	
}
