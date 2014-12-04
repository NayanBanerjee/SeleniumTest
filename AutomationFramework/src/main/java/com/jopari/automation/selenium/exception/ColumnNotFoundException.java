/**
 * 
 */
package com.jopari.automation.selenium.exception;

/**
 * This class ColumnNotFoundException represents the exceptional condition
 * which occurs when column index is not found in the input sheet.
 */
public class ColumnNotFoundException extends Exception
{

	
	private static final long serialVersionUID = -6053057127728441638L;

	public ColumnNotFoundException() {
	
	}

	/**
	 * Constructor with parameter of type String
	 * @param arg0
	 */
	public ColumnNotFoundException(String arg0) {
		super(arg0);
	
	}

	/**
	 * Constructor with parameter of type Throwable
	 * @param arg0
	 */
	public ColumnNotFoundException(Throwable arg0) {
		super(arg0);
	
	}

	/**
	 * Constructor with parameters of type String and Throwable.
	 * @param arg0
	 * @param arg1
	 */
	public ColumnNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	
	}
}
