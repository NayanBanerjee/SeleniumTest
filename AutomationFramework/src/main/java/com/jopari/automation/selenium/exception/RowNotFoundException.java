/**
 * 
 */
package com.jopari.automation.selenium.exception;

/**
 * This class RowNotFoundException represents the exception 
 * when input rowindex is not found.
 * 
 */
public class RowNotFoundException extends Exception
{

	private static final long serialVersionUID = -6053057127728441638L;

	public RowNotFoundException() {
		
	}

	/**
	 * Constructor with parameter of type String
	 * @param arg0
	 */
	public RowNotFoundException(String arg0) {
		super(arg0);
		
	}

	/**
	 * Constructor with parameter of type Throwable
	 * @param arg0
	 */
	public RowNotFoundException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * Constructor with parameters of type String and Throwable.
	 * @param arg0
	 * @param arg1
	 */
	public RowNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
}
