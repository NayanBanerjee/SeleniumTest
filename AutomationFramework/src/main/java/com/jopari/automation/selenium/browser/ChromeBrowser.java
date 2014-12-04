package com.jopari.automation.selenium.browser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.webdriver.util.WebDriverUtil;

public class ChromeBrowser implements IInternetBrowser
{
	DesiredCapabilities capabilities;
	
	public ChromeBrowser()
	{
		capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(
				CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
				UnexpectedAlertBehaviour.ACCEPT);	
	}
	
	@Override
	public WebDriver start(String url) throws Exception
	{
		WebDriver driver = new ChromeDriver(capabilities);
		driver.get(url);
		Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	@Override
	public void stop() throws Exception
	{
		String chromeDriver = "chromedriver.exe"; // chrome process
		String chrome = "chrome.exe";
		Runtime.getRuntime().exec("taskkill /F /IM " + chromeDriver);
		Thread.sleep(3000); // Allow OS to kill the process
		Runtime.getRuntime().exec("taskkill /F /IM " + chrome);
		Thread.sleep(3000); // Allow OS to kill the process
	}

	
	@Override
	public DesiredCapabilities getCapabilities()
	{
		return capabilities;
	}
	
	@Override
	public int getDelayMultiplier() {
		return 3;
	}
	
	public URL getDriverPath()
	{
		return Thread.currentThread().getContextClassLoader().getResource("chromedriver.exe");
	}

	@Override
	public void handlePopupWindow(WebDriver driver) throws Exception 
	{
		WebDriverUtil.handlePopupWindow(driver);	
	}

	@Override
	public List<String> getKillCommand(String os)
	{
		ArrayList<String> listOfCommands = new ArrayList<String>();
		listOfCommands.add("taskkill");
		listOfCommands.add("/f");
		listOfCommands.add("/IM");
		listOfCommands.add("chrome.exe");
		
		return listOfCommands;		
	}
	
}
