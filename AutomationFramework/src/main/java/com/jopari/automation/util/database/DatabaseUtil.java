/**
 * 
 */
package com.jopari.automation.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.selenium.exception.AutomationException;
import com.jopari.automation.selenium.logging.ErrorEvent;
import com.jopari.automation.selenium.logging.InfoEvent;
import com.jopari.automation.selenium.logging.TestLogger;
import com.jopari.automation.util.exception.ExceptionUtil;

/**
 * DatabaseUtil contains the utility methods to access the
 * database(select,insert,update and delete)
 * 
 * @author Rajnish.Verma
 * 
 */
public class DatabaseUtil
{

	/**
	 * Gets database connection based on connection properties
	 * 
	 * @param properties
	 *            Properties object containing details about database connection
	 * @return <code>Connection</code> object
	 * @throws AutomationException
	 * @see {@link #getConnection(String, String, String, String)}
	 */
	public static Connection getConnection() throws AutomationException
	{

		String dataBaseDriver = Constants.DATABASE_DRIVER;
		String dataBaseURL = Constants.DATABASE_URL;
		String userName = Constants.DATABASE_USERNAME;
		String password = Constants.DATABASE_PASSWORD;

		return getConnection(dataBaseDriver, dataBaseURL, userName, password);
	}

	/**
	 * Get database connection based on passed argument
	 * 
	 * @param databaseDriver
	 *            - Driver of database to load
	 * @param datbaseURL
	 *            - URL of database
	 * @param userName
	 *            - User name to login in database
	 * @param password
	 *            - Password of database to login of specified user
	 * @return <code>Connection</code> object
	 * @throws AutomationException
	 * @see {@link #getConnection(Properties)}
	 */
	public static Connection getConnection(String databaseDriver,
			String datbaseURL, String userName, String password)
			throws AutomationException
	{
		Connection connection = null;

		try
		{
			Class.forName(databaseDriver);
		} catch (ClassNotFoundException e1)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed to load the driver:"
							+ ExceptionUtil.stackTraceToString(e1)));
			throw new AutomationException(e1);
		}

		try
		{
			connection = DriverManager.getConnection(datbaseURL, userName,
					password);
		} catch (SQLException e)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed to open Connection:"
							+ ExceptionUtil.stackTraceToString(e)));
			throw new AutomationException(e);
		}
		return connection;
	}

	/**
	 * This method will run the <code>DML SQL query</code>(for update or delete
	 * row)
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which DML
	 *            will be run.
	 * @param dmlQuery
	 *            - DML Query to run
	 * @return Count of updated/deleted row
	 * @throws AutomationException
	 */
	public static int executeSQL(String dmlQuery) throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class, "Executing: "
					+ dmlQuery));
			rowCount = stmnt.executeUpdate(dmlQuery);
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing DML query:"
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}
		return rowCount;
	}

	/**
	 * This method will run the all the <code>DML SQL query</code> added in the
	 * <code>List</code>(for update or delete row)
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which DML
	 *            will be run.
	 * @param dmlQuery
	 *            - List of DML Queries to run
	 * @return Count of updated/deleted row
	 * @throws AutomationException
	 * @see {@link #executeSQL(Connection, String)}
	 */
	public static int executeSQL(List<String> dmlQuery)
			throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{
			connection.setAutoCommit(false);
			for (String query : dmlQuery)
			{
				TestLogger.log(new InfoEvent(DatabaseUtil.class, "Executing: "
						+ dmlQuery));
				int result = stmnt.executeUpdate(query);
				rowCount += result;
			}
			connection.commit();
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing DML queries:"
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}
		return rowCount;

	}

	/**
	 * The method executes a SQL query <code>baseQuery</code> and then call list
	 * of DML SQL queries on the basis of below condition:
	 * 
	 * <p>
	 * <ul>
	 * <li>If record found it will run the list of DML queries passed in
	 * <code>sqlsToExcuteIfRecordExist</code>
	 * <li>If record does not exist it will execute the list of DML queries
	 * passed in <code>sqlsToExcuteIfRecordDoesnotExist</code>.
	 * </ul>
	 * </p>
	 * 
	 * @param Connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @param baseQuery
	 *            - select query, this will be run first. And the base of the
	 *            result of this query corresponding list of DML queries will be
	 *            called
	 * @param sqlsToExcuteIfRecordExist
	 *            - List of DML SQL queries that needs to be executed when
	 *            record will be found
	 * @param sqlsToExcuteIfRecordDoesnotExist
	 *            - List of insert SQL queries that needs to be executed when
	 *            record will not be found
	 * @return Total count of modified rows, whether updated/deleted
	 * @throws AutomationException
	 */
	public static int executeSQL(String baseQuery,
			List<String> sqlsToExcuteIfRecordExist,
			List<String> sqlsToExcuteIfRecordDoesnotExist)
			throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching records: " + baseQuery));
			ResultSet resultSet = stmnt.executeQuery(baseQuery);
			if (resultSet.next())
			{
				connection.setAutoCommit(false);
				for (String update : sqlsToExcuteIfRecordExist)
				{
					TestLogger.log(new InfoEvent(DatabaseUtil.class,
							"Executing: " + update));
					int result = stmnt.executeUpdate(update);
					rowCount += result;
				}
				connection.commit();
			} else if (!resultSet.next())
			{
				connection.setAutoCommit(false);
				for (String insert : sqlsToExcuteIfRecordDoesnotExist)
				{
					TestLogger.log(new InfoEvent(DatabaseUtil.class,
							"Executing: " + insert));
					int result = stmnt.executeUpdate(insert);
					rowCount += result;
				}
				connection.commit();
			}
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing query: "
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}

		return rowCount;
	}

	/**
	 * This method will return the ticket number generated for new signup user.
	 * With the help of generated ticketNumber user can construct the URL to
	 * directly change of password and security questions.
	 */

	public static String getTicketNumber(String oid)
	{
		String ticketNumber = null;
		try (Connection conn = getConnection())
		{
			String sql = "select ticketid from t_ticket where oid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, oid);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next())
			{
				ticketNumber = resultSet.getString("ticketid");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ticketNumber;
	}

	/**
	 * This method will execute for DML query conditionally, if through query
	 * <code>baseQuery</code> record not found.
	 * <p>
	 * This is basically used in the case when user want to insert some row in
	 * data base, if that row not exist in the data base.
	 * 
	 * So first this will check by executing the <code>baseQuery</code> that
	 * record exist or not, If not then execute that DML Statement, in most of
	 * cases this will be insert statement.
	 * </P>
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @param baseQuery
	 *            - select query, this will be run first. And the base of the
	 *            result of this query, the
	 *            <code>sqlToExecuteIfRecordNotExist</code> will be executed.
	 *            (If record not found)
	 * @param sqlToExecuteIfRecordNotExist
	 *            - The DML SQL query which will be execute if record not found
	 * @return Total count of modified rows, whether updated/deleted
	 * @throws AutomationException
	 * @see {@link #executeSQLIfRecordExist(Connection, String, String)}
	 */
	public static int executeSQLIfRecordNotExist(String baseQuery,
			String sqlToExecuteIfRecordNotExist) throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching records: " + baseQuery));
			ResultSet resultSet = stmnt.executeQuery(baseQuery);
			if (!resultSet.next())
			{
				TestLogger.log(new InfoEvent(DatabaseUtil.class, "Executing: "
						+ sqlToExecuteIfRecordNotExist));
				int result = stmnt.executeUpdate(sqlToExecuteIfRecordNotExist);
				rowCount += result;
			}
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in  executing insert SQL: "
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}
		return rowCount;
	}

	/**
	 * This method will execute for DML query conditionally, if through query
	 * <code>baseQuery</code> record not found.
	 * <p>
	 * This is basically used in the case when user want to insert some rows in
	 * data base, if some specific row not exist in the data base.
	 * 
	 * So first this will check by executing the <code>baseQuery</code> that
	 * record exist or not, If not then execute that all DML Statement passed in
	 * <code>sqlsToExecuteIfRecordNotExist</code>, in most of cases that will be
	 * insert statements.
	 * </P>
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @param baseQuery
	 *            - select query, this will be run first. And the base of the
	 *            result of this query, all SQL queries
	 *            <code>sqlsToExecuteIfRecordNotExist</code> will be executing.
	 *            (If record not found)
	 * @param sqlsToExecuteIfRecordNotExist
	 *            - The list of DML SQLs query, which will be execute if record
	 *            not found
	 * @return Total count of modified rows, whether updated/deleted
	 * @throws AutomationException
	 * @see #executeSQLIfRecordNotExist(Connection, String, String)
	 * @see #executeSQLsIfRecordExist(Connection, String, List)
	 */
	public static int executeSQLsIfRecordNotExist(String baseQuery,
			List<String> sqlsToExecuteIfRecordNotExist)
			throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching Records: " + baseQuery));
			ResultSet resultSet = stmnt.executeQuery(baseQuery);
			if (!resultSet.next())
			{
				connection.setAutoCommit(false);
				for (String sql : sqlsToExecuteIfRecordNotExist)
				{
					TestLogger.log(new InfoEvent(DatabaseUtil.class,
							"Executing: " + sql));
					int result = stmnt.executeUpdate(sql);
					rowCount += result;
				}
				connection.commit();
			}
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing insert SQLs: "
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}
		return rowCount;
	}

	/**
	 * This method will execute for DML query conditionally, if through query
	 * <code>baseQuery</code> record found.
	 * <p>
	 * This is basically used in the case when user want to update/delete some
	 * rows in data base, if some specific row exist in the data base.
	 * 
	 * So first this will check by executing the <code>baseQuery</code> that
	 * record exist or not, If yes then execute that all DML Statement passed in
	 * <code>sqlToExecuteIfRecordExist</code>, in most of cases that will be
	 * update/delete statements.
	 * </P>
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @param baseQuery
	 *            - select query, this will be run first. And the base of the
	 *            result of this query, SQL querie
	 *            <code>sqlToExecuteIfRecordExist</code> will be executing. (If
	 *            record found)
	 * @param sqlToExecuteIfRecordExist
	 *            - The DML SQL query which will be execute if record not found
	 * @return Total count of modified rows, whether updated/deleted
	 * @throws AutomationException
	 * @see {@link #executeSQLIfRecordNotExist(Connection, String, String)}
	 */
	public static int executeSQLIfRecordExist(String baseQuery,
			String sqlToExecuteIfRecordExist) throws AutomationException
	{
		int rowCount = 0;
		try (Connection connection = getConnection();
				Statement stmnt = connection.createStatement())
		{

			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching records: " + baseQuery));
			ResultSet resultSet = stmnt.executeQuery(baseQuery);
			if (resultSet.next())
			{
				TestLogger.log(new InfoEvent(DatabaseUtil.class, "Executing: "
						+ sqlToExecuteIfRecordExist));
				int result = stmnt.executeUpdate(sqlToExecuteIfRecordExist);
				rowCount += result;
			}
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing updateSQL:"
							+ ExceptionUtil.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}
		return rowCount;
	}

	/**
	 * This method will execute all for DML queries conditionally, if through
	 * query <code>baseQuery</code> record found.
	 * 
	 * <p>
	 * This is basically used in the case when user want to update/delete some
	 * rows in data base, if some specific row exist in the data base.
	 * 
	 * So first this will check by executing the <code>baseQuery</code> that
	 * record exist or not, If yes then execute that all DML Statement passed in
	 * <code>sqlsToExecuteIfRecordExist</code>, in most of cases that will be
	 * update/delete statements.
	 * </P>
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @param baseQuery
	 *            - select query, this will be run first. And the base of the
	 *            result of this query, SQL queries passed in
	 *            <code>executeSQLsIfRecordExist</code> will be executed. (If
	 *            record found)
	 * @param sqlsToExecuteIfRecordExist
	 *            - The list of DML SQLs query, which will be execute if record
	 *            found
	 * @return Total count of modified rows, whether updated/deleted
	 * @throws AutomationException
	 * @see #executeSQLIfRecordExist(Connection, String, String)
	 * @see #executeSQLsIfRecordNotExist(Connection, String, List)
	 */
	public static int executeSQLsIfRecordExist(String baseQuery,
			List<String> sqlsToExecuteIfRecordExist) throws AutomationException
	{
		int rowCount = 0;
		try (Connection conn = getConnection();
				Statement stmnt = conn.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching records: " + baseQuery));
			ResultSet resultSet = stmnt.executeQuery(baseQuery);
			if (resultSet.next())
			{
				conn.setAutoCommit(false);
				for (String update : sqlsToExecuteIfRecordExist)
				{
					TestLogger.log(new InfoEvent(DatabaseUtil.class,
							"Executing: " + update));
					int result = stmnt.executeUpdate(update);
					rowCount += result;
				}
				conn.commit();
			}
		} catch (SQLException ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing updateSQLs:"
							+ ExceptionUtil.stackTraceToString(ex)));

			throw new AutomationException(ex);
		}
		return rowCount;
	}

	/**
	 * The method executes a SQL SELECT query and returns a list of records for
	 * same.
	 * 
	 * @param query
	 *            - query to execute
	 * @param properties
	 *            Properties object containing details required for obtaining
	 *            database connection
	 * @return List of Map where in Map's key is the column name and value is
	 *         the String version of the column's data, each <code>Map</code> in
	 *         <code>list</code> will define one row, while whole
	 *         <code>list</code> will describe whole table
	 * @throws AutomationException
	 * @throws SQLException
	 * @see #getRecords(String, Connection)
	 */
	public static List<Map<String, String>> getRecords(String query,
			Properties properties) throws SQLException, AutomationException
	{

		return getRecords(query);
	}

	/**
	 * The method executes a SQL SELECT query and returns a list of records for
	 * same.
	 * 
	 * @param query
	 *            - query to execute
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which
	 *            query will be run.
	 * @return List of Map where in Map's key is the column name and value is
	 *         the String version of the column's data, each <code>Map</code> in
	 *         <code>list</code> will define one row, while whole
	 *         <code>list</code> will describe whole table
	 * @throws SQLException
	 * @throws AutomationException
	 * @see #getRecords(String, Properties)
	 */
	public static List<Map<String, String>> getRecords(String query)
			throws SQLException, AutomationException
	{
		List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();

		try (Connection conn = getConnection();
				Statement stmnt = conn.createStatement())
		{
			TestLogger.log(new InfoEvent(DatabaseUtil.class,
					"Fetching records: " + query));
			ResultSet resultSet = stmnt.executeQuery(query);

			// If some records are expected
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();

			while (resultSet.next())
			{
				Map<String, String> recordAsMap = new HashMap<String, String>();
				for (int i = 1; i <= columnCount; i++)
				{
					String columnName = resultSetMetaData.getColumnName(i);
					String value = resultSet.getString(columnName);
					recordAsMap.put(columnName, value);
				}

				recordList.add(recordAsMap);
			}
		} catch (SQLException e)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class,
					"Failed in executing select Query: "
							+ ExceptionUtil.stackTraceToString(e)));
			throw new AutomationException(e);
		}
		return recordList;
	}

	/**
	 * Compare values in <code>expectedRecord</code> and
	 * <code>expectedSize</code> with list <code>dbComparisonResult</code>
	 * <ul>
	 * <br>
	 * Note:</br> One Map of List canbe assumed as one row of table. <br>
	 * Whole List can be assumed as whole table.
	 * 
	 * @param expectedRecord
	 *            - Fetched from recordSet defined in descriptor XML, which have
	 *            all the records element and column element name and their
	 *            expected values.
	 * 
	 * @param actualRecords
	 *            - This will contains the result rows in list manner after the
	 *            execution of SQL Query.
	 * @return - true if validation successful false otherwise
	 * @throws AutomationException
	 */
	public static boolean verifyRecords(
			List<Map<String, String>> expectedRecords,
			List<Map<String, String>> actualRecords) throws AutomationException
	{
		boolean result = true;

		try
		{
			// This is record loop, Will iterate till number of records
			for (Map<String, String> expectedRecordAsRow : expectedRecords)
			{
				/**
				 * Through this bool, will decide that, searching of column need
				 * to be done on "current Map" of iteration of List
				 * 
				 * or from
				 * 
				 * previously saved map as replica.
				 */
				boolean isPreviousColumnMatched = false;

				/**
				 * This is maintain the index of last ignored Map, while
				 * matching the column.
				 * 
				 * Here canbe the case that matching the column values with
				 * replica map may fail at end. so that time need to restart the
				 * matching from first column but here need to know from which
				 * map index of List we have to start this, means will not start
				 * from zero index Map of List.
				 * 
				 * So this integer var will maintain the index from which we
				 * start ignoring the <code>rowMap</code> and start using the
				 * <code>replicaOfRowMap</code>
				 */
				int lastIgnoreIndexOfMap = 0;
				Set<String> columnNames = expectedRecordAsRow.keySet();

				// This is column loop
				for (int columnIndex = 0; columnIndex < columnNames.size(); columnIndex++)
				{
					String expectedColumnName = new ArrayList<String>(
							columnNames).get(columnIndex);
					String expectedColumnValue = expectedRecordAsRow
							.get(expectedColumnName);

					int mapIndex = lastIgnoreIndexOfMap;

					// This is List loop
					for (; mapIndex < actualRecords.size()
							&& !isPreviousColumnMatched; mapIndex++)
					{
						Map<String, String> actualRecordAsRow = actualRecords
								.get(mapIndex);
						// Means column name not found
						if (!actualRecordAsRow.containsKey(expectedColumnName))
						{
							throw new AutomationException("Column Name: "
									+ expectedColumnName
									+ " not found, from expected record: "
									+ expectedRecordAsRow);
						}

						if (actualRecordAsRow.get(expectedColumnName).equals(
								expectedColumnValue))
						{
							lastIgnoreIndexOfMap = mapIndex + 1;
							isPreviousColumnMatched = true;

							/**
							 * Exit from loop, we got one Map which have the
							 * expected value for Column, So further check will
							 * be done on that Map till failure Or column ends
							 */
							break;
						}
					}

					// Means column not found, have searched in all Maps
					if (!isPreviousColumnMatched
							&& mapIndex == actualRecords.size())
					{
						isPreviousColumnMatched = false;
						result = false;
						// Exit from column loop, because no need to match for
						// next column
						break;
					}

					// At first match in above loop, this will do again same
					// matching
					if (isPreviousColumnMatched)
					{
						Map<String, String> mathedActualRowMap = actualRecords
								.get(lastIgnoreIndexOfMap - 1);
						// Means column name not found
						if (!mathedActualRowMap.containsKey(expectedColumnName))
						{
							throw new AutomationException("Column Name: "
									+ expectedColumnName
									+ " not found, from expected record: "
									+ expectedRecordAsRow);
						}

						if (!mathedActualRowMap.get(expectedColumnName).equals(
								expectedColumnValue))
						{
							/**
							 * ohh.. we need to start matching from first column
							 * again from lastIgnoreIndexOfMap of List. :(
							 * 
							 * Please exit from List loop as well as column loop
							 * and start column loop from fresh and List loop
							 * from <code>lastIgnoreIndexOfMap</code>.
							 */
							columnIndex = -1;

							// This will again start the loop
							isPreviousColumnMatched = false;
							mathedActualRowMap = null;
						}
					}
				}
			}
		} catch (Exception ex)
		{
			TestLogger.log(new ErrorEvent(DatabaseUtil.class, ExceptionUtil
					.stackTraceToString(ex)));
			throw new AutomationException(ex);
		}

		return result;
	}

	/**
	 * This method will verify the expected records with, actual records after
	 * execution of query
	 * 
	 * @param connection
	 *            - The reference of <code>Connection</code> Object on which DML
	 *            will be run.
	 * @param query
	 *            - select query, this will be run first.
	 * @param expectedRecords
	 *            - List of Map<String, String>, which will be matched with
	 *            actual results,
	 *            <p>
	 *            <ul>
	 *            <li>In this List each <code>Map<String, String></code> will
	 *            define a row
	 *            <li>In the <code>Map</code> each <code>Key</code> of Map will
	 *            define the column name and value of that key will define
	 *            column's actual value.
	 *            </ul>
	 * @return true if matched each row of expected as whole with Actual Records
	 * @throws AutomationException
	 * @throws SQLException
	 * @see #verifyRecords(List, List)
	 * @see #getRecords(String, Connection)
	 */
	public static boolean verifyRecords(Connection connection, String query,
			List<Map<String, String>> expectedRecords)
			throws AutomationException, SQLException
	{

		List<Map<String, String>> actualRecords = getRecords(query);
		return verifyRecords(expectedRecords, actualRecords);
	}
}
