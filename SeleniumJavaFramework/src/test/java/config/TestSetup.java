package config;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class TestSetup {

	public static WebDriver driver;
	String testName;
	
	/**
	 * Before Suite method........
	 */
	@BeforeSuite
	public void beforeSuite() {
		Report.initialiseReporters();
	}

	 @BeforeMethod
	  public void beforeMethod(Method method) {
		// Set up ChromeDriver
		  testName = method.getName();
		  Report.startReporters(testName);
		  getBrowserDriver();
	      
		  
	  }
	
	@AfterMethod
	  public void afterMethod() {
		Report.flushReporters();
	    driver.quit();
	}
	
	public void getBrowserDriver() {
		System.setProperty("webdriver.chrome.driver", "Driver/" + "chromedriver.exe");
	    driver = new ChromeDriver(); 
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
}
