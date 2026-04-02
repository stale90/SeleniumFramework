package com.automation.SeleniumJavaFramework;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import config.Report;
import reusablecomponents.BusinessComponents;
import reusablecomponents.Utilities;

public class DropDown extends BusinessComponents {

	@Test
	public void testDropDownElement() {
		String testDesc = "Practice:51 -Verify Dropdown values and selection behaviour on practice page";
		String complexity = "medium";
		String url = Utilities.getProperty("TEST_ENVIRONMENT_URL");
		setParametersPerTestCase(testDesc,complexity);
		try {
			navigatetoUrl(url);
			verifyDropdown();
			
			Report.pass(testDesc + " : Passed", driver);
			assertTrue(true, testDesc + " : Passed");
		} catch (Exception e) {
			Report.fail(testDesc + " Failed.", driver);
			assertTrue(false, testDesc + ": Failed");
			System.out.println("Unknow exception encountered in Test : handleDropDown-- " 
			                    + e.getClass() + "---" + e.getMessage());
		}
	}
}

