package com.automation.SeleniumJavaFramework;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import reusablecomponents.BusinessComponents;


public class DropDown extends BusinessComponents {

	@Test
	public void handleDropDown() {
		String url = "https://www.letskodeit.com/practice";
		try {
			navigatetoUrl(url);
			verifyDropdown();

			// Assert successful
			assertTrue(true, "Test Case: handleDropDown Passed.");
			System.out.println("Test Case: handleDropDown Passed.");

		} catch (Exception e) {
			assertTrue(false, "Test Case: handleDropDown Failed.");
			System.out.println("Unknow exception encountered in Test : handleDropDown-- " 
			                    + e.getClass() + "---" + e.getMessage());
		}
	}
}

