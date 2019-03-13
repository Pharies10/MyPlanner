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

	public String Name;
	public String userName;
	public String password;
	public String deptName;
	public boolean admin;
	
	
	

	public User()
	{
		
	}


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
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		Name = name;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}


	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}


	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin)
	{
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
