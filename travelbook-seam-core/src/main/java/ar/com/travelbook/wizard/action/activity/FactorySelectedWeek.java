package ar.com.travelbook.wizard.action.activity;

import java.util.Date;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import ar.com.travelbook.domain.Travel;

/**
 * Setups the selected week to print in the calendar
 * 
 * @author cruz
 *
 */
@Name("factorySelectedWeek")
public class FactorySelectedWeek {
	@In private Travel travel;
	@In Integer selectedItem;

	@Factory
	public Date getSelectedWeek(){
		return travel.getDestinations().get(selectedItem).getDepartureDateTime();
	}
}
