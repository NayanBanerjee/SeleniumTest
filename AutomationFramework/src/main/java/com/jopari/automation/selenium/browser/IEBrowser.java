package com.jopari.automation.selenium.browser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.webdriver.util.WebDriverUtil;

public class IEBrowser implements IInternetBrowser
{
	DesiredCapabilities capabilities;
	
	public IEBrowser()
	{
		capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				false);
		capabilities.setCapability(
				InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,
				UnexpectedAlertBehaviour.ACCEPT);	
	}
	
	@Override
	public WebDriver start(String url) throws Exception
	{
		WebDriver driver = new InternetExplorerDriver(capabilities);
		driver.get(url);
		Thread.sleep(2000);
		System.out.println("INSIDE IE Explorer");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	@Override
	public void stop() throws Exception
	{
		String IEDriver = "IEDriverServer.exe"; // IE process
		String iexplore = "iexplore.exe";
		Runtime.getRuntime().exec("taskkill /F /IM " + IEDriver);
		Thread.sleep(3000); // Allow OS to kill the process
		Runtime.getRuntime().exec("taskkill /F /IM " + iexplore);
		Thread.sleep(3000); // Allow OS to kill the process
	}
	
	@Override
	public DesiredCapabilities getCapabilities()
	{
		return capabilities;
	}
	
	@Override
	public int getDelayMultiplier() {
		return 4;
	}
	
	public URL getDriverPath()
	{
		return Thread.currentThread().getContextClassLoader().getResource("IEDriverServer.exe");
	}

	@Override
	public void handlePopupWindow(WebDriver driver) throws Exception 
	{
		WebDriverUtil.handlePopupWindow(driver);		
	}

	@Override
	public List<String> getKillCommand(String os)
	{
		List<String> listOfCommands = new ArrayList<String>();
		listOfCommands.add("taskkill");
		listOfCommands.add("/f");
		listOfCommands.add("/IM");
		listOfCommands.add("iexplore.exe");
		 
		return listOfCommands;
	}
}