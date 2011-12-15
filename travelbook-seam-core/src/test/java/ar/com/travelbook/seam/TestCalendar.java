package ar.com.travelbook.seam;
import java.util.List;

import org.jboss.seam.mock.SeamTest;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import ar.com.travelbook.domain.ActivityTicketPrivate;
import ar.com.travelbook.domain.Place;
import ar.com.travelbook.domain.TransportTicketPrivate;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.action.activity.calendar.CalendarActivity;
import ar.com.travelbook.wizard.action.itinerary.PlaceFilter;

/**
 * Tests calendar logic
 * 
 * @author cruz
 *
 */
@SuppressWarnings({ "unchecked" })
public class TestCalendar extends SeamTest{
	

	/**
	 * Creates in travel the transports and destinations
	 * 
	 * @param travel
	 * @param places
	 */
	private void fillTravelWithTransport(Travel travel, List<Place> places){
		travel.addDestination(places.get(0));
		travel.addDestination(places.get(1));
		TransportTicketPrivate transport = new TransportTicketPrivate("",new DateTime(2009,2,1,6,0,0,0).toDate(),
				new DateTime(2009,2,1,7,0,0,0).toDate(),0,null,null,null);
		travel.getDestinations().get(1).setTransportTicket(transport);
		
	}
	
