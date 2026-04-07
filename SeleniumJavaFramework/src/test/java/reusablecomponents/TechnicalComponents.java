package reusablecomponents;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import config.FrameworkException;
import config.Report;
import config.TestSetup;

public class TechnicalComponents extends TestSetup {

	public static void navigatetoUrl(String url) {
		try {
			if (url != "") {
				driver.get(url);
				Report.info("Info", driver);
				driver.manage().window().maximize();
				
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Unable to navigate to URL--- " + url + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	public static String getCurrentURL() {
		try {
			return driver.getCurrentUrl();

		} catch (Exception e) {
			throw new FrameworkException(
					"Unable to get current url---" + e.getClass() + "---" + e.getMessage());
		}

	}

	public static void wait(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			throw new FrameworkException(
					"Unable to apply static wait---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
		
	/**
	 * Function to wait for any element to be visible, invisible or enable.
	 * 
	 * @param element
	 *            - Element to be looked for.
	 * @param state
	 *            - Expected state of Element. Expected values: "visible", "enable",
	 *            "invisible"
	 * @throws FrameworkException
	 *             - in case of error.
	 */
	public static void waitTill(WebElement element, String state) {
		try {
			switch (state.toLowerCase()) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "enable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case "invisible":
				wait.until(ExpectedConditions.invisibilityOf(element));
				break;
			default:
				wait.until(ExpectedConditions.visibilityOf(element));
			}

			Report.log("Element " + element.toString() + " " + state);

		} catch (StaleElementReferenceException e) {
			if (timeOut > 0) {
				timeOut--;
				waitTill(element, state);
			} else {
				throw new FrameworkException(
						"Page refreshed while waiting for element : *  '" + element.toString() + "'");
			}
		} catch (TimeoutException e) {
			throw new FrameworkException(
					"Element : *  '" + element.toString() + "' not found within defined time limit.");
		} catch (NoSuchElementException e) {
			throw new FrameworkException("Element : *  '" + element.toString() + "' not found in DOM.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while waiting for element: *  '"
					+ element.toString() + "'---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Get the attribute value of particular element.
	 * 
	 * @param element
	 *            - Element for which attribute value needs to be retrieved.
	 * @param attribute
	 *            - Attribute to be retrieved.
	 * @param desc
	 *            - Free text to identify field.
	 * @return - Returns attribute value for the element. Returns "" in case value
	 *         is null.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static String getAttribute(WebElement element, String attribute, String desc) {
		try {
			String value;
			if (attribute.equals("text")) {
				if (element.getText() != null) {
					value = element.getText();
				} else {
					value = "";
				}
			} else {
				if (element.getAttribute(attribute) != null) {
					value = element.getAttribute(attribute);
				} else {
					value = "";
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			Report.log("Attribute " + attribute + " for " + desc + " returned " + value);
			return value;
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				return getAttribute(element, attribute, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while retrieving Attribute: "
					+ attribute.toUpperCase() + " value for " + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	public static void selectValuefromDropdown(Select select, String selectBy, String value, String desc) {
		try {
			if (selectBy.toLowerCase().contains("value")) {
				select.selectByValue(value);
				Report.info("DropDown value selected by value : "+value, driver);
			}else if(selectBy.toLowerCase().contains("visibletext")){
				select.selectByVisibleText(value);
				Report.info("DropDown value selected by VisibleText : "+value , driver);
			} else if (selectBy.toLowerCase().contains("index")) {
				select.selectByIndex(Integer.valueOf(value));
				WebElement selectedOption = select.getFirstSelectedOption();
				String selectedText = selectedOption.getText();
				Report.info("DropDown value selected by Index : "+Integer.valueOf(value)+" with value : "+selectedText  , driver);
			} else {
				throw new FrameworkException(selectBy + " is incorrect parameter. Please contact Automation Team.");
			}
		} catch (Exception e) {
			if (e instanceof FrameworkException) {
				throw e;
			} else {
				throw new FrameworkException("Unknown exception while selecting value from dropdown---" + e.getClass()
				+ "---" + e.getMessage());
			}
		}
	}

	public static void printListOfValues(List<WebElement> options, String desc) {
		try {
			for (int i = 0; i < options.size(); i++) {
				Report.info("List Option With index " + i + " and value : " 
						+ options.get(i).getText());	
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception while printing values of Element List---" + e.getClass()
			+ "---" + e.getMessage());
		}
	}

}
