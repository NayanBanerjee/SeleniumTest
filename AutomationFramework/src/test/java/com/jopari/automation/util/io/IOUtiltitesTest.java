package com.jopari.automation.util.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * UnitTest cases for class com.jopari.automation.util.io.IOUtilities.
 * 
 * @author Kanika.Agarwal
 * 
 */
public class IOUtiltitesTest
{
	private static final String dirhome = ".\\src\\test\\resources";
	private static final String dirname = "\\testdir2";
	private static final String filename = "/readFile.txt";

	/*
	 * This method tests createDirectory(String,String) method of IOUtilities
	 * classIt verifies if the directory is created at specified location with
	 * specified name
	 */
	@Test
	public void testCreateDirectory()
	{
		try
		{
			Assert.assertTrue(IOUtilities.createDirectory(dirhome, dirname));
			File directory = new File(dirhome + dirname);
			directory.delete();
		} catch (IOException e)
		{
			Assert.fail(e.getMessage());
		}
	}

	/*
	 * This method tests readTextLinesInFile(String) method of IOUtilities class
	 * It verifies if the text of the file is correctly read from specified
	 * location
	 */
	@Test
	public void testReadTextLinesInFile()
	{
		String data1 = "testdata1";
		String data2 = "testdata2";
		try
		{
			List<String> list = IOUtilities.readTextLinesInFile(dirhome
					+ filename);
			Assert.assertEquals(data1, list.get(0));
			Assert.assertEquals(data2, list.get(1));
		} catch (IOException e)
		{
			Assert.fail(e.getMessage());
		}
	}

	/*
	 * This method tests isContainSpecialChar(String) method of IOUtilities
	 * classIt verifies if the text contains special character.
	 */
	@Test
	public void testSpecialCharacter()
	{
		try
		{
			String testData1 = "testData";
			String testData2 = "testData@";
			Assert.assertFalse(IOUtilities.isContainSpecialChar(testData1));
			Assert.assertTrue(IOUtilities.isContainSpecialChar(testData2));
		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	}

	/*
	 * This method tests testSpecialCharacter(String) method of IOUtilities
	 * classIt verifies if the text contains blank space.
	 */
	@Test
	public void testBlankSpace()
	{
		try
		{
			String testData1 = "testData";
			String testData2 = "test Data";
			Assert.assertFalse(IOUtilities.isContainsWhiteSpace(testData1));
			Assert.assertTrue(IOUtilities.isContainsWhiteSpace(testData2));
		} catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}
	}
}
