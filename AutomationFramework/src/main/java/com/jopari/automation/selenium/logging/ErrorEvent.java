package com.jopari.automation.selenium.logging;

/**
 * This class represents an error event. It contains information about the error
 * that occurred.
 * 
 * @author Bharat Mehta
 */
public class ErrorEvent extends LoggingEvent
{

	// The message that will be presented to the user
	private String userMessage;
	// The stacktrace of the exception
	private String stackTrace;

	/**
	 * Constructs the Event
	 * 
	 * @param className
	 *            The classname where the error occured
	 * @param userMessage
	 *            The message that will be shown to the enduser
	 * @param stackTrace
	 *            The stacktrace of the Exception
	 */
	public ErrorEvent(Class<?> className, String userMessage, String stackTrace)
	{
		this.className = className;
		this.userMessage = userMessage;
		this.stackTrace = stackTrace;
		logginLevel = Level.ERROR;
	}

	/**
	 * Constructs the Event
	 * 
	 * @param className
	 *            The classname where the error occured
	 * @param userMessage
	 *            The message that will be shown to the enduser
	 */
	public ErrorEvent(Class<?> className, String userMessage)
	{
		this.className = className;
		this.userMessage = userMessage;
		this.stackTrace = null;
		logginLevel = Level.ERROR;
	}

	@Override
	public String toString()
	{
		return "The error \"" + userMessage + "\" occured with following details: " + stackTrace;
	}
}
