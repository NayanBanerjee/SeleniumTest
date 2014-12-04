package com.jopari.automation.selenium.logging;

import java.util.Date;

/**
 * Abstract super class of all logging related events Is passed as a parameter
 * to the {@link TestLogger}
 * 
 * @author Kanika Agarwal
 */
public abstract class LoggingEvent
{

	// The name of the class where the logging event occurred
	protected Class<?> className;
	// The severity level of the logging event
	protected Level logginLevel;
	// The Date when event occurred
	protected Date dateOfEvent;

	/**
	 * Parameterless constructor
	 */
	public LoggingEvent()
	{
		dateOfEvent = new Date();
		logginLevel = Level.INFO;
	}

	/**
	 * Gets the logging {@link Level} associated with the event
	 * 
	 * @return the {@link Level} associated with the event
	 */
	public Level getLogginLevel()
	{
		return logginLevel;
	}

	/**
	 * Gets the logging {@link Level} associated with the event
	 * 
	 * @return the {@link Level} associated with the event
	 */
	public void setLogginLevel(Level loggingLevel)
	{
		this.logginLevel = loggingLevel;
	}

	/**
	 * Gets the {@link Date} when the event was created
	 * 
	 * @return the {@link Date} when the event was created
	 */
	public Date getDateOfEvent()
	{
		return dateOfEvent;
	}

	/**
	 * Indicates the severity of the event
	 * 
	 */
	public enum Level {
		FATAL, ERROR, WARN, INFO, DEBUG
	}

	@Override
	public abstract String toString();

	public Class<?> getClassName()
	{
		return className;
	}

}
