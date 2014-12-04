package com.jopari.automation.webdriver.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jopari.automation.selenium.logging.ErrorEvent;
import com.jopari.automation.selenium.logging.TestLogger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class contains all generic functions for Webdrivers. e.g. findelement on
 * webpage with different parameters , click on button, switch to popup window.
 * 
 * @author Rajnish.Verma
 * 
 */
public class WebDriverUtil
{

	/**
	 * This method will locate the element on webpage with its Class Name. Here
	 * locator is class name of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByClassName(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByClassName"));
			throw new WebDriverUtilException("Incorrect ClassName or Webdriver");
		}
		WebElement webElement = driver.findElement(By.className(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its ID . Here locator
	 * is ID of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByID(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByID"));
			throw new WebDriverUtilException("Incorrect Id or Webdriver");
		}
		WebElement webElement = driver.findElement(By.id(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its CSS. Here locator
	 * is CSS of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByCssSelector(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByCssSelector"));
			throw new WebDriverUtilException("Incorrect CssSelector or Webdriver");
		}
		WebElement webElement = driver.findElement(By.cssSelector(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its Link text. Here
	 * locator is Link text of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */

	public static WebElement findElementByLinkText(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByLinkText"));
			throw new WebDriverUtilException("Incorrect LinkText or Webdriver");
		}
		WebElement webElement = driver.findElement(By.linkText(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its Name. Here
	 * locator is name of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByName(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByName"));
			throw new WebDriverUtilException("Incorrect Name or Webdriver");
		}
		WebElement webElement = driver.findElement(By.name(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its XPATH. Here
	 * locator is XPATH of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByXpath(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByXpath"));
			throw new WebDriverUtilException("Incorrect Xpath or Webdriver");
		}
		WebElement webElement = driver.findElement(By.xpath(locator));
		return webElement;

	}

	/**
	 * This method will locate the element on webpage with its Tag Name. Here
	 * locator is tag name of webelement.it returns the list of element
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static List<WebElement> findElementsByTagName(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementsByTagName"));
			throw new WebDriverUtilException("Incorrect TagName or Webdriver");
		}
		List<WebElement> webElements = driver.findElements(By.tagName(locator));
		return webElements;

	}

	/**
	 * This method will locate the element on webpage with its Tag Name. Here
	 * locator is tag name of webelement.
	 * 
	 * @param locator
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static WebElement findElementByTagName(String locator, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null || (locator == null || locator == ""))
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver or Locator is NULL/EMPTY in method :findElementByTagName"));
			throw new WebDriverUtilException("Incorrect TagName or Webdriver");
		}
		WebElement webElement = driver.findElement(By.tagName(locator));
		return webElement;

	}

	/**
	 * This method will perform the click operation on any clickable webelement.
	 * 
	 * @param webElement
	 * @throws WebDriverUtilException
	 */
	public static void click(WebElement webElement) throws WebDriverUtilException
	{
		if (webElement == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "webElement is NULL/EMPTY in method :click"));
			throw new WebDriverUtilException("Incorrect webElement to click");
		}
		webElement.click();
	}

	/**
	 * This method will clear the textbox area of the webelement passed as
	 * parameter.
	 * 
	 * @param webElement
	 * @throws WebDriverUtilException
	 */
	public static void clear(WebElement webElement) throws WebDriverUtilException
	{
		if (webElement == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "webElement is NULL/EMPTY in method :clear"));
			throw new WebDriverUtilException("Incorrect webElement to clear");
		}
		webElement.clear();
	}

	/**
	 * This method will type the msg provided as parameter on the textbox area
	 * of passed webElement.
	 * 
	 * @param webElement
	 * @param msg
	 * @throws WebDriverUtilException
	 */
	public static void sendKeys(WebElement webElement, String msg) throws WebDriverUtilException
	{
		if (webElement == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "WebElement is NULL/EMPTY in method :sendKeys"));
			throw new WebDriverUtilException("Incorrect webElement for sendkeys");
		}
		webElement.sendKeys(msg);
	}

	/**
	 * This method will click on the cancel button of any popup alert message
	 * 
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void dismissAlert(WebDriver driver) throws WebDriverUtilException
	{
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	/**
	 * This method will click on the accept button of any popup alert message
	 * 
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void acceptAlert(WebDriver driver) throws WebDriverUtilException
	{
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * This method is used by webdriver for pressing the back button on browser.
	 * 
	 * @param driver
	 */
	public static void pressBackButton(WebDriver driver)
	{
		driver.navigate().back();
	}

	/**
	 * This method will read the content of alert box and return the message to
	 * calling method.
	 * 
	 * @param driver
	 * @return
	 * @throws WebDriverUtilException
	 */
	public static String getAlertContents(WebDriver driver) throws WebDriverUtilException
	{
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	/**
	 * This method will refresh the driver.
	 * 
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void refreshPage(WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :refreshPage"));
			throw new WebDriverUtilException("Driver not properly initialized to Refresh page");
		}
		driver.navigate().refresh();
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its ClassName
	 * 
	 * @param ClassName
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementByClassName(String name, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :WaitForElementByClassName"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (name == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "ClassName is NULL/EMPTY in method :WaitForElementByClassName"));
			throw new WebDriverUtilException("Incorrect name to wait");
		}
		By locator = By.className(name);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its Id
	 * 
	 * @param id
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementById(String id, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementById"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (id == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "ID is NULL/EMPTY in method :waitForElementById"));
			throw new WebDriverUtilException("Incorrect id to wait");
		}
		By locator = By.id(id);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its CSS.
	 * 
	 * @param css
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */

	public static void waitForElementByCssSelector(String css, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByCssSelector"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (css == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "CSS value is NULL/EMPTY in method :waitForElementByCssSelector"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.cssSelector(css);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its Linktext
	 * 
	 * @param linkText
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementByLinkText(String linkText, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByLinkText"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (linkText == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "LinkText value is NULL/EMPTY in method :waitForElementByLinkText"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.linkText(linkText);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its Name
	 * 
	 * @param name
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementByName(String name, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByName"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (name == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Name value is NULL/EMPTY in method :waitForElementByName"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.name(name);
		WebDriverWait wait = new WebDriverWait(driver, second);

		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its XPATH
	 * 
	 * @param xpath
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */

	public static void waitForElementByXpath(String xpath, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByXpath"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (xpath == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "XPATH is NULL/EMPTY in method :waitForElementByXpath"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.xpath(xpath);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its TagName
	 * 
	 * @param tagName
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementByTagName(String tagName, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByTagName"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (tagName == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "tagName is NULL/EMPTY in method :waitForElementByTagName"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.tagName(tagName);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * This method takes number of second to wait for a webelement to appear on
	 * Webpage with its PartialLink
	 * 
	 * @param partialLink
	 * @param second
	 * @param driver
	 * @throws WebDriverUtilException
	 */
	public static void waitForElementByPartialLink(String partialLink, int second, WebDriver driver) throws WebDriverUtilException
	{
		if (driver == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "Driver is NULL/EMPTY in method :waitForElementByPartialLink"));
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		if (partialLink == null)
		{
			TestLogger.log(new ErrorEvent(WebDriver.class, "PartialLink is NULL/EMPTY in method :waitForElementByPartialLink"));
			throw new WebDriverUtilException("Incorrect css to wait");
		}
		By locator = By.partialLinkText(partialLink);
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * This method will wait for alert box for number of seconds passed as
	 * parameter.
	 * 
	 * @param driver
	 * @param seconds
	 * @return
	 */
	public static boolean waitForAlert(WebDriver driver, int seconds)
	{
		boolean isAlertFound = false;
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		if (wait.until(ExpectedConditions.alertIsPresent()) != null)
		{
			isAlertFound = true;
		}
		return isAlertFound;
	}

	/**
	 * This method will wait for Javascript to be executed for number of seconds
	 * passed as parameter.
	 * 
	 * @param driver
	 * @throws WebDriverUtilException
	 */

	public static void waitForJavaScript(WebDriver driver) throws WebDriverUtilException
	{
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver driver)
			{
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try
		{
			Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			Thread.sleep(2000);
			wait.until(pageLoadCondition);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * This method maximized the window size.
	 * 
	 * @param driver
	 */

	public static void windowMaximizeAndFocus(WebDriver driver)
	{
		driver.manage().window().maximize();
	}

	/**
	 * This method take Id of dropdown box and index number of element to be
	 * selected.
	 * 
	 * @param index
	 * @param driver
	 * @param id
	 * @throws WebDriverUtilException
	 */
	public static void SelectfromDropDown(int index, WebDriver driver, String id) throws WebDriverUtilException
	{
		if (driver == null)
		{
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		Select dropDown = new Select(findElementByID(id, driver));
		dropDown.selectByIndex(index);
	}

	/**
	 * This method take Id of dropdown box and text values of element to be
	 * selected.
	 * 
	 * @param optionText
	 * @param driver
	 * @param id
	 * @throws WebDriverUtilException
	 */

	public static void SelectfromDropDownVisibleText(String optionText, WebDriver driver, String id) throws WebDriverUtilException
	{
		if (driver == null)
		{
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		Select dropDown = new Select(findElementByID(id, driver));
		dropDown.selectByVisibleText(optionText);
	}

	/**
	 * This method check the checkbox for webelement for which Id is passed.
	 * 
	 * @param driver
	 * @param locator
	 * @throws WebDriverUtilException
	 */
	public static void setCheckBox(WebDriver driver, String locator) throws WebDriverUtilException
	{
		driver.findElement(By.id(locator)).click();
	}

	/**
	 * This method gives the keyboard command to select whole webpage i.e. Ctrl
	 * + A
	 * 
	 * @param driver
	 */
	public static void selectAll(WebDriver driver)
	{
		driver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
	}

	/**
	 * This method gives the keyboard command to select area i.e. Ctrl + A
	 * 
	 * @param driver
	 */
	public static String select()
	{
		String select = Keys.chord(Keys.CONTROL, "a");
		return select;
	}

	/**
	 * This method gives the keyboard command to driver to copy text i.e. Ctrl +
	 * C
	 * 
	 * @return
	 */
	public static String copy()
	{
		String copy = Keys.chord(Keys.CONTROL, "c");
		return copy;
	}

	/**
	 * This method gives the keyboard command to driver to paste whole text i.e.
	 * Ctrl + V
	 * 
	 * @return
	 */
	public static String paste()
	{
		String paste = Keys.chord(Keys.CONTROL, "v");
		return paste;
	}

	/**
	 * This method gives the keyboard command to driver to press backspace
	 * 
	 * @param ele
	 * @param num
	 */
	public static void backspace(WebElement ele, int num)
	{
		for (int i = 0; i < num; i++)
		{
			ele.sendKeys(Keys.BACK_SPACE);
		}
	}

	/**
	 * This method will toggle the driver handle from main window to popup
	 * window.
	 * 
	 * @param driver
	 */
	public static void handlePopupWindow(WebDriver driver)
	{
		(new WebDriverWait(driver, 90)).until(new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver d)
			{
				return d.getWindowHandles().size() > 1;
			}
		});

		for (String winHandle : driver.getWindowHandles())
		{
			driver.switchTo().window(winHandle);
		}

	}

	/**
	 * This method returns the current window handle of the driver.
	 * 
	 * @param driver
	 * @return
	 */

	public static String getCurrentdriverHandler(WebDriver driver)
	{
		return (driver.getWindowHandle());
	}

	/**
	 * This method sorts the input list in ascending order and returns the
	 * sorted List
	 * 
	 * @param list
	 * @return list
	 */
	public static List<String> sortList(List<String> list)
	{
		Collections.sort(list);
		return list;
	}
	
	
	/**
	 * Selects values from drop down using index, found on basis of Xpath
	 * @param index
	 * @param driver
	 * @param Xpath
	 * @throws WebDriverUtilException
	 */
	public static void SelectfromDropDownByXpath(int index, WebDriver driver, String Xpath)
			throws WebDriverUtilException
	{
		if (driver == null)
		{
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		Select dropDown = new Select(findElementByXpath(Xpath, driver));
		dropDown.selectByIndex(index);
	}
	
	/**
	 * Selects from drop down by visible text found on basis of Xpath
	 * @param optionText
	 * @param driver
	 * @param Xpath
	 * @throws WebDriverUtilException
	 */
	public static void SelectfromDropDownVisibleTextByXpath(String optionText,
			WebDriver driver, String Xpath) throws WebDriverUtilException
	{
		if (driver == null)
		{
			throw new WebDriverUtilException("Driver not properly initialized");
		}
		Select dropDown = new Select(findElementByID(Xpath, driver));
		dropDown.selectByVisibleText(optionText);
	}

	/**
	 * This method always generate and return a unique number to the calling
	 * method. It generate a new number from current date. It appends Date,
	 * Month, year, Hour, minutes and seconds.
	 * 
	 * @return
	 */
	public static String generateUUID()
	{
		DateFormat dateFormat = new SimpleDateFormat("MddyyyyHHmmss");
		Date today = Calendar.getInstance().getTime();
		String UUID = dateFormat.format(today);
		return UUID;

	}
}
