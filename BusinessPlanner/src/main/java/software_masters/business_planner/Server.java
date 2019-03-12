/**
 * 
 */
package software_masters.business_planner;

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
	 * checks the admins has for the key
	 * then checks if the password of that key matches the password given
	 * if it does return a true
	 * any other case return false
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
		
		
		User user = new User(name, userName, password, deptName, access); 
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
	


	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#adminLogin(java.lang.String, java.lang.String)
	 */
	public boolean adminLogin(String username, String password)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#userLogin(java.lang.String, java.lang.String)
	 */
	public boolean userLogin(String username, String password)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#getPlan(java.lang.String, java.lang.String, java.lang.String)
	 */
	public BusinessPlanner getPlan(String username, String password, String planName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#updatePlan(java.lang.String, java.lang.String, software_masters.business_planner.BusinessPlanner)
	 */
	public void updatePlan(String username, String password, BusinessPlanner plan)
	{
		// TODO Auto-generated method stub

	}

}
