package config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestSetup {

	public static WebDriver driver;
	
	 @BeforeMethod
	  public void beforeMethod() {
		// Set up ChromeDriver
	      System.setProperty("webdriver.chrome.driver", "Driver/" + "chromedriver.exe");
	      driver = new ChromeDriver(); 
	      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		  
	  }
	
	@AfterMethod
	  public void afterMethod() {
	      driver.quit();
	}
	
	
	
	 
}
