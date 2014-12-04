package com.jopari.automation.webdriver.util;

import org.junit.Assert;
import org.testng.annotations.Test;
import com.jopari.automation.webdriver.util.WindowHelper;

/**
 * UnitTest cases for class com.jopari.automation.webdriver.util.WindowHelper.
 * @author Kanika.Agarwal
 */
public class WindowHelperTest 
{
	/*
	 * This method tests isAlphaNumeric(String) method of WindowHelper class
	 * It verifies if the string being passed is alphanumeric
	 */
	@Test
	public static void testIsAlphaNumeric()

	{	
	String alphanumericvalue = "asc123";
	Assert.assertTrue(WindowHelper.isAlphaNumeric(alphanumericvalue));
	}

}