package com.jopari.automation.util.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jopari.automation.selenium.exception.ColumnNotFoundException;
import com.jopari.automation.selenium.exception.MalformedFileException;
import com.jopari.automation.selenium.exception.RowNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Rajnish.Verma
 *
 */
public class ExcelReader
{

	private XSSFSheet XSSFSheet;
	private List<String> headerList;

	/**
	 * Creates a new ExcelReader. Loads the excel File given by the path
	 * "workBookPath" and then loads the workSheet of given name "workSheetName"
	 * Takes the relative path of workBook For example if the excel file is in
	 * resources folder of your code directory then the relative path will be
	 * "resources" + File.separator + "ExcelName"
	 * 
	 * @param String
	 *            path of the workBook
	 * @param String
	 *            Name of WorkSheet
	 * @throws MalformedFileException
	 * @throws Exception
	 *             IOExceptions
	 */

	public ExcelReader(String workBookPath, String workSheetName) throws IOException, MalformedFileException {
		FileInputStream fileInputStream = new FileInputStream(workBookPath);
		// POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);

		XSSFSheet = workBook.getSheet(workSheetName);
		if (XSSFSheet == null)
		{
			throw new MalformedFileException("Worksheet: " + workSheetName + ", not found");
		}
		List<String> headerList = getHeader();
		this.headerList = headerList;
	}

	/**
	 * Creates a new ExcelReader. Loads the excel File given by the path
	 * "workBookPath" and then loads the very first workSheet of workBook. Takes
	 * the relative path of workBook For example if the excel file is in
	 * resources folder of your code directory then the relative path will be
	 * "resources" + File.separator + "ExcelName"
	 * 
	 * @param String
	 *            path of the workBook
	 * @throws MalformedFileException
	 * @throws Exception
	 *             IOExceptions
	 */
	public ExcelReader(String workBookPath) throws IOException, MalformedFileException {
		FileInputStream fileInputStream = new FileInputStream(workBookPath);
		// POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);

		XSSFSheet = workBook.getSheetAt(0);
		List<String> headerList = getHeader();
		this.headerList = headerList;
	}

	/**
	 * Creates a new ExcelReader. Loads the excel File given by the path
	 * "workBookPath" and then loads the workSheet at index. Takes the relative
	 * path of workBook For example if the excel file is in resources folder of
	 * your code directory then the relative path will be "resources" +
	 * File.separator + "ExcelName"
	 * 
	 * @param String
	 *            path of the workBook
	 * @param int Index of WorkSheet
	 * @throws MalformedFileException
	 * @throws Exception
	 *             IOExceptions
	 */

	public ExcelReader(String workBookPath, int index) throws IOException, MalformedFileException {
		FileInputStream fileInputStream = new FileInputStream(workBookPath);
		// POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		XSSFSheet = workBook.getSheetAt(index);
		if (XSSFSheet == null)
		{
			throw new MalformedFileException("Worksheet with index: " + index + ", not found");
		}
		List<String> headerList = getHeader();
		this.headerList = headerList;
	}

	/**
	 * Returns the number of Rows in the workSheet
	 */

	public int getRowCount()
	{

		return (XSSFSheet.getLastRowNum() - XSSFSheet.getFirstRowNum());
	}

	/**
	 * Returns the number of Columns in the workSheet
	 */

	public int getColumnCount()
	{
		int columnCount = 0;
		if (XSSFSheet.getRow(0) != null)
		{
			columnCount = XSSFSheet.getRow(0).getLastCellNum() - XSSFSheet.getRow(0).getFirstCellNum();
		}
		return columnCount;
	}

	/**
	 * Returns the value at rowIndex and ColumnIndex in the workSheet
	 * 
	 * @param int index of the row starting with 1. First Row means index 1 .
	 * @param int Index of column starting with 1 . First Column means index 1.
	 * @return String value at rowIndex and columnIndex
	 */
	public String getValue(int rowIndex, int columnIndex) throws ColumnNotFoundException, RowNotFoundException
	{
		String value = "";
		if (XSSFSheet.getRow(rowIndex) != null && XSSFSheet.getRow(rowIndex).getCell(columnIndex - 1) != null)
		{
			value = XSSFSheet.getRow(rowIndex).getCell(columnIndex - 1).toString();
		}
		if (columnIndex > getColumnCount())
		{
			throw new ColumnNotFoundException("Column Index: " + columnIndex + ", not found");
		}
		if (rowIndex > getRowCount())
		{
			throw new RowNotFoundException("Row Index: " + rowIndex + ", not found");
		}
		return value;

	}

