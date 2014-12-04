package com.jopari.automation.selenium.logging;

import org.apache.log4j.Logger;

/**
 * TestLogger is the main logging class which is used to log
 * {@link LoggingEvent}
 * 
 * @author Kanika Agarwal
 * 
 */
public class TestLogger
{

	/*
	 * This method logs a given event
	 * 
	 * @param event the {@link LoggingEvent} to be logged
	 */
	public static void log(LoggingEvent event)
	{
		try
		{

			final Logger log = Logger.getLogger(event.getClassName());

			String logMessage = "Message: " + event.toString();
			switch (event.logginLevel)
			{
			case DEBUG:
				log.debug(logMessage);
				break;
			case ERROR:
				log.error(logMessage);
				break;
			case INFO:
				log.info(logMessage);
				break;
			case FATAL:
				log.error("Fatal: " + logMessage);
				break;
			case WARN:
				log.warn(logMessage);
				break;
			default:
				log.info(logMessage);
				break;
			}
		} catch (RuntimeException e)
		{
			// logging is a non-critical operation.
			System.out.println("[TestLogger] ERROR: COULD NOT LOG EVENT. STACK TRACE BELOW.");
			e.printStackTrace();
		}

	}
}
