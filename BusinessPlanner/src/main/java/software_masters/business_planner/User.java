/**
 * 
 */
package software_masters.business_planner;

/**
 * @author pharies
 *
 */
public class User
{

	protected String Name;
	protected String userName;
	protected String password;
	protected String deptName;
	protected boolean admin;
	
	
	




	/**
	 * @param name
	 * @param userName
	 * @param password
	 * @param deptName
	 * @param admin
	 */
	public User(String name, String userName, String password, String deptName, boolean admin)
	{
		
		this.Name = name;
		this.userName = userName;
		this.password = password;
		this.deptName = deptName;
		this.admin = admin;
	}



	/**
	 * @return the name
	 */
	public String getName()
	{
		return Name;
	}



	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}



	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}



	/**
	 * @return the deptName
	 */
	public String getDeptName()
	{
		return deptName;
	}



	/**
	 * @return the admin
	 */
	public boolean isAdmin()
	{
		return admin;
	}



	
	
	
	
	
}
