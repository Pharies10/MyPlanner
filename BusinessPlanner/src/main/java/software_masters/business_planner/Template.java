package software_masters.business_planner;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * This class can be both a representation of a business plan or a business plan outline.
 * 
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * 
 * @since 2019-02-23
 * 
 * 
 */

public class Template {
	private String developerTemplateName;
	private String userTemplateName;
	private TemplateSection root;
	
	/**
	 * This constructor creates a default object for use by XML encoder
	 */
	public Template() {
		this(null,null,null);
	}
	
	/**
	 * @param developerTemplateName
	 * @param userTemplateName
	 * @param root
	 * 
	 * Constructor for creating Template object.
	 */
	public Template(String developerTemplateName, String userTemplateName, TemplateSection root) {
		this.developerTemplateName = developerTemplateName;
		this.userTemplateName = userTemplateName;
		this.root = root;
	}

	/**
	 * @return the name of the developer template
	 */
	public String getDeveloperTemplateName() {
		return developerTemplateName;
	}

	/**
	 * @param developerTemplateName the name of the developer template being used
	 */
	public void setDeveloperTemplateName(String developerTemplateName) {
		this.developerTemplateName = developerTemplateName;
	}

	/**
	 * @return the name of the business plan
	 */
	public String getUserTemplateName() {
		return userTemplateName;
	}

	/**
	 * @param userTemplateName the name of the business plan
	 */
	public void setUserTemplateName(String userTemplateName) {
		this.userTemplateName = userTemplateName;
	}

	/**
	 * @return the start section for the template
	 */
	public TemplateSection getRoot() {
		return root;
	}

	/**
	 * @param root the start TemplateSection for the template 
	 */
	public void setRoot(TemplateSection root) {
		this.root = root;
	}
	
	/**
	 * This method returns a deepCopy of the template object
	 * 
	 * @return a copy of the template object
	 */
	public Template deepCopy() {
		Template copy = new Template(this.developerTemplateName, this.userTemplateName, this.root.deepCopy());
		return copy;
		
	}
	
	/**
	 * This method serializes the object.
	 */
	public void save(String filename) 
	{
		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(this);
		encoder.close();
		
	}
	
	/**
	 * This method is used to load a design based on a provided filepath
	 * 
	 * @param filepath the filepath to the xml file representation of the object 
	 * 
	 * @return a template object from memory
	 */
	public static Template load(String filepath) {
			XMLDecoder decoder=null;
			try {
				decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: File dvd.xml not found");
			}
			return (Template)decoder.readObject();
	}

}
