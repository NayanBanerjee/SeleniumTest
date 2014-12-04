package com.jopari.automation.selenium.exception;

/**
 * This class MalformedFileException occurs when the 
 * input file is distorted or malformed.
 */
public class MalformedFileException extends Exception
{

	
	private static final long serialVersionUID = -3098741085202969120L;

	public MalformedFileException() 
	{
		
	}

	/**
	 * Constructor with parameter of type String
	 * @param arg0
	 */
	public MalformedFileException(String arg0) 
	{
		super(arg0);
		
	}

	/**
	 * Constructor with parameter of type Throwable
	 * @param arg0
	 */
	public MalformedFileException(Throwable arg0) 
	{
		super(arg0);
		
	}

	/**
	 * Constructor with parameters of type String and Throwable.
	 * @param arg0
	 * @param arg1
	 */
	public MalformedFileException(String arg0, Throwable arg1) 
	{
		super(arg0, arg1);
		
	}

}
