package com.jopari.automation.listener;

import java.util.Collection;

import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

/**
 * CustomTestSuiteListener listens to the events related to a test suite.
 * 
 * @author Bharat Mehta
 * 
 */
public class CustomTestSuiteListener implements ITestListener
{

	/**
	 * The method removes duplicate entries from list of passed tests and failed
	 * tests. If a test is found in both passed and failed lists, we consider
	 * the same as failed.
	 */
	public void onFinish(ITestContext context)
	{
		IResultMap passedTests = context.getPassedTests();
		IResultMap failedTests = context.getFailedTests();

		Collection<ITestNGMethod> passedTestMethods = passedTests
				.getAllMethods();
		Collection<ITestNGMethod> failedTestMethods = failedTests
				.getAllMethods();
		for (ITestNGMethod method : failedTestMethods)
		{
			if (passedTestMethods.contains(method))
			{
				passedTestMethods.remove(method);
			}
		}

	}

	public void onStart(ITestContext context)
	{
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult context)
	{
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult context)
	{
		// TODO Auto-generated method stub

	}

	public void onTestSkipped(ITestResult context)
	{
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult context)
	{
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult context)
	{
		// TODO Auto-generated method stub

	}

}
