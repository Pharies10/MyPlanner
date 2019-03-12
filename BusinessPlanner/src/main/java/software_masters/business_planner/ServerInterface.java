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
	void addUser(String key, String value, String name, String userName, String password, String deptName, boolean access);
	boolean adminLogin(String username, String password);
	boolean userLogin(String username, String password);
	BusinessPlanner getPlan(String username, String password, String planName);
	void updatePlan(String username, String password, BusinessPlanner plan);
}
