package com.jopari.automation.selenium.browser;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface IInternetBrowser
{
	public WebDriver start(String url) throws Exception;
	public void stop() throws Exception;
	public DesiredCapabilities getCapabilities();
	public int getDelayMultiplier();
	public void handlePopupWindow(WebDriver driver) throws Exception;
	public List<String> getKillCommand(String os);
}