/**
 * 
 */
package software_masters.business_planner;

import java.util.ArrayList;
import java.io.Serializable;
import java.beans.*;

/**
 * @author pharies
 *
 */
public class Department implements Serializable
{
	/**
	 * 
	 */
	
	private String departmentName;
	private ArrayList<User> admin;
	private ArrayList<User> users;
	private ArrayList<BusinessPlanner> plans;
	
	
	public Department()
	{
		this("not a department");
	}
	
	
	/**
	 * @return the admin
	 */
	public ArrayList<User> getAdmin()
	{
		return admin;
	}


	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(ArrayList<User> admin)
	{
		this.admin = admin;
	}


	/**
	 * @return the users
	 */
	public ArrayList<User> getUsers()
	{
		return users;
	}


	/**
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<User> users)
	{
		this.users = users;
	}


	/**
	 * @return the plans
	 */
	public ArrayList<BusinessPlanner> getPlans()
	{
		return plans;
	}


	/**
	 * @param plans the plans to set
	 */
	public void setPlans(ArrayList<BusinessPlanner> plans)
	{
		this.plans = plans;
	}


	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}


	/**
	 * 
	 * @param departmentName
	 * 
	 */
	public Department(String departmentName)
	{
		
		this.departmentName = departmentName;
		this.admin = new ArrayList<User>();
		this.users = new ArrayList<User>();
		this.plans = new ArrayList<BusinessPlanner>();
	}
	
	/**
	 * 
	 * @param departmentName
	 * @param admin
	 * @param users
	 * @param plans
	 * 
	 * constructor is wanted to set up with preexisting variable
	 */
	public Department(String departmentName, ArrayList<User> admin, ArrayList<User> users)
	{
		
		this.departmentName = departmentName;
		this.admin = admin;
		this.users = users;
		this.plans = new ArrayList<BusinessPlanner>();
	}
	
	/**
	 * 
	 * @return the departmentName
	 * 
	 */
	public String getDepartmentName()
	{
		return departmentName;
	}

	/**
	 * 
	 * @param person
	 * adds a user "person" to either the admin list or the user list of the Dept
	 * the first "if" statement checks  whether the person is admin or user
	 * the second "if" statement checks whether the person is already in the list. 
	 * if the user is already in the list, an exception is thrown.
	 * 
	 */
	
	public void addUser(User person)
	{
		if(person.isAdmin() == true)
		{
			if(admin.contains(person) == false)
			{
				admin.add(person);
			}
				
			else 
			{
				throw new IllegalArgumentException("the user that is being added is already in the department");
			}
				
		}
		
		else 
		{
			if(users.contains(person) == false)
			{
				users.add(person);
			}
				
			else 
			{
				throw new IllegalArgumentException("the user that is being added is already in the department");
			}
		}
	}
	
	
	/**
	 * 
	 * @param name
	 * @return plan
	 * goes through the plan list to and checks each plan against the name given
	 * if the name matches then the plan is returned
	 * if not and the for loop ends with nothing being found, then an exception is thrown.
	 * 
	 */
	public User getUser(String name, boolean access)
	{
		if(access == true)
		{
			for(int i = 0; i < admin.size(); i++)
			{
				if(admin.get(i).getName() == name)
				{
					return admin.get(i);
				}
			
			}
			
		}
		else
		{
			for(int i = 0; i < users.size(); i++)
			{
				if(users.get(i).getName() == name)
				{
					return users.get(i);
				}
			
			}
		}
		throw new IllegalArgumentException("The plan you have chosen doesnt exists");
	}
	
	
	/**
	 * 
	 * @param person
	 * adds a Template "Plan" to the plans list
	 * the  "if" statement checks  whether the plan is in the list already. 
	 * if the plan is already in the list  is an exception is thrown
	 * 
	 */
	public void addPlan(BusinessPlanner Plan)
	{

		if(plans.contains(Plan) == false)
		{
			plans.add(Plan);			
		}
			
		else 
		{
			throw new IllegalArgumentException("the plan that is being added is already in the department");
		}

	}
	
	/**
	 * 
	 * @param name
	 * @return plan
	 * goes through the plan list to and checks each plan against the name given
	 * if the name matches then the plan is returned
	 * if not and the for loop ends with nothing being found, then an exception is thrown.
	 * 
	 */
	public BusinessPlanner getPlan(String name)
	{
		for(int i = 0; i < plans.size(); i++)
		{
			if(plans.get(i).getUserTemplate().getUserTemplateName() == name)
			{
				return plans.get(i);
			}
			
		}
		throw new IllegalArgumentException("The plan you have chosen doesnt exists");
	}
	
	
	
	
	
	/**
	 * 
	 * @param plan
	 * updates the plan, if new plan add it to the list
	 * 
	 */
	public void updatePlan(BusinessPlanner plan)
	{
		String name = plan.getUserTemplate().getUserTemplateName();
		
		for(int i = 0; i < plans.size(); i++)
		{
			if(plans.get(i).getUserTemplate().getUserTemplateName().equals(name))
			{
				plans.remove(i);
				plans.add(i,plan);
				return;
			}
		}
		
		plans.add(plan);
		
		
		
	}
	
	/**
	 * 
	 * 
	 * @param plan
	 * 
	 * changes edit variable
	 */
	public void edit(BusinessPlanner plan)
	{
		
		boolean change = plan.isEdit();
		if(change == true)
		{
			plan.setEdit(false);
		}
		else
		{
			plan.setEdit(true);
		}

	}
	
	public boolean getEdit(BusinessPlanner plan)
	{
		return plan.isEdit();
		
		
	}
	
	

	
}
