package com.jopari.automation.util.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jopari.automation.selenium.exception.ColumnNotFoundException;
import com.jopari.automation.selenium.exception.MalformedFileException;

import com.csvreader.CsvReader;

/**
 *  This file will be responsible for reading csv files and providing some
 * getters methods for that file.
 * 
 * In this it is assumed that csv file must have unique headers.
 * <p>
 * The erroneous case will be handles in below manner:
 * <ul>
 * <li>If headers not found, then first row will be assumed as headers.
 * <li>If duplicate header name found then this will throw
 * {@link MalformedFileException}.
 * <li>If any row have more values than the count of headers, then this will
 * throw {@link MalformedFileException}.
 * <li>If in cell value (specific column value of specific row) is not given,
 * then empty value will be assumed.
 * <li>If only headers defined in the files, then no harm.
 * <li>If empty file, then again no harm.
 * 
 * @author Rajnish.Verma
 *
 */
public class CSVReader
{

	private Map<String, List<String>> mapResult;

	private void loadFile(CsvReader reader) throws IOException, MalformedFileException
	{
		mapResult = new LinkedHashMap<String, List<String>>();

		reader.readHeaders();

		String headers[] = reader.getHeaders();

		for (String header : headers)
		{
			if (mapResult.containsKey(header))
			{
				throw new MalformedFileException("Duplicate header name found: " + header);
			}

			mapResult.put(header, new ArrayList<String>());
		}

		// Will iterate through number of rows
		while (reader.readRecord())
		{
			int currentRowColCount = reader.getColumnCount();
			int currentRowIndex = (int) (reader.getCurrentRecord() + 1);

			if (currentRowColCount > headers.length)
			{
				throw new MalformedFileException("Row: " + currentRowIndex + ", have more columns than in headers.");
			}

			// Will iterate through number of headers
			for (int i = 0; i < headers.length; i++)
			{
				String headerName = headers[i];
				List<String> listOfColValueForCurrentRow = mapResult.get(headerName);

				String colValue = "";
				if (i < currentRowColCount) // Means current have have the
											// column
					colValue = reader.get(i) == null ? "" : reader.get(i);
				listOfColValueForCurrentRow.add(colValue);
			}
		}
	}

	public CSVReader(String fileName) throws IOException, MalformedFileException {
		CsvReader reader = new CsvReader(new FileReader(fileName));
		loadFile(reader);
		reader.close();
	}

	/**
	 * Return the list of headers
	 * 
	 * @return
	 */
	public List<String> getHeaders()
	{
		if (mapResult.isEmpty())
			return new ArrayList<String>();

		return new ArrayList<String>(mapResult.keySet());
	}

	/**
	 * Return the number of rows
	 * 
	 * @return
	 */
	public int getRowCount()
	{
		if (mapResult.isEmpty())
			return 0;

		String firstHeaderName = new ArrayList<String>(mapResult.keySet()).get(0);
		return mapResult.get(firstHeaderName).size();
	}

	/**
	 * Return total column count, this is basically will be header count
	 * 
	 * @return
	 */
	public int getColumnCount()
	{
		return mapResult.keySet().size();
	}

	/**
	 * Return the String for specific column name and row index
	 * 
	 * @param columnName
	 *            - The name of header
	 * @param rowIndex
	 *            - Index of row
	 * @return
	 * @throws ColumnNotFoundException
	 *             - If specific column not found
	 */
	public String getValue(String columnName, int rowIndex) throws ColumnNotFoundException
	{
		List<String> listOfColValue = mapResult.get(columnName);
		if (listOfColValue == null)
		{
			throw new ColumnNotFoundException("Column name: " + columnName + ", not found");
		}
		rowIndex--;
		return listOfColValue.get(rowIndex);
	}

	/**
	 * Return the list of column values
	 * 
	 * @param columnName
	 *            - The name of header
	 * @return
	 * @throws ColumnNotFoundException
	 *             - If specific column not found
	 */
	public List<String> getColumn(String columnName) throws ColumnNotFoundException
	{
		List<String> listOfColValue = mapResult.get(columnName);
		if (listOfColValue == null)
		{
			throw new ColumnNotFoundException("Column name: " + columnName + ", not found");
		}

		return listOfColValue;
	}

	/**
	 * Return the String for specific column index and row index, It will be
	 * better if call {@link #getValue(String, int)} directly.
	 * 
	 * @param colIndex
	 *            - index of column
	 * @param rowIndex
	 *            - index of row
	 * @return
	 * @throws ColumnNotFoundException
	 *             - If specific column not found
	 */
	public String getValue(int colIndex, int rowIndex) throws ColumnNotFoundException
	{
		colIndex--;
		String columnName = new ArrayList<String>(mapResult.keySet()).get(colIndex);

		return getValue(columnName, rowIndex);
	}

	/**
	 * This will return the map of ColumnName-ColumnValue of given row index
	 * 
	 * @param rowIndex
	 * @return
	 */
	public Map<String, String> getRow(int rowIndex)
	{
		Map<String, String> mapOfColNameToValue = new LinkedHashMap<String, String>();

		List<String> listOfHeaders = getHeaders();
		rowIndex--;
		for (String header : listOfHeaders)
		{
			List<String> listOfColumnValues = mapResult.get(header);
			mapOfColNameToValue.put(header, listOfColumnValues.get(rowIndex));
		}

		return mapOfColNameToValue;
	}
}
