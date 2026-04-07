package reusablecomponents;

import objectrepository.DropDownOR;

public class BusinessComponents extends TechnicalComponents{

	public void verifyDropdown() {
		
		DropDownOR d = new DropDownOR(driver);
		d.verifyDropdownSelection();
		d.printDropdownOptions();

	}
	
}
