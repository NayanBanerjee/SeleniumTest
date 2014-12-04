/**
 * 
 */
package com.jopari.automation.selenium.exception;

/**
 * Main exception which is thrown to the client
 * 
 */
public class AutomationException extends Exception
{

	private static final long serialVersionUID = 3516759669586713627L;

	public AutomationException() {
		
	}

	/**
	 *  Constructor with parameter of type String
	 * @param arg0
	 */
	public AutomationException(String arg0) {
		super(arg0);
		
	}

	/**
	 * Constructor with parameter of type Throwable.
	 * @param arg0
	 */
	public AutomationException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 *  Constructor with parameters of type String and Throwable.
	 * @param arg0
	 * @param arg1
	 */
	public AutomationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}
