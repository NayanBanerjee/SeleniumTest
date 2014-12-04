package com.jopari.automation.selenium.base;

import java.net.URL;

/**
 * 
 * TestConstants contains the application level constants
 * 
 * @author Rajnish
 * 
 */
public class Constants
{
	private static URL _inputdata = null;
	private static URL _chromedriver = null;
	private static URL _iedriver = null;

	static
	{
		_inputdata = Thread.currentThread().getContextClassLoader().getResource("PNCData.xlsx");
		
		_chromedriver = Thread.currentThread().getContextClassLoader().getResource("chromedriver.exe");
		
		_iedriver = Thread.currentThread().getContextClassLoader().getResource("IEDriverServer.exe");
	}
	/**
	 * Database Driver
	 */
	public static final String DATABASE_DRIVER = "oracle.jdbc.OracleDriver";
	/**
	 * URL to database
	 */
	public static final String DATABASE_URL = "jdbc:oracle:thin:@//10.25.1.45:1521/epay";
	/**
	 * Database User Name
	 */
	public static final String DATABASE_USERNAME = "test6eb1global";
	/**
	 * Database Password
	 */
	public static final String DATABASE_PASSWORD = "jopari";
	/**
	 * RunnableTestCase File name
	 */
	public static final String RUNNABLE_TEST_CASES = "runnableTestSuites";

	/**
	 * Directory Name where reports will be generated
	 */
	public static final String REPORTS_DIRECTORY = "reports";

	/**
	 * Prefix for the window os
	 */
	public static final String PREFIX_WINDOW_OS = "win";
	/**
	 * Kill browser before test
	 */
	public static final String KILL_BROWSER = "kill_browser";
	/**
	 * for Internet Exploere
	 */
	public static final String INTERNET_EXPLORER = "iexplore";
	/**
	 * For Firefox
	 */
	public static final String FIREFOX = "firefox";
	/**
	 * For Chrome
	 */
	public static final String CHROME = "chrome";
	/**
	 * For Smtp Server
	 */
	public static final String SMTP_SERVER = "smtp-server";
	/**
	 * For smtp auth user
	 */
	public static final String SMTP_AUTH_USER = "smtp-auth-user";
	/**
	 * For smtp auth password
	 */
	public static final String SMTP_AUTH_PWD = "smtp-auth-pwd";
	/**
	 * For From email
	 */
	public static final String EMAIL_FROM = "email-from";
	/**
	 * For To email
	 */
	public static final String EMAIL_TO = "email-to";
	/**
	 * TestRun Type
	 */
	public static final String TEST_RUN_TYPE = "test_type";

	/**
	 * Notify
	 */
	public static final String NOTIFY = "notify";
	/**
	 * Default Email address for default From Email
	 */
	public static final String DEFAULT_EMAIL_FROM = "test-suite@infogain.com";

	public static final String TEST_DATA = _inputdata.getPath();

	public static final String CHROME_DRIVER = _chromedriver.getPath();

	public static final String IE_DRIVER = _iedriver.getPath();
}
