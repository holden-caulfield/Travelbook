package ar.com.travelbook.helpersTest;

import java.util.Date;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

/**
 * Helper for setting simple properties in unit tests
 * 
 * @author cruz
 *
 */
@Name("testSimpleProperties")
@SuppressWarnings("unused")
public class TestSetSimpleProperties {

	@Out
	private int selectedItem;
	
	@Out(required=false)
	private Date selectedWeek;
	
	public void setSelectedItem(int value) {
		this.selectedItem = value;
	}

	public void setSelectedWeek(Date selectedWeek) {
		this.selectedWeek = selectedWeek;
	}
	
	
}
