package com.jopari.automation.selenium.logging;

/**
 * Refers to the Debug event
 * 
 * @author Kanika Agarwal
 * 
 */
public class DebugEvent extends LoggingEvent
{

	// The message that will be presented to the user
	private String userMessage;

	/**
	 * Constructs the Event
	 * 
	 * @param className
	 *            The classname where the error occured
	 * @param userMessage
	 *            The message that will be shown to the enduser
	 * 
	 */
	public DebugEvent(Class<?> className, String userMessage)
	{
		this.userMessage = userMessage;
		this.className = className;
		logginLevel = Level.DEBUG;
	}

	@Override
	public String toString()
	{
		return userMessage;
	}
}
