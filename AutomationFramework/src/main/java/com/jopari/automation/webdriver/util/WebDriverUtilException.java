package com.jopari.automation.webdriver.util;
/**
 * 
 * @author Rajnish.Verma
 *
 */
@SuppressWarnings("serial")
public class WebDriverUtilException extends Exception
{
	private String message = null;

	public WebDriverUtilException() {
		super();
	}

	public WebDriverUtilException(String message) {
		super(message);
		this.message = message;

	}

	@Override
	public String toString()
	{
		return message;
	}

	@Override
	public String getMessage()
	{
		return message;
	}

}
