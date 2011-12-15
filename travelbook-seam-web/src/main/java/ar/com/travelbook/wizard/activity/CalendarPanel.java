package ar.com.travelbook.wizard.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.annotations.In;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import ar.com.travelbook.wizard.action.activity.calendar.CalendarActivity;

public class CalendarPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public CalendarPanel(String id, IModel model) {
		super(id,model);
		add(new ListView("dias", new PropertyModel(model,"days")){
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Date date = (Date) item.getModelObject();
				DateFormat format = new SimpleDateFormat("EE dd/MM/yy");
				item.add(new Label("fecha", format.format(date)));
			}
		});

		add(new ListView("eventos", new PropertyModel(model,"activitiesCalendar")) {
			private static final long serialVersionUID = 1L;

			@In(create=true) Date selectedWeek;
			
			@Override
			protected void populateItem(ListItem item) {
				CalendarActivity actividad = (CalendarActivity) item.getModelObject();
				
				Label span = new Label("nombre",actividad.getName());
			
				Days d = Days.daysBetween(new LocalDate(selectedWeek), 
						new LocalDate(actividad.getStart()));
				int column = d.getDays() + 1;
			
				Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
				calendar.setTime(actividad.getStart());
			
				String style = "left: "+(101*column)+"px;";
				
				float horaIniciof = (float) calendar.get(Calendar.HOUR_OF_DAY) + ((float) calendar.get(Calendar.MINUTE))/60;
				style += "top: "+(20+Math.round(horaIniciof*40))+"px;";

				calendar.setTime(actividad.getEnd());
				float horaFinf = (float) calendar.get(Calendar.HOUR_OF_DAY) + ((float) calendar.get(Calendar.MINUTE))/60;
				float pixels = Math.round((horaFinf-horaIniciof)*40);
				style += "height:"+(int)( pixels-3)+"px;";
				
				span.add(new SimpleAttributeModifier("style",style));
				item.add(span);
			}
		});
	}

}
