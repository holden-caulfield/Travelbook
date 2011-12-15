package ar.com.travelbook.wizard.action.activity.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.com.travelbook.domain.Activity;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.Travel;

/**
 * Gets printable calendar activities
 * 
 * @author gabriel
 *
 */
@Name("activitiesCalendar")
@Scope(ScopeType.CONVERSATION)
public class CalendarActivities implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@In(create=true) Date selectedWeek;
	@In private Travel travel;
	@In Integer selectedItem;
	@Logger
	private static Log log;
	
	private List<Date> days;
	private List<CalendarActivity> activitiesCalendar;

	public CalendarActivities() {
	}
	
	public void setActivitiesCalendar(List<CalendarActivity> actividadesCalendar) {
		this.activitiesCalendar = actividadesCalendar;
	}

	public List<CalendarActivity> getActivitiesCalendar() {
		if(activitiesCalendar==null)
			recalculateLists();
		return activitiesCalendar;
	}

	public void setDays(List<Date> dias) {
		this.days = dias;
	}

	public List<Date> getDays() {
		if(days==null)
			recalculateLists();
		return days;
	}

	/**
	 * Recalculates the list of items to be printed and the list of days
	 */
	@Observer("searchActividadesCalendar")
	public void recalculateLists(){
		List<CalendarActivity> activities = this.getActivitiesSplittedByDay(travel.getDestinations().get(selectedItem));
		this.activitiesCalendar = this.filterActivitiesByWeek(activities, selectedWeek);
		
		this.days = new ArrayList<Date>();
		DateTime day = new DateTime(selectedWeek);
		for (int i = 0; i < 7; i++) {
			this.days.add(day.toDate());
			day = day.plusDays(1);
		}
	}
	
	/**
	 * Filters and displays the activities by day
	 * If an activity is longer than a day, it returns several activities.
	 * 
	 * @param destination
	 * 
	 * @return
	 */
	private List<CalendarActivity> getActivitiesSplittedByDay(Destination destination) {
		List<CalendarActivity> weekActivities = new ArrayList<CalendarActivity>();
		for (Activity activity : destination.getActivities()) {
			DateTime start = new DateTime(activity.getStartDateTime());
			DateTime end = new DateTime(activity.getEndDateTime());
			
			if(!start.isBefore(end))  {
				// domain error
				throw new RuntimeException("Domain activity error - Invalid dates - End date before start date");
			}
			CalendarActivity calendarActivity = new CalendarActivity(activity.getName(), activity.getStartDateTime(), activity.getEndDateTime(), activity.getPlace());
			end=end.minusSeconds(1);
			if(start.getDayOfYear() == end.getDayOfYear()) {
				weekActivities.add(calendarActivity);
			}
			else {
				CalendarActivity tempActivity;
				
				for(DateTime startTmp = new DateTime(start); 
					startTmp.isBefore(end.minusDays(1));
					startTmp = startTmp.withTime(0, 0, 0, 0).plusDays(1)) {
					DateTime endTmp = new DateTime(startTmp).withTime(0, 0, 0, 0).plusDays(1).minusSeconds(1);
					
					tempActivity = new CalendarActivity(activity.getName(),
							startTmp.toDate(), endTmp.toDate(),activity.getPlace());
					
					weekActivities.add(tempActivity);
				}
				
				tempActivity = new CalendarActivity(activity.getName(),end.withTime(0, 0, 0, 0).toDate(),end.toDate(),activity.getPlace());
				weekActivities.add(tempActivity);
			}
		}
		return weekActivities;
	}
	/**
	 * Filters activities if they should be printed in this week
	 * 
	 * @param activities List of all activities
	 * @param day First day of the week
	 * 
	 * @return List<CalendarActivity>
	 */
	private List<CalendarActivity> filterActivitiesByWeek(List<CalendarActivity> activities, Date day){
		DateMidnight firstDay = new DateMidnight(day);
		DateMidnight lastDay = firstDay.plusDays(7);
		
		Interval week = new Interval(firstDay, lastDay);

		List<CalendarActivity> filteredActivities = new ArrayList<CalendarActivity>();
		
		for (CalendarActivity activity : activities) {
			if (week.contains(new DateTime(activity.getStart()))){
				log.debug("comparing "+week.toString()+" with "+activity.getStart().toString());
				filteredActivities.add(activity);
			}
		}
		return filteredActivities;
	}
	
}
