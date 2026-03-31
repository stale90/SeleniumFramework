package reusablecomponents;

import objectrepository.DropDownOR;

public class BusinessComponents extends TechnicalComponents{

	public void verifyDropdown() {
		
		DropDownOR d = new DropDownOR(driver);
		d.insideDropDown();
		d.verifyDropdownSelection();
		d.printDropdownOptions();

	}
	
}
