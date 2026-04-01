package data;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.DataProvider;
import reusablecomponents.Utilities;

/**
 * This class is designed to return test data for each test, wherever required.
 * 
 * @author Shailendra
 */

public class TestData {

	@DataProvider(name = "testWebUrls")
	public static Object[][] testWebUrls() throws FileNotFoundException, IOException {
		return Utilities.Read_Excel(Utilities.getProperty("TEST_DATA_LOCATION"), "testWebUrls");
	}
}
