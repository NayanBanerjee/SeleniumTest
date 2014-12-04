package com.jopari.automation.selenium.logging;

/**
 * 
 * Refers to a warning event
 * 
 * @author Kanika Agarwal
 * 
 */
public class WarningEvent extends LoggingEvent
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
	 */
	public WarningEvent(Class<?> className, String userMessage)
	{
		this.className = className;
		this.userMessage = userMessage;
		logginLevel = Level.WARN;
	}

	@Override
	public String toString()
	{
		return userMessage;
	}
}
