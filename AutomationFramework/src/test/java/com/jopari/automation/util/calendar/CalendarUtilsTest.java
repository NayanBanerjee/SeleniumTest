package com.jopari.automation.util.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jopari.automation.util.calendar.CalendarUtils;

/**
 * UnitTest cases for class com.jopari.automation.util.calendar.CalendarUtils.
 * 
 * @author Kanika.Agarwal
 *
 */
public class CalendarUtilsTest
{
	
	/*
	 * This method tests formatDate() method .
	 * It checks if date passed ,is returned in specified format 
	 */
	@Test
	public static void testFormatDate()
	{
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, 2014);
	    calendar.set(Calendar.MONTH, 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 10);
	    Date date = calendar.getTime();
	    String simpledateformat = "yyyy-MM-dd";
		Assert.assertEquals(CalendarUtils.formatDate(date, simpledateformat),"2014-02-10");	
	}
	
	/*
	 * This method tests getDateFromFormat method .
	 * It checks format for specific DateFormatString 
	 */
	@Test
	public void testGetDateFromFormat()
	{
		String dateStr=new Date().toString();
		String dateFormatString = "EEE MMM dd HH:mm:ss z yyyy";
		try
		{
			Date date = CalendarUtils.getDateFromFormat(dateStr,dateFormatString);
			Assert.assertTrue(dateStr.equalsIgnoreCase(date.toString()));
		} 
		catch (ParseException e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/*
	 * This method tests getCurrentTime method .
	 * It checks if current time is returned. 
	 */
	@Test
	public void testGetCurrentTime()
	{
		String currenttime= CalendarUtils.getCurrentTime();
		String dateformat = "yyyy-MM-dd 'at' HH:mm:ss z";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
		Assert.assertEquals(currenttime, dateFormat.format(new Date()));
	}

	/*
	 * This method tests getCurrentTime(String) method .
	 * It checks if current time is returned in given format. 
	 */
	@Test
	public void testGetCurrentTimeInKnownFormat()
	{
		String format = "yyyy-mm-dd" ;
		String currenttime= CalendarUtils.getCurrentTime(format);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Assert.assertEquals(currenttime, dateFormat.format(new Date()));
	}
	
	
	/*
	 * This method checks add() method which adds given milliseconds to given date 
	 */
	@Test
	public void testadd()
	{
		Date date = new Date();
		date.setTime(date.getTime() + (long) 5 * 1000 * 60 * 60 * 24);
		Date currenttime = CalendarUtils.add(new Date(), Calendar.DATE, 5);
		date.compareTo(currenttime);
	}

	
	/*
	 * This method checks addYear() method which adds given number of years to given date 
	 */
	@Test
	public void testaddYear()
	{
		Date date = new Date();
		date.setTime(date.getTime() + (long) 365 * 1000 * 60 * 60 * 24);
		Date currenttime = CalendarUtils.addYear(new Date(), 1);
		date.compareTo(currenttime);
	}
	
	/*
	 * This method checks addMonth() method which adds given number of months to given date 
	 */
	@Test
	public  void testaddMonth()
	{
		Date date = new Date();
		date.setTime(date.getTime() + (long) 30 * 1000 * 60 * 60 * 24);
		Date currenttime = CalendarUtils.addMonth(new Date(), 1);
		date.compareTo(currenttime);
	}

	
	/*
	 * This method checks addDay() method which adds given number of days to given date 
	 */
	@Test
	public void testaddDay()
	{
		Date currentdate = new Date();
		currentdate.setTime(currentdate.getTime() + (long) 1 * 1000 * 60 * 60 * 24);
		Date currenttime = CalendarUtils.addDay(new Date(), 1);
		currentdate.compareTo(currenttime);
	}

	
	/*
	 * This method checks addHour() method which adds given number of hours to given date 
	 */
	@Test
	public void testaddHour()
	{
		Date date=new Date();
		date.setTime( date.getTime() + (long)1000*60*60*1 );
		Date currenttime= CalendarUtils.addHour(new Date(), 1);
		date.compareTo(currenttime);
	}

	
	/*
	 * This method checks addMinutes() method which adds given number of minutes to given date 
	 */
	@Test
	public void testaddMinutes()
	{
		Date date=new Date();
		date.setTime( date.getTime() + (long)1000*60*1 );
		Date currenttime= CalendarUtils.addMinutes(new Date(), 1);
		date.compareTo(currenttime);
	}

	
	/*
	 * This method checks addSeconds() method which adds given number of seconds to given date 
	 */
	@Test
	public void testaddSeconds()
	{
		Date date=new Date();
		date.setTime( date.getTime() + (long)1000*1 );
		Date currenttime= CalendarUtils.addSeconds(new Date(), 1);
		date.compareTo(currenttime);
	}

	
	/*
	 * This method checks getDayOfMonth() method which gets curent day Of Month
	 */
	@Test
	public static void testGetDayOfMonth()
	{
		Calendar calendar = Calendar.getInstance();
		Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), CalendarUtils.getDayOfMonth());	
	}

	
	/*
	 * This method checks getDayOfWeek() method which gets curent day Of week
	 */
	@Test
	public void testGetDayOfWeek()
	{
		Calendar calendar = Calendar.getInstance();
		Assert.assertEquals(calendar.get(Calendar.DAY_OF_WEEK), CalendarUtils.getDayOfWeek());	
	}

	
	/*
	 * This method checks getHourOfDay() method which gets current hour of day
	 */
	@Test
	public void testGetHoursOfDay()
	{
		Calendar calendar = Calendar.getInstance();
		Assert.assertEquals(calendar.get(Calendar.HOUR_OF_DAY), CalendarUtils.getHourOfDay());
	}
	
	
	/*
	 * This method checks getMinutes() method which gets current minutes
	 */
	@Test
	public void testGetMinutes()
	{
		Calendar calendar = Calendar.getInstance();
		Assert.assertEquals(calendar.get(Calendar.MINUTE), CalendarUtils.getMinutes());
	}
	
	
	/*
	 * This method checks getSeconds() method which gets current seconds
	 */
	@Test
	public void testGetSeconds()
	{
		Calendar calendar = Calendar.getInstance();
		Assert.assertEquals(calendar.get(Calendar.SECOND), CalendarUtils.getSeconds());
	}

	
	/*
	 * This method checks getDateInNewFormat() method which gets date in given format
	 */	
	@Test
	public static void testGetDateInNewFormat()
	{
		try
		{
			String date = CalendarUtils.getDateInNewFormat("10/01/2014", "dd/mm/yyyy", "mm/dd/yy");
			Assert.assertEquals("01/10/14", date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * This method checks getMonthNum() method returns current month Number
	 * to the calling method
	 */	
	@Test
	public static void testGetMonthNum()
	{
		try
		{
			Calendar nowCal = Calendar.getInstance();
			int monthNumActual = nowCal.get(Calendar.MONTH);
			int monthNumber = CalendarUtils.getMonthNum();
			Assert.assertEquals(monthNumActual, monthNumber);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * This method checks getYearNum() method returns current year Number
	 * to the calling method
	 */	
	@Test
	public static void testGetYearNum()
	{
		try
		{
			Calendar nowCal = Calendar.getInstance();
			int yearActual = nowCal.get(Calendar.YEAR);
			int yearNumber = CalendarUtils.getYearNum();
			Assert.assertEquals(yearActual, yearNumber);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * This method checks getDayNumOfMonth() method returns  day number of a month
	 * to the calling method
	 */	
	@Test
	public static void testGetDayNumOfMonth()
	{
		try
		{
			Calendar nowCal = Calendar.getInstance();
			int dayActual = nowCal.get(Calendar.DAY_OF_MONTH);
			int day = CalendarUtils.getDayNumOfMonth();
			Assert.assertEquals(dayActual, day);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * This method checks getDate() method returns Date type casted to String
	 * for a given year, month, day to the calling method
	 * in format d,M,yyyy
	 */	
	@Test
	public static void testGetDate()
	{
		try
		{
			String date = CalendarUtils.getDate(2014,3-1,10);
			Assert.assertEquals("10,3,2014", date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * This method checks getMonthName() method returns  month Name for a given month Number
	 * to the calling method
	 */	
	@Test
	public static void testGetMonthName()
	{
		try
		{
			String monthName = CalendarUtils.getMonthName(8);
			Assert.assertEquals("August", monthName);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}