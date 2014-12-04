package com.jopari.automation.testng.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import com.jopari.automation.selenium.logging.InfoEvent;
import com.jopari.automation.selenium.logging.TestLogger;
import com.jopari.automation.util.calendar.CalendarUtils;
import com.jopari.automation.util.io.IOUtilities;

/**
 * Contains helper methods for TestNG
 * 
 * @author Kanika Agarwal
 * 
 */
public class TestNGHelper
{

	/**
	 * Parses a XML file to XmlSuite
	 * 
	 * @param fileName
	 *            Name of <code>xml</code> file containing the test suite
	 *            description
	 * @return List
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<XmlSuite> parse(String fileName) throws ParserConfigurationException, SAXException, IOException
	{
		TestLogger.log(new InfoEvent(TestNGHelper.class, "Generating XMLSuites from: " + fileName));
		return new Parser(fileName).parseToList();
	}

	/**
	 * Sets the title for TestNG reports and configures other properties
	 * required for the report ng
	 * 
	 * @param reportTitle
	 *            Report title
	 */
	private static void configureReportNG(String reportTitle)
	{
		
		System.getProperties().put("org.uncommons.reportng.stylesheet", "resources" + File.separator + "hudsonesque.css");
		System.getProperties().put("org.uncommons.reportng.title", reportTitle);
		
	}

	/**
	 * Runs {@link XmlSuite}. The method runs a {@link List} of {@link XmlSuite}
	 * 
	 * @param xmlSuites
	 *            List of test suites to be run
	 * @throws IOException
	 */
	public static void executesTest(List<XmlSuite> xmlSuites, String browser) throws IOException
	{
		// Disable the default testng reporting

		System.out.println("LIST COUNT " + xmlSuites.size());
		System.setProperty("browserString", browser);
		String currentTimeStamp = CalendarUtils.formatDate(new Date(), CalendarUtils.TIME_FORMAT_REPORT_NAME);
		//for (XmlSuite suit : xmlSuites)
		//{
			TestNG testng = new TestNG(false);
			//TestNG testng = new TestNG(true);
			XmlSuite suit=xmlSuites.get(0);////////
			String suitname = suit.getName();
			IOUtilities.createDirectory(currentTimeStamp, suitname);
			String timestampDirectory = currentTimeStamp + File.separator + suitname;
			// Create report directories
			createReportDirectory(testng, "testReports");
			// Make directory for LoggingSelenium reports
			//configureReportNG("Test Suite Results");
			configureListenersForTestNG(testng);
			 //testng.setXmlSuites(xmlSuites);
			//testng.setCommandLineSuite(suit);
			
			//testng.setXmlSuites(xmlSuites);
			testng.setCommandLineSuite(suit);
			System.out.println("============dir== "+ testng.getOutputDirectory());

			
			testng.run();
		//}
	}

	/**
	 * @param testng
	 * @param currentTimeStamp
	 * @throws IOException
	 */
	private static  void createReportDirectory(TestNG testng, String currentTimeStamp) throws IOException
	{
		/*
		final String SRC_FOLDER="D:\\july11 workspace\\AutomationScript\\reports";
		File baseDirectoryName = new File(SRC_FOLDER);
		if(baseDirectoryName.exists())
		{
		IOUtilities.deleteDirectory(baseDirectoryName);
		}*/
		
		IOUtilities.createDirectory("reports", currentTimeStamp);
		// Set the output directory for ReportNG and selenium reporting
		String reportDirectory = "reports" + File.separator + currentTimeStamp;
		testng.setOutputDirectory(reportDirectory);
		System.getProperties().put("reportPath", reportDirectory);
		
	}

	/**
	 * Configures the listeners for TestNG for reporting
	 * 
	 * @param testNG
	 *            TestNG
	 */
	@SuppressWarnings("rawtypes")
	private static void configureListenersForTestNG(TestNG testNG)
	{
		List<Class> classesList = new ArrayList<Class>();
		classesList.add(com.jopari.automation.listener.CustomTestListener.class);
		classesList.add(com.jopari.automation.listener.CustomTestSuiteListener.class);
		classesList.add(org.uncommons.reportng.HTMLReporter.class);
		classesList.add(org.uncommons.reportng.JUnitXMLReporter.class);
		classesList.add(org.testng.reporters.XMLReporter.class);

		testNG.setListenerClasses(classesList);
	}

}