	/**
	 * Tests calendar splitting several activities that start and end same day
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCalendarSplitter() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placeFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placeFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				
				TestCalendar.this.fillTravelWithTransport(travel, places);
				
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate privateActivity = new ActivityTicketPrivate("bailar tango", new DateTime(2009,2,3, 0, 0, 0, 0).toDate(),
						new DateTime(2009,2,4,1,0, 0, 0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				privateActivity = new ActivityTicketPrivate("bailar tango 2", 
						new DateTime(2009,2,3, 0, 0, 0, 0).toDate(),new DateTime(2009,2,4, 0, 0, 0, 0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				privateActivity = new ActivityTicketPrivate("bailar tango 3", 
						new DateTime(2009,2,5, 0, 0, 0, 0).toDate(),new DateTime(2009,2,6, 0, 0, 0, 0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				privateActivity = new ActivityTicketPrivate("bailar tango 4", 
						new DateTime(2009,2,7, 0, 0, 0, 0).toDate(),new DateTime(2009,2,8, 0, 0, 0, 0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				privateActivity = new ActivityTicketPrivate("bailar tango 5", 
						new DateTime(2009,2,8,1,1,0, 0).toDate(),new DateTime(2009,2,8,1,2,0, 0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
							getValue("#{activitiesCalendar.activitiesCalendar}");
				Assert.assertEquals(calendarActivities.size(), 5);
			}
		}.run();
		
	}
	
	
	/**
	 * Tests calendar splitting with an activity that starts one month and ends another
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCalendarSplitterAcrossMonth() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placeFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placeFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				travel.addDestination(places.get(0));
				travel.addDestination(places.get(1));
				TransportTicketPrivate transport = new TransportTicketPrivate("",new DateTime(2009,1,28,6,0,0,0).toDate(),
						new DateTime(2009,1,28,7,0,0,0).toDate(),0,null,null,null);
				travel.getDestinations().get(1).setTransportTicket(transport);

				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate activityPersonal = new ActivityTicketPrivate("bailar tango", 
						new DateTime(2009,1,31, 0, 0, 0, 0).toDate(),
						new DateTime(2009,2,2, 4, 0, 0, 0).toDate(),
						places.get(1));
				travel.getDestinations().get(1).addActivity(activityPersonal);
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
					getValue("#{activitiesCalendar.activitiesCalendar}");
				Assert.assertEquals(calendarActivities.size(), 3);
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getStart()),
						new DateTime(2009,1,31,0,0,0,0));
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getEnd()),
						new DateTime(2009,1,31,23,59,59,0));
				Assert.assertEquals(new DateTime(calendarActivities.get(1).getStart()),
						new DateTime(2009,2,1,0,0,0,0));
				Assert.assertEquals(new DateTime(calendarActivities.get(1).getEnd()),
						new DateTime(2009,2,1,23,59,59,0));
				Assert.assertEquals(new DateTime(calendarActivities.get(2).getStart()),
						new DateTime(2009,2,2,0,0,0,0));
				Assert.assertEquals(new DateTime(calendarActivities.get(2).getEnd()),
						new DateTime(2009,2,2,3,59,59,0));
			}
		}.run();
		
	}
	
	/**
	 * Tests one activity that shouldn't be split
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCalendarSplitterSimple() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placeFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placeFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				TestCalendar.this.fillTravelWithTransport(travel, places);
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate privateActivity = new ActivityTicketPrivate("bailar tango", 
							new DateTime(2009,2,1,3,0,0,0).toDate(),	
							new DateTime(2009,2,1,6,0,0,0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
					getValue("#{activitiesCalendar.activitiesCalendar}");
				Assert.assertEquals(calendarActivities.size(), 1);
				DateTime start = new DateTime(calendarActivities.get(0).getStart());
				DateTime end = new DateTime(calendarActivities.get(0).getEnd());
				Assert.assertEquals(new DateTime(2009,2,1,3,0,0,0), start);
				Assert.assertEquals(new DateTime(2009,2,1,6,0,0,0), end);
			}
		}.run();
		
	}
	
	/**
	 * Tests an activity that should be split in two days
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCalendarSplitterSimpleTwoDays() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placeFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placeFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				TestCalendar.this.fillTravelWithTransport(travel, places);
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate privateActivity = new ActivityTicketPrivate("bailar tango", 
						new DateTime(2009,2,1,3,0,0,0).toDate(),	
						new DateTime(2009,2,2,6,0,0,0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(privateActivity);
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
					getValue("#{activitiesCalendar.activitiesCalendar}");
				Assert.assertEquals(calendarActivities.size(), 2);
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getStart()),
						new DateTime(2009,2,1,3,0,0,0)
				);
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getEnd()),
						new DateTime(2009,2,1,23,59,59, 0)
				);
				Assert.assertEquals(new DateTime(calendarActivities.get(1).getStart()),
						new DateTime(2009,2,2,0,0,0,0)
				);
				Assert.assertEquals(new DateTime(calendarActivities.get(1).getEnd()),
						new DateTime(2009,2,2,5,59,59, 0)
				);
				
			}
		}.run();
		
	}
	
	@Test
	public void testCalendarSplitterSimpleFullDay() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placeFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placeFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				TestCalendar.this.fillTravelWithTransport(travel, places);
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate activityPrivate = new ActivityTicketPrivate("bailar tango", 
						new DateTime(2009,2,1,0,0,0,0).toDate(),	
						new DateTime(2009,2,2,0,0,0,0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(activityPrivate);
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
					getValue("#{activitiesCalendar.activitiesCalendar}");
				Assert.assertEquals(calendarActivities.size(), 1);
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getStart()),
						new DateTime(2009,2,1,0,0,0,0)
				);
				Assert.assertEquals(new DateTime(calendarActivities.get(0).getEnd()),
						new DateTime(2009,2,2,0,0,0, 0)
				);
				
			}
		}.run();
		
	}	

	/**
	 * Tests invalid case of start date that is after end date
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Test(expectedExceptions=RuntimeException.class)
	public void testCalendarSplitterInvalidDates() throws Exception{
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel travel = (Travel)getValue("#{travel}");
				PlaceFilter placesFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert placesFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				TestCalendar.this.fillTravelWithTransport(travel, places);
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				
				assert getValue("#{selectedItem}") != null;
				ActivityTicketPrivate activityPrivate = new ActivityTicketPrivate("bailar tango", 
							new DateTime(2009,2,4,3,0,0,0).toDate(),	
							new DateTime(2009,2,1,6,0,0,0).toDate(), places.get(1));
				travel.getDestinations().get(1).addActivity(activityPrivate);
				
				List<CalendarActivity> calendarActivities = (List<CalendarActivity>)
					getValue("#{activitiesCalendar.activitiesCalendar}");			
				}
		}.run();
		
	}	

}