	public String getNumValue(int rowIndex, int columnIndex) throws ColumnNotFoundException, RowNotFoundException
	{
		String value = "";
		Cell cell = null;
		if (XSSFSheet.getRow(rowIndex) != null && XSSFSheet.getRow(rowIndex).getCell(columnIndex - 1) != null)
		{
			cell = XSSFSheet.getRow(rowIndex).getCell(columnIndex - 1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			value = cell.toString();
		}
		if (columnIndex > getColumnCount())
		{
			throw new ColumnNotFoundException("Column Index: " + columnIndex + ", not found");
		}
		if (rowIndex > getRowCount())
		{
			throw new RowNotFoundException("Row Index: " + rowIndex + ", not found");
		}
		return value;

	}

	/**
	 * Returns the List of headers of the workSheet
	 * 
	 * @return List list of headers
	 * @throws MalformedFileException
	 */
	public List<String> getHeader() throws MalformedFileException
	{

		List<String> headerList = new ArrayList<String>();
		XSSFRow XSSFRow = XSSFSheet.getRow(0);
		if (XSSFRow != null)
		{
			Iterator<Cell> iterator = XSSFRow.cellIterator();

			while (iterator.hasNext())
			{
				XSSFCell XSSFCell = (XSSFCell) iterator.next();
				if (XSSFCell != null)
				{
					if (headerList.contains(XSSFCell.toString()))
					{
						throw new MalformedFileException("Duplicate header name found: " + XSSFCell.toString());
					} else
						headerList.add(XSSFCell.toString());
				} else
				{
					headerList.add("");
				}
			}
		}
		return headerList;
	}

	/**
	 * Returns the value at rowIndex and ColumnName in the workSheet
	 * 
	 * @param int index of the row starting with 1. First Row means index 1 .
	 * @param String
	 *            Name of the Column
	 * @return String value at rowIndex and columnName
	 */
	public String getValue(int rowIndex, String columnName) throws ColumnNotFoundException, RowNotFoundException
	{
		String returnValue = "";
		int columnIndex = -1;
		Iterator<String> iterator = headerList.iterator();
		while (iterator.hasNext())
		{
			String value = (String) iterator.next();
			if (value.equals(columnName))
			{
				columnIndex = headerList.indexOf(columnName);
				break;
			}
		}
		if (rowIndex > getRowCount())
		{
			throw new RowNotFoundException("Row at Index: " + rowIndex + ", not found");
		}
		if (columnIndex != -1)
		{
			if (XSSFSheet.getRow(rowIndex) != null && XSSFSheet.getRow(rowIndex).getCell(columnIndex) != null)
				returnValue = XSSFSheet.getRow(rowIndex).getCell(columnIndex).toString();
		} else if (columnIndex == -1)
		{
			throw new ColumnNotFoundException("Column Name: " + columnName + ", not found");
		}
		return returnValue;
	}

	/**
	 * Returns a Row at index
	 * 
	 * @param int index of the row starting with 1. First Row means index 1 .
	 * @return a Map with key as column header and value as row value
	 */

	public Map<String, String> getRow(int index) throws RowNotFoundException
	{
		Map<String, String> hashMap = new LinkedHashMap<String, String>();
		XSSFRow XSSFRow = XSSFSheet.getRow(index);
		if (XSSFRow != null)
		{
			for (int i = 0; i < getColumnCount(); i++)
			{
				XSSFCell XSSFCell = XSSFRow.getCell(i, Row.CREATE_NULL_AS_BLANK);
				hashMap.put(headerList.get(i), XSSFCell.toString());
			}
		} else
			throw new RowNotFoundException("Row at Index: " + index + ", not found");

		return hashMap;
	}

	/**
	 * Returns values in a column.
	 * 
	 * @param String
	 *            name of the column header .
	 * @return a list of column values
	 */

	public List<String> getValues(String columnName) throws ColumnNotFoundException
	{
		List<String> list = new ArrayList<String>();
		int columnIndex = -1, i = 0;
		Iterator<String> iterator = headerList.iterator();
		while (iterator.hasNext())
		{
			String value = (String) iterator.next();
			if (value.equals(columnName))
			{
				columnIndex = headerList.indexOf(columnName);
				break;
			}
		}
		Iterator<Row> rowIterator = XSSFSheet.rowIterator();

		while (rowIterator.hasNext())
		{
			XSSFRow XSSFRow = (XSSFRow) rowIterator.next();
			if (columnIndex != -1)
			{
				XSSFCell columnValue = XSSFRow.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
				if (i != 0)
					list.add(columnValue.toString());
				i++;
			} else
			{
				throw new ColumnNotFoundException("Column Name: " + columnName + ", not found");
			}
		}
		return list;

	}

}
