/**
 * 
 */
package software_masters.business_planner;

import java.io.Serializable;

/**
 * @author pharies
 *
 */
public class Server implements ServerInterface, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see software_masters.business_planner.ServerInterface#addUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void addUser(String name, String userName, String password, String deptName, boolean admin)
	{
		// TODO Auto-generated method stub

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
