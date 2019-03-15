package software_masters.business_planner;

import java.io.Serializable;
import java.beans.*;
/**
 * This class handles user interaction with this application.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

class BusinessPlanner implements Serializable
{

	/**
	 * 
	 */
	
	/**
	 * 
	 */
	
	private Template userTemplate;
	private Template developerTemplate;
	private TemplateSection current;
	
	
	public BusinessPlanner()
	{
		this("VMOSA", null);
	}
	
	
	/**
	 * @param userTemplate
	 * @param developerTemplate
	 */
	public BusinessPlanner(String developerTemplate, String userTemplate)
	{
		chooseTemplate(developerTemplate, userTemplate);
		
	}
	

	private void chooseTemplate(String templateName, String userTemplateName)
	{
		developerTemplate = Template.loadDeveloperTemplate(templateName);
		userTemplate = Template.loadDeveloperTemplate(templateName);
		userTemplate.setUserTemplateName(userTemplateName);
		current = userTemplate.getRoot();
	}



	/**
	 * Sets developerTemplate to the chosen XML file, clones developerTemplate to
	 * make a userTemplate object. When the user selects a plan template they want,
	 * it is loaded into memory and cloned.
	 * 
	 * @param templateName     of chosen developerTemplate XML file
	 * @param userTemplateName name of userTemplate
	 
	public void chooseTemplate(String templateName, String userTemplateName)
	{
		developerTemplate = Template.loadDeveloperTemplate(templateName);
		userTemplate = Template.loadDeveloperTemplate(templateName);
		userTemplate.setUserTemplateName(userTemplateName);
		current = userTemplate.getRoot();
	}
	*/
	/**
	 * Allows the user to load a previously edited user template from memory.
	 * 
	 * @param templateName name of user template to load
	 */
	public void loadUserTemplate(String templateName)
	{
		userTemplate = Template.loadUserTemplate(templateName);
		developerTemplate = userTemplate.getDeveloperTemplate();
		current = userTemplate.getRoot();
	}

	/*
	 * methods like this that edit the template should be moved to template.
	 * something to consider later.
	 */
	/**
	 * Adds a copy of the type of TemplateSection currently accessed by the user.
	 * Only works if the section is allowed to be copied.
	 * 
	 * @return TemplateSection that is the head of the new branch
	 */
	public TemplateSection addBranch()
	{
		String currCategory = current.getCategory();

		TemplateSection sectionToCopy = this.findTemplateSection(developerTemplate.getRoot(), currCategory);
		TemplateSection copy = sectionToCopy.deepCopy();

		if (copy != null)
		{
			System.out.println("hello");
			current.getParent().addChild(copy);
			copy.setParent(current);
			return copy;
		}
		return null;

	}

	/**
	 * Removes the currently accessed section if allowed, sets current section to
	 * parent of removed section.
	 * 
	 * @return boolean indicating if section actually removed
	 */
	public boolean removeSection()
	{
		if (current.getParent().removeChild(current))
		{
			current = current.getParent();
			return true;
		}
		return false;
	}

	/**
	 * Recursively finds a templateSection with a given category.
	 * 
	 * @param section
	 * @param targetCategory
	 * @return TemplateSection of developerTemplate which user wants to add
	 */
	public TemplateSection findTemplateSection(TemplateSection section, String targetCategory)
	{
		/* Base case - category found */
		if (section.getCategory().equals(targetCategory))
		{
			return section;
		}
		/* Recursive case - category not found */
		else
		{
			for (TemplateSection t : section.getChildren())
			{
				section = findTemplateSection(t, targetCategory);
				if (section != null)
				{
					return section;
				}
			}
		}
		return null;
	}
	

	

	/**
	 * Allows user to navigate to parent of the currently accessed template section
	 */
	public void accessParent()
	{
		current = current.getParent();
	}

	/**
	 * Allows user to navigate to the child of the currently accessed template
	 * section, given its position in the children array
	 * 
	 * @param index
	 */
	public void accessChild(int index)
	{
		current = current.getChildren().get(index);
	}

	/**
	 * @return the userTemplate
	 */
	public Template getUserTemplate()
	{
		return userTemplate;
	}

	/**
	 * @param userTemplate the userTemplate to set
	 */
	public void setUserTemplate(Template userTemplate)
	{
		this.userTemplate = userTemplate;
	}

	/**
	 * @return the developerTemplate
	 */
	public Template getDeveloperTemplate()
	{
		return developerTemplate;
	}

	/**
	 * @param developerTemplate the developerTemplate to set
	 */
	public void setDeveloperTemplate(Template developerTemplate)
	{
		this.developerTemplate = developerTemplate;
	}

	/**
	 * @return the current
	 */
	public TemplateSection getCurrent()
	{
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(TemplateSection current)
	{
		this.current = current;
	}

}
