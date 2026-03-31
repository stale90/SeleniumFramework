package com.automation.SeleniumJavaFramework;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import config.Report;
import reusablecomponents.BusinessComponents;
import reusablecomponents.Utilities;

public class DropDown extends BusinessComponents {

	@Test
	public void handleDropDown() {
		String url = Utilities.getProperty("TEST_ENVIRONMENT_URL");
		String testDesc = "handleDropDown";
		try {
			navigatetoUrl(url);
			verifyDropdown();

			// Assert successful
			Report.pass(testDesc + " : Passed", driver);
			assertTrue(true, testDesc + " : Passed");
			System.out.println("Test Case: handleDropDown Passed.");

		} catch (Exception e) {
			Report.fail(testDesc + " Failed.", driver);
			assertTrue(false, testDesc + ": Failed");
			System.out.println("Unknow exception encountered in Test : handleDropDown-- " 
			                    + e.getClass() + "---" + e.getMessage());
		}
	}
}

