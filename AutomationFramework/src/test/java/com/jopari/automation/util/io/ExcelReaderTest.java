package com.jopari.automation.util.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jopari.automation.selenium.exception.ColumnNotFoundException;
import com.jopari.automation.selenium.exception.MalformedFileException;
import com.jopari.automation.selenium.exception.RowNotFoundException;
import com.jopari.automation.util.io.ExcelReader;

/**
 * UnitTest cases for class com.jopari.automation.util.io.ExcelReaderNew.
 * 
 * @author Kanika.Agarwal
 *
 */
public class ExcelReaderTest
{
	
	ExcelReader excelsheet ;
	private String column1 = "column1";
	private String column2 = "column2";
	private String column3 = "column3";
	
	ExcelReaderTest()
	{
		String excelpath = ".\\src\\test\\resources\\testdata.xlsx";
		try
		{
			excelsheet = new ExcelReader(excelpath);
		} 
		catch (IOException e) 
		{
			Assert.fail(e.getMessage());
		}
		catch (MalformedFileException e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/*
	 * This method tests getRowCount() method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct number of rows
	 */
	@Test
	public void testGetRowCount()
	{
		Assert.assertNotNull(excelsheet.getRowCount());
	}
	
	
	/*
	 * This method tests getColumnCount() method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct number of columns
	 */
	@Test
	public void testGetColumnCount()
	{
		Assert.assertNotNull(excelsheet.getColumnCount());
	}
	
	
	/*
	 * This method tests getValue(int,int) method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct cell value
	 * given the row number and column number
	 */
	@Test
	public void testGetValue()
	{
		try
		{
			Assert.assertEquals(excelsheet.getValue(1,3),"data1");
		}
		catch (ColumnNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		} 
		catch (RowNotFoundException e)
		{
			Assert.fail(e.getMessage());
		}
	}
	
	
	/*
	 * This method tests getValue(int,String) method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct cell value
	 * given the row number and column name
	 */
	@Test
	public void testGetValueString()
	{
		try
		{
			Assert.assertEquals(excelsheet.getValue(1,"column3"),"data1");
		} 
		catch (ColumnNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		}
		catch (RowNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/*
	 * This method tests getNumValue(int,int) method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct cell value
	 * given the row number and column number.If the integer value in sheet is large to be stored in an integer, 
	 * it is not converted into signed value and is returned as it is.
	 */
	@Test
	public void testGetNumValue()
	{
		try
		{
			Assert.assertEquals(excelsheet.getNumValue(2,2),"123456789");
		}
		catch (ColumnNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		}
		catch (RowNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	
	/*
	 * This method tests getValues(String) method of ExcelReaderNew class
	 * It verifies if the excel sheet is getting read and is returning the correct column values
	 * given the column name.
	 */
	@Test
	public void testGetValues()
	{
		try
		{
			List<String> columnvalue= excelsheet.getValues("column3");
			Assert.assertEquals(columnvalue.get(0),"data1");
			Assert.assertEquals(columnvalue.get(1),"data2");
		}
		catch (ColumnNotFoundException e) 
		{
			Assert.fail(e.getMessage());
		}
	}

	
	/*
	 * This method tests getRow(int) method of ExcelReaderNew class
	 * It verifies the row values is returned for given row number.
	 */
	@Test
	public void testGetRow()
	{		
		try
		{
			Map<String, String> rowlist = excelsheet.getRow(1);
			Assert.assertEquals(rowlist.get(column1),"row2");
			Assert.assertEquals(rowlist.get(column2),"12.0");
			Assert.assertEquals(rowlist.get(column3),"data1");
		} 
		catch (RowNotFoundException e)
		{
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*
	 * This method tests getHeader() method of ExcelReaderNew class
	 * gets the header of the table.
	 */
	@Test
	public void testGetHeader()
	{
		try
		{
			List<String> header = excelsheet.getHeader();
			Assert.assertTrue(header.contains(column1));
			Assert.assertTrue(header.contains(column2));
			Assert.assertTrue(header.contains(column3));
		}
		catch (MalformedFileException e) 
		{
			Assert.fail(e.getMessage());
		}
			
	}


}
