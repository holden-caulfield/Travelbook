package ar.com.travelbook.wizard.action.activity.calendar;

import java.util.Date;

import ar.com.travelbook.domain.Place;

/**
 * Calendar activity. Maps for printing purposes inside 
 * calendar an Activity (split by day in some cases)
 * @author gabriel
 *
 */
public class CalendarActivity {
	
	private String name;
	private Date start;
	private Date end;
	private Place place;

	public CalendarActivity(String name, Date start, Date end, Place place) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.place = place;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	
}
