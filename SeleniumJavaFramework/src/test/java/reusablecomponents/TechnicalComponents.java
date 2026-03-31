package reusablecomponents;

import config.FrameworkException;
import config.TestSetup;

public class TechnicalComponents extends TestSetup {

	public static void navigatetoUrl(String url) {
		try {
			if (url != "") {
				driver.get(url);
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
  
}
