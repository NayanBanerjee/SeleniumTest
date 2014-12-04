package com.jopari.automation.webdriver.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class contains all Jopari specific methods required for webpage
 * traversing.
 * 
 * @author Rajnish.Verma
 * 
 */
public class WindowHelper
{

	/**
	 * This method checks the value passed to it is contaiAlphanumebric
	 * character or not.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isAlphaNumeric(String value)
	{
		return StringUtils.isAlphanumeric(value);
	}

	/**
	 * This method checks whether the passed text parameter is present in
	 * current webpage or not
	 * 
	 * @param text
	 * @param driver
	 * @return
	 */
	public static boolean isTextPresentOnPage(String text, WebDriver driver)
	{
		boolean isTextPresent = driver.getPageSource().contains(text);
		return isTextPresent;
	}

	/**
	 * This method return the list of actual rows on the webtable which is
	 * passed as a parameter.
	 * 
	 * @param table
	 * @return
	 */
	public static List<WebElement> getRowsofTable(WebElement table)
	{
		List<WebElement> rowsInTable = table.findElements(By.tagName("tr"));
		return rowsInTable;
	}

	/**
	 * This method return the number of coulmns in a row of a web table.
	 * 
	 * @param row
	 * @return
	 */

	public static List<WebElement> getCellsInRow(WebElement row)
	{
		List<WebElement> cellsInRow = row.findElements(By.tagName("td"));
		return cellsInRow;
	}

	/**
	 * This method return the number of rows on the webtable which is passed as
	 * a parameter.
	 * 
	 * @param table
	 * @return
	 */
	public static int getNumberOfRows(WebElement table)
	{
		List<WebElement> rowsInTable = table.findElements(By.tagName("tr"));
		int sizeOfRow = 0 ;
		if(rowsInTable != null)
		{
			sizeOfRow = (rowsInTable.size()- 1);
		}
		return sizeOfRow;
	}

	/**
	 * This method returns the rowNumber of webtable for the passed valueDesired
	 * parameter.
	 * 
	 * @param valueDesired
	 * @param table
	 * @return
	 */
	public static int getRowIndexContainingDesiredValue(String valueDesired, WebElement table)
	{
		List<WebElement> rowsInTable = getRowsofTable(table);
		int rowIndex = 0;
		outerloop: for (int rowNum = 0; rowNum < rowsInTable.size(); rowNum++)
		{
			List<WebElement> cellsInRow = getCellsInRow(rowsInTable.get(rowNum));
			for (int columnIndex = 0; columnIndex < cellsInRow.size(); columnIndex++)
			{
				if (valueDesired.equalsIgnoreCase(cellsInRow.get(columnIndex).getText()))
				{
					rowIndex = rowNum;
					break outerloop;
				}
			}
		}
		return rowIndex;
	}

	/**
	 * This method returns the Column Number of webtable for the passed
	 * valueDesired parameter.
	 * 
	 * @param valueDesired
	 * @param table
	 * @return
	 */
	public static int getColumnIndexContainingDesiredValue(String valueDesired, WebElement table)
	{
		List<WebElement> rowsInTable = getRowsofTable(table);
		int columnIndex = 0;
		outerloop: for (int i = 0; i < rowsInTable.size(); i++)
		{
			List<WebElement> cellsInRow = getCellsInRow(rowsInTable.get(i));
			for (int j = 0; j < cellsInRow.size(); j++)
			{
				if (valueDesired.equalsIgnoreCase(cellsInRow.get(j).getText()))
				{
					columnIndex = j;
					break outerloop;
				}
			}
		}
		return columnIndex;
	}

	/**
	 * This method takes table, row and column information and return the String
	 * value of a cell.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param table
	 * @return
	 */
	public static String getTableCellValue(int rowIndex, int columnIndex, WebElement table)
	{
		String returnValue = null;
		List<WebElement> rowsInTable = getRowsofTable(table);
		outerloop: for (int i = 0; i < rowsInTable.size(); i++)
		{
			List<WebElement> cellsInRow = getCellsInRow(rowsInTable.get(i));
			for (int j = 0; j < cellsInRow.size(); j++)
			{
				if ((i == rowIndex) && (j == columnIndex))
				{
					returnValue = cellsInRow.get(j).getText();
					break outerloop;
				}
			}
		}
		return returnValue;
	}

	/**
	 * This method takes table, row and column information and return the
	 * WebElement value of a cell.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param table
	 * @return
	 */
	public static WebElement getTableCellValueAsElement(int rowIndex, int columnIndex, WebElement table)
	{
		WebElement returnElement = null;
		List<WebElement> rowsInTable = getRowsofTable(table);
		outerloop: for (int i = 0; i < rowsInTable.size(); i++)
		{
			List<WebElement> cellsInRow = getCellsInRow(rowsInTable.get(i));
			for (int j = 0; j < cellsInRow.size(); j++)
			{
				if ((i == rowIndex) && (j == columnIndex))
				{
					returnElement = cellsInRow.get(j);
					break outerloop;
				}
			}
		}
		return returnElement;
	}

	/**
	 * This method returns the String value of whole column as a List.
	 * 
	 * @param columnIndex
	 * @param table
	 * @return
	 */
	public static List<String> getColumnValuesOfTable(int columnIndex, WebElement table)
	{
		List<String> desiredcolumnValues = new ArrayList<String>();
		List<WebElement> rowsInTable = getRowsofTable(table);
		for (int rowCounter = 0; rowCounter < rowsInTable.size(); rowCounter++)
		{
			List<WebElement> cellsInRow = getCellsInRow(rowsInTable.get(rowCounter));
			for (int j = 0; j < cellsInRow.size(); j++)
			{
				if (j == columnIndex)
				{
					desiredcolumnValues.add(cellsInRow.get(columnIndex).getText());
					break;
				}
			}
		}
		return desiredcolumnValues;
	}
}
