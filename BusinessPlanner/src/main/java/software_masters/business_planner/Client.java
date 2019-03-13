/**
 * 
 */
package software_masters.business_planner;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author pharies
 *
 */
public class Client implements Serializable
{
	public String userName;
	public String password;
	public boolean login;
	public Server server;
	public BusinessPlanner editable;
	
	public Client()
	{
		
	}
	public Client(Server proxy) throws AccessException, RemoteException, NotBoundException
	{
		

		this.server = proxy;
		
		this.login = false;
	}
	
	
	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/**
	 * @return the login
	 */
	public boolean isLogin()
	{
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(boolean login)
	{
		this.login = login;
	}
	/**
	 * @return the server
	 */
	public Server getServer()
	{
		return server;
	}
	/**
	 * @param server the server to set
	 */
	public void setServer(Server server)
	{
		this.server = server;
	}
	/**
	 * @return the editable
	 */
	public BusinessPlanner getEditable()
	{
		return editable;
	}
	/**
	 * @param editable the editable to set
	 */
	public void setEditable(BusinessPlanner editable)
	{
		this.editable = editable;
	}
	/**
	 * 
	 * @param name
	 * @param pass
	 * 
	 * login for admins
	 * checks if the server has the username and password in a hash, then sets private variables within client
	 * @throws RemoteException 
	 * 
	 */
	public void adminLogin(String name, String pass) throws RemoteException
	{
		login = server.adminLogin(name, pass);
		if(login == true)
		{
			userName = name;
			password = pass;
			
		}
		else
		{
			
			throw new IllegalArgumentException("that is not correct username and/or password");
		}
	}
	
	
	
	/**
	 * 
	 * @param name
	 * @param pass
	 * 
	 * login for users
	 * checks if the server has the username and password in a hash, then sets private variables within client
	 * @throws RemoteException 
	 * 
	 */
	public void userLogin(String name, String pass) throws RemoteException
	{	
		login = server.userLogin(name, pass);
		if(login == true)
		{
			userName = name;
			password = pass;
			
		}
		else
		{
			
			throw new IllegalArgumentException("that is not correct username and/or password");
		}

	}
	
	
	
	/**
	 * 
	 * @param name
	 * @param Username
	 * @param Password
	 * @param deptName
	 * @param access
	 * 
	 * creates the new user sends to server
	 * @throws RemoteException 
	 * 
	 */
	public void addUser(String name, String Username, String Password, String deptName, boolean access) throws RemoteException
	{
		
		server.addUser(userName, password, name, Username, Password, deptName, access);
		
		
		
	}
	
	
	
	/**
	 * 
	 * @param planName
	 * 
	 * gets a plan from the server from the string planName
	 * @throws RemoteException 
	 * 
	 */
	public void getPlan(String planName) throws RemoteException
	{
		if(login==true)
		{
			editable = server.getPlan(userName, password, planName);
			
		}
		
	}
	
	/**
	 * 
	 * @param name  =  name of new plan
	 * 
	 * the server takes the name and the plan that the client has and makes a copy, 
	 * @throws RemoteException 
	 */
	public void copyPlan(String name) throws RemoteException
	{
		if(login == true)
		{
			server.copyPlan(userName, password, editable, name);
		}
	}
	
	
	
	/**
	 * 
	 * saves the plan that is currently being worked on
	 * @throws RemoteException 
	 * 
	 */
	public void savePlan() throws RemoteException
	{
		if(login == true)
		{
			server.updatePlan(userName, password, editable);
		}
		
	}
	
	
	
	/**
	 * 
	 * changes the edit status of the plan
	 * @throws RemoteException 
	 * 
	 */
	public void changeEdit() throws RemoteException
	{
		if(login == true)
		{
			server.setEdit(userName, password, editable);
		}
		
		
	}
	
	/**
	 *
	 * @param user
	 * @param dev
	 * @param edit
	 * 
	 * creates a new plan based of user dev and edit
	 * @throws RemoteException 
	 */
	public void createPlan(String user, String dev, boolean edit) throws RemoteException
	{
		
		server.createPlan(userName, password, user, dev, edit);
		
	}
	
	
	
	
	
	
	
}
