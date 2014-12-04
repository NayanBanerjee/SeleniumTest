package com.jopari.automation.selenium.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.selenium.browser.ChromeBrowser;
import com.jopari.automation.selenium.browser.FirefoxBrowser;
import com.jopari.automation.selenium.browser.IEBrowser;
import com.jopari.automation.selenium.browser.IInternetBrowser;
import com.jopari.automation.selenium.logging.InfoEvent;
import com.jopari.automation.selenium.logging.TestLogger;
import com.thoughtworks.selenium.SeleneseTestBase;

/**
 * This is the parent class inherited by all test cases . It initialise the
 * browser . It also has various verification methods
 * 
 * @author Kanika.Agarwal
 * 
 */
public class BaseTestCase extends SeleneseTestBase
{

	private BufferedWriter loggingWriter;
	private String baseUrl;
	private WebDriver driver;
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap;
	protected String SYNC_TIME = "3000";
	private IInternetBrowser browser;

	/**
	 * Creates a new driver object and starts it using the specified baseUrl and
	 * browser string.
	 * 
	 * @param url
	 *            the baseUrl for your tests
	 * @param browserString
	 *            the browser to use, e.g. *firefox
	 * @throws Exception
	 */
	@BeforeSuite
	@Parameters({ "selenium.url", "selenium.repo.url", "setupContext" })
	public void setUp(@Optional String url, @Optional String repoUrl, ITestContext testContext,
			@Optional("false") Boolean setUpContext) throws Exception
	{
		Boolean toKillZombies = false;
		String browserString = System.getProperty("browserString");
		
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER);
		System.setProperty("webdriver.ie.driver", Constants.IE_DRIVER);
		
		System.setProperty("webdriver.pnc.url", url);
		System.setProperty("webdriver.repo.url", repoUrl);
		System.out.println("Using " + browserString.substring(1) + " browser option");
		if (Constants.FIREFOX.equalsIgnoreCase(browserString.substring(1)))
		{
			browser = new FirefoxBrowser();			
		} 
		else if (Constants.INTERNET_EXPLORER.equalsIgnoreCase(browserString.substring(1)))
		{
			browser = new IEBrowser();
		} 
		else if (Constants.CHROME.equalsIgnoreCase(browserString.substring(1)))
		{
			browser = new ChromeBrowser();
		} 
		else
		{
			System.out.println("Browser not supported");
			return;
		}
		
