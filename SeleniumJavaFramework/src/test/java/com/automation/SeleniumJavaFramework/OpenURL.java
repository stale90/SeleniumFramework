package com.automation.SeleniumJavaFramework;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import config.Report;
import reusablecomponents.BusinessComponents;

public class OpenURL extends BusinessComponents {

	@Test(dataProvider = "testWebUrls", dataProviderClass = data.TestData.class)
	public void testBrowserOpenURL(String testDesc, String complexity, String url) {
		
		setParametersPerTestCase(testDesc,complexity);
		try {
			navigatetoUrl(url);

			// Assert successful
			Report.pass(testDesc + " : Passed", driver);
			assertTrue(true, testDesc + " : Passed");

		} catch (Exception e) {
			Report.fail(testDesc + " Failed.", driver);
			assertTrue(false, testDesc + ": Failed");
			System.out.println("Unknow exception encountered in Test : "+ testName + "--" 
			                    + e.getClass() + "---" + e.getMessage());
		}
	}
}

