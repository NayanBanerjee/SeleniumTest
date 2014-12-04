package com.jopari.automation.webdriver.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

public class WebDriverUtilTest
{

	/**
	 * This method tests {@link WebDriverUtil} method sortList
	 * 
	 */
	@Test
	public static void testSort()
	{	
	List<String> list = new ArrayList<String>();
	list.add("40");
	list.add("51");
	list.add("10");
	List<String> sorted = WebDriverUtil.sortList(list);
	String sortedList = "";
	for(String data:sorted)
	{
		sortedList = sortedList+data +" ";
	}
	
	Assert.assertEquals("10 40 51 ", sortedList);
	}
}
