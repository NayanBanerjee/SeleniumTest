package com.jopari.automation.selenium.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jopari.automation.selenium.base.Constants;
import com.jopari.automation.webdriver.util.WebDriverUtil;

public class FirefoxBrowser implements IInternetBrowser
{
	DesiredCapabilities capabilities;
	
	public FirefoxBrowser()
	{
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(
				CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
				UnexpectedAlertBehaviour.ACCEPT);	
		
		
	}
	
	@Override
	public WebDriver start(String url) throws Exception
	{
		FirefoxProfile Selenium = new FirefoxProfile();
		Selenium.setPreference("network.automatic-ntlm-auth.trusted-uris", "aspire.infogain.com");
		
		WebDriver driver = new FirefoxDriver(Selenium);
		
		driver.get(url);
		Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	@Override
	public void stop() throws Exception
	{
		
	}
	
	@Override
	public DesiredCapabilities getCapabilities()
	{
		return capabilities;
	}
	
	@Override
	public int getDelayMultiplier() {
		return 1;
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
		
		if (os.startsWith(Constants.PREFIX_WINDOW_OS))
		{// Window os
			listOfCommands.add("taskkill");
			listOfCommands.add("/f");
			listOfCommands.add("/IM");
			listOfCommands.add("firefox.exe");
		} else
		{// Linux, in linux machine only firefox is handled only
			listOfCommands.add("pkill");
			listOfCommands.add("-9 firefox");
		}
		return listOfCommands;
	}	
}
