package com.automation.SeleniumJavaFramework;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DropDownAutomate {
  
	WebDriver driver;
	  
	  @BeforeMethod
	  public void beforeMethod() {
		  //New update
		// Set up ChromeDriver
	      System.setProperty("webdriver.chrome.driver", "Driver/" + "chromedriver.exe");
	      driver = new ChromeDriver();  
		  
	  }
	  
	   public void openBrowser() {
		// Navigate to Practice page
		   driver.get("https://www.letskodeit.com/practice");
	       driver.manage().window().maximize();
	       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }
	  
	  @Test
	  public void handleDropDown() {
		  
		  try {
	      	openBrowser();
	      	
	          // Locate Element
	          WebElement element =
	        		  driver.findElement(By.id("carselect"));
	          
	          Select dropdown = new Select(element);
	          
	          List<WebElement> options = dropdown.getOptions();
	        		  
	        
	          // Select Value
	          waitTill(3);
	          dropdown.selectByIndex(0);
	          waitTill(3);
	          dropdown.selectByValue("benz");
	          waitTill(3);
	          dropdown.selectByVisibleText("Honda");
	          waitTill(3);
	          
	          for (int i=0; i< options.size();i++) {
	        	  System.out.println("Option "+ (i+1) + " with value : "+ options.get(i).getText());
	          }
	          
	          for (WebElement option : options) {
	        	  System.out.println("Using for-each loop : " + option.getText());
	          }
	          
	          // Assert successful
	          Assert.assertTrue(true);
	          
	          System.out.println("Test Dropdown Passed.");
	          
	      } catch (Exception e) {
	          System.out.println("Test Failed: " + e.getMessage());
	          Assert.fail("Test Dropdown failed");
	      }
	  }
	 
	  public void waitTill(int time) {
			try {
				Thread.sleep(time * 1000);
			} catch (InterruptedException e) {
				System.out.println("waitTill Failed: " + e.getMessage());
			}
		}
	  
	  @AfterMethod
	  public void afterMethod() {
	      if (driver != null) {
	          try {
	              driver.quit();
	          } catch (UnreachableBrowserException e) {
	              System.out.println("Browser already closed or unreachable: " + e.getMessage());
	          }
	      }
	  }

}
