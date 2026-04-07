package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import reusablecomponents.Utilities;

public class TestSetup {

	public static WebDriver driver;
	public static String testBrowser;
	public static long timeOut, driverWait;
	public static WebDriverWait wait;
	public static int testCaseCount = 0, testCaseExecuted = 0, testCasePassed = 0, testCaseFailed = 0,
			testCaseSkipped = 0;
	public static String testName;
	public static String testDataLocation , testCategory, browserName;
	public boolean toBeTested = false;
	public static String[][] testCases;
	public static HashMap<String, String> testCasesToBeExecuted = new HashMap<String, String>();
	public static HashMap<String, String> testCaseBrowser = new HashMap<String, String>();
	public static HashMap<String, String> testCaseCategory = new HashMap<String, String>();
	
	/*
	 * Before Suite method........
	 */
	
	@BeforeSuite
	public void beforeSuite() {
		Report.initialiseReporters();
		testDataLocation = Utilities.getProperty("TEST_DATA_LOCATION");
		
		testCases = Utilities.Read_Excel(testDataLocation, "TestCases");

		for (int i = 0; i < testCases.length; i++) {
			testCasesToBeExecuted.put(testCases[i][1], testCases[i][2]);
			testCaseBrowser.put(testCases[i][1], testCases[i][4]);
			testCaseCategory.put(testCases[i][1], testCases[i][3]);
		}
	}

	/*
	 * Before method....
	 */
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		testCaseCount++;
		testName = method.getName();
		driver = null;
		driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
		Report.startReporters(testName);
		try {
			if (testCasesToBeExecuted.get(testName).equalsIgnoreCase("Yes")) {
				toBeTested = true;
				testCategory = testCaseCategory.get(testName);
				browserName = testCaseBrowser.get(testName);
				getBrowserDriver(browserName);
			}
		} catch (NullPointerException e) {
			Report.skip(testName + " not configured. Please check data file and function name for consistency.");
		}
	}
	
	/*
	 * After method....
	 */
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		String testCaseStatus = null;
		int rowCounter = testCaseCount;
		Report.flushReporters();
		
		try {
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			testCasePassed++;
			testCaseStatus = "PASS";
			break;
		case ITestResult.FAILURE:
			testCaseFailed++;
			testCaseStatus = "FAIL";
			break;
		case ITestResult.SKIP:
			testCaseSkipped++;
			testCaseStatus = "SKIP";
			break;
		}
		testCaseExecuted = testCasePassed + testCaseFailed;
		Report.writeTestResultInExcel(rowCounter,testCaseStatus);
		driver.quit();
		}catch(Exception e) {
			Report.log("Unknown Exception occured in after Method execution. ");
			
		}
	}

	/*
	 * After Suite .....
	 */
	
	@AfterSuite
	public void afterSuite() throws FileNotFoundException, IOException {
		System.out.println("Test Cases Executed: " + testCaseExecuted);
		System.out.println("Test Cases Passed: " + testCasePassed);
		System.out.println("Test Cases Failed: " + testCaseFailed);
		System.out.println("Test Cases Skipped: " + testCaseSkipped);
	}
	
	
	/**
	 * Method to Open specific browser based on TEST_BROWSER property value set in 
	 * properties file
	 * 
	 * @param browser
	 */
	public void getBrowserDriver(String browser) {
		 browser = Utilities.getProperty("TEST_BROWSER");
		 
		try {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "Driver/" + "chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "Driver/" + "geckodriver.exe");
			driver = new FirefoxDriver();	
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "Driver/" + "msedgedriver.exe");
			driver = new EdgeDriver();	
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}catch (Exception e) {
			throw new FrameworkException(
					"Unknown exeception occurs in TestSetup.getBrowserDriver Method---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Sets the logger test description to the one from sheet and customize waits
	 * per test case complexity
	 * 
	 * @param testDesc   - Test Description to be entered.
	 * @param complexity - Complexity of test case i.e. high, medium or low. high
	 *                   will have maximum wait and will be reduced from high till
	 *                   low.
	 * @throws FrameworkException in case of error.
	 */

	public void setParametersPerTestCase(String testDesc, String complexity) {
		String browserVersion = "", browserName = "";
		
		try {
			 Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			 // Browser Name
			 browserName = String.format("Browser Name: %s", cap.getCapability("browserName"));
			 // Browser Version
				if (driver instanceof ChromeDriver) {
					browserVersion = (String) cap.getCapability("browserVersion");
				} else if (driver instanceof FirefoxDriver) {
					browserVersion = String.format("Browser Version: %s", cap.getVersion());
				} else if (driver instanceof EdgeDriver) {
					browserVersion = String.format("Browser Version: %s", cap.getVersion());
				} else if (driver instanceof InternetExplorerDriver) {
					browserVersion = String.format("Browser Version: %s", cap.getVersion());
				} else if (driver instanceof RemoteWebDriver) {
					browserVersion = "";
				}
			} catch (Exception e) {
				throw new FrameworkException(
						"Unknown exeception occurs in TestSetup.setParametersPerTestCase Method");
		}
			// Timeout Assign based on complexity of Test
		if (driver != null) {
			if (complexity.toLowerCase().equals("high")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT"));
			} else if (complexity.toLowerCase().equals("medium")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_MEDIUM"));
			} else if (complexity.toLowerCase().equals("low")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_LOW"));
			}
			wait = new WebDriverWait(driver, timeOut);
		}
		Report.setLoggersTestNameAndDesc(testDesc, complexity, browserName, browserVersion, testCaseCount);
	}

}
