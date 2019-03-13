/**
 * 
 */
package software_masters.business_planner;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author pharies
 *
 */
public interface ServerInterface extends Remote
{
	void addUser(String key, String value, String name, String userName, String password, String deptName, boolean access) throws RemoteException; 
	boolean adminLogin(String username, String password) throws RemoteException;
	boolean userLogin(String username, String password) throws RemoteException;
	BusinessPlanner getPlan(String username, String password, String planName)throws RemoteException;
	void updatePlan(String username, String password, BusinessPlanner plan)throws RemoteException;
	public void copyPlan(String username, String password, BusinessPlanner Plan, String name)throws RemoteException;
	void setEdit(String userName, String password, BusinessPlanner editable)throws RemoteException;
	void createPlan(String userName, String password, String user, String dev, boolean edit)throws RemoteException;
	boolean adminAccess(String username, String password);
	boolean userAccess(String username, String password);
	public void save(Department dept);
	
}
