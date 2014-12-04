package com.jopari.automation.selenium.logging;

/**
 * Refers to a information event
 * 
 * @author Kanika Agarwal
 * 
 */
public class InfoEvent extends LoggingEvent
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
	public InfoEvent(Class<?> className, String userMessage)
	{
		this.className = className;
		this.userMessage = userMessage;

		logginLevel = Level.INFO;
	}

	@Override
	public String toString()
	{
		return userMessage;
	}
}
