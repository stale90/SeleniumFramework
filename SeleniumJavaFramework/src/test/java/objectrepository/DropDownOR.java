package objectrepository;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import reusablecomponents.TechnicalComponents;

public class DropDownOR {
	WebDriver driver;
	
	@FindBy(id = "carselect")
	public static WebElement drop_Down;

	public DropDownOR(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void insideDropDown() {
		System.out.println("Inside OR : " + this.getClass() );
	}
		
	public void verifyDropdownSelection() {
		Select select = new Select(drop_Down);
		TechnicalComponents.selectValuefromDropdown(select, "index", "2","Car Dropdown");
		TechnicalComponents.wait(2);
		TechnicalComponents.selectValuefromDropdown(select, "value", "benz", "Car Dropdown");
		TechnicalComponents.wait(2);
		TechnicalComponents.selectValuefromDropdown(select, "visibletext", "BMW", "Car Dropdown");
		TechnicalComponents.wait(2); 
	}

	public void printDropdownOptions() {
		Select select = new Select(drop_Down);
		List<WebElement> options = select.getOptions();
		TechnicalComponents.printListOfValues(options, "Dropdown Element List");
		for (WebElement option : options) {
			System.out.println("Using for-each loop : " + option.getText());
		}
	}
}
