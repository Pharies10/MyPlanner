package software_masters.business_planner;

/**
 * This class represents a content section that is all text
 * 
 * @author Wesley Murray
 * @author Lee Kendall
 * @since 2019-02-23
 */

public class Text implements Content 
{

	private String textString;

	/**
	 * @return the textString
	 */
	public String getTextString()
	{
		return textString;
	}

	/**
	 * @param textString the textString to set
	 */
	public void setTextString(String textString)
	{
		this.textString = textString;
	}

	/**
	 * this method demonstrates how a user might interact with the content of
	 * TemplateSection
	 */
	public void display()
	{
		setTextString("display");
		System.out.println(textString);
	}

	/**
	 * @return copy of this content object
	 */
	public Content copy()
	{
		Text copy = new Text();
		copy.setTextString(this.textString);
		return copy;
	}

	/**
	 * Tests if two text objects are equal, useful for testing
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Text other = (Text) obj;
		if (textString == null)
		{
			if (other.textString != null)
				return false;
		} else if (!textString.equals(other.textString))
			return false;
		return true;
	}

}