		if (toKillZombies)
		{
			killBrowser();
		}
		baseUrl = url;
		driver = browser.start(url);
	}

	@BeforeClass
	@Parameters({ "selenium.restartSession", "selenium.sync_time" })
	public void getSelenium(@Optional("false") boolean restartSession,
			@Optional String sync_time)
	{
		// processor = staticProcessor;
		if (sync_time != null)
			SYNC_TIME = sync_time;
		if (restartSession)
		{
			// driver.quit();
		}
	}

	/**
	 * Returns driver instance
	 * 
	 * @return the driver
	 */
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public IInternetBrowser getBrowser()
	{
		return browser;
	}

	/**
	 * 
	 * @param method
	 */
	public BufferedWriter getWriter()
	{
		return loggingWriter;
	}

	@SuppressWarnings("static-access")
	@BeforeMethod
	public void setTestContext(Method method)
	{
		/*selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.setContext(method.getDeclaringClass().getSimpleName() + "."
				+ method.getName());
		this.verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();*/

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.thoughtworks.selenium.SeleneseTestBase#verifyEquals(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void verifyEquals(Object s1, Object s2)
	{
		try
		{
			assertEquals(s1, s2);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.thoughtworks.selenium.SeleneseTestBase#verifyEquals(java.lang.String
	 * [], java.lang.String[])
	 */
	@Override
	public void verifyEquals(String[] s1, String[] s2)
	{
		String comparisonDumpIfNotEqual = null;
		boolean misMatch = false;
		if (s1.length != s2.length)
		{
			misMatch = true;
		}
		for (int j = 0; j < s1.length; j++)
		{
			if (!seleniumEquals(s1[j], s2[j]))
			{
				misMatch = true;
				break;
			}
		}
		if (misMatch)
		{
			comparisonDumpIfNotEqual = "Expected " + stringArrayToString(s1)
					+ " but saw " + stringArrayToString(s2);
		}

		if (comparisonDumpIfNotEqual != null)
		{
			verificationErrors.append(comparisonDumpIfNotEqual);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.selenium.SeleneseTestBase#verifyTrue(boolean)
	 */
	@Override
	public void verifyTrue(boolean condition)
	{
		try
		{
			assertTrue(condition);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.selenium.SeleneseTestBase#verifyEquals(boolean,
	 * boolean)
	 */
	@Override
	public void verifyEquals(boolean actual, boolean expected)
	{
		try
		{
			assertEquals(actual, expected);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.selenium.SeleneseTestBase#verifyFalse(boolean)
	 */
	@Override
	public void verifyFalse(boolean b)
	{
		try
		{
			assertFalse(b);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.thoughtworks.selenium.SeleneseTestBase#verifyNotEquals(java.lang.
	 * Object, java.lang.Object)
	 */
	@Override
	public void verifyNotEquals(Object s1, Object s2)
	{
		try
		{
			assertNotEquals(s1, s2);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.selenium.SeleneseTestBase#verifyNotEquals(boolean,
	 * boolean)
	 */
	@Override
	public void verifyNotEquals(boolean s1, boolean s2)
	{
		try
		{
			assertNotEquals(s1, s2);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	/**
	 * This method is a wrapper over SeleneseTestBase assertEquals
	 * 
	 * @param actual
	 * @param expected
	 */
	public void verifyEquals(Object[] actual, Object[] expected)
	{
		try
		{
			assertEquals(actual, expected);
		} catch (Throwable e)
		{
			addVerificationFailure(e);
		}
	}

	private static String stringArrayToString(String[] sa)
	{
		StringBuffer sb = new StringBuffer("{");
		for (int j = 0; j < sa.length; j++)
		{
			sb.append(" ").append("\"").append(sa[j]).append("\"");
		}
		sb.append(" }");
		return sb.toString();
	}

	/**
	 * This method closes the diver and opened browser executables
	 * 
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws IOException
	{
		try
		{
			driver.quit();
			if (null != loggingWriter)
			{
				loggingWriter.close();
			}

			browser.stop();

			TestLogger.log(new InfoEvent(this.getClass(), "Killed processes"));

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// TODO: UPDATE THIS WITH NEW IInternetBrowser functionality
	/**
	 * This method will be called when parameter <code>browser.killZombie</code>
	 * in XML file defined as <code>true</code>
	 * 
	 * This will execute command to kill process based on operating syetem.
	 * (Linux is not tested)
	 */
	private void killBrowser()
	{
		String osName = System.getProperty("os.name").toLowerCase();
		try
		{
			List<String> killCommands = browser.getKillCommand(osName);
			executeCommand(killCommands);
		} 
		catch (IOException e)
		{
			// No exception handled, because exception may come when process was
			// not already running
		}
	}

	private String executeCommand(List<String> listOfCommandWithArgs)
			throws IOException
	{
		ProcessBuilder builder = new ProcessBuilder(listOfCommandWithArgs);
		builder.redirectErrorStream(true);
		Process process = builder.start();
		InputStreamReader isr = new InputStreamReader(new BufferedInputStream(
				process.getInputStream()));
		BufferedReader reader = new BufferedReader(isr);
		StringBuffer outputBuffer = new StringBuffer();
		String line = null;

		while ((line = reader.readLine()) != null)
			outputBuffer.append(line);

		return outputBuffer.toString();
	}

	/**
	 * This method asserts faliure of a message
	 * 
	 * @param message
	 */
	public static void fail(String message)
	{
		Assert.fail(message);
	}

	/**
	 * This method fetches a list of all verification faliures
	 * 
	 * @return List<Throwable>
	 */
	public static List<Throwable> getVerificationFailures()
	{
		List<Throwable> verificationFailures = verificationFailuresMap
				.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>()
				: verificationFailures;
	}

	private static void addVerificationFailure(Throwable e)
	{
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(),
				verificationFailures);

		verificationFailures.add(e);
	}

}
