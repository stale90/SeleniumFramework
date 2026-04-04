package objectrepository;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import reusablecomponents.TechnicalComponents;
import config.Report;

public class DropDownOR {
	
	WebDriver driver;
	
	@FindBy(id = "carselect")
	public static WebElement drop_Down;

	public DropDownOR(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void insideDropDown() {
		System.out.println("Inside Object Repository : " + this.getClass() );
	}
		
	public void verifyDropdownSelection() {
		
		TechnicalComponents.selectValuefromDropdown(drop_Down, "index", "2");
		Report.info("DropDown Index 2 Selection : Honda ", driver);
		TechnicalComponents.wait(3);
	
		TechnicalComponents.selectValuefromDropdown(drop_Down, "value", "benz");
		TechnicalComponents.wait(3);
		Report.info("DropDown Selection by Value : benz", driver);
		
		TechnicalComponents.selectValuefromDropdown(drop_Down, "visibletext", "BMW");
		Report.info("DropDown Selection by VisibleText : BMW", driver);
		TechnicalComponents.wait(3); 
	}

	public void printDropdownOptions() {
		Select dropdown = new Select(drop_Down);
		List<WebElement> options = dropdown.getOptions();
		for (int i = 0; i < options.size(); i++) {
			System.out.println("Option " + (i + 1) + " with value : " 
					+ options.get(i).getText());
		}
		for (WebElement option : options) {
			System.out.println("Using for-each loop : " + option.getText());
		}
	}
}
