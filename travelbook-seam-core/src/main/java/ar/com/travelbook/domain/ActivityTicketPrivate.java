package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

/**
 * Private Activity
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class ActivityTicketPrivate extends Activity {
	private static final long serialVersionUID = 1L;

	public ActivityTicketPrivate() {

	}

	public ActivityTicketPrivate(String name, Date startDateTime, Date endDateTime,
			Place place) {
		super(name, startDateTime, endDateTime, place);
	}

}
