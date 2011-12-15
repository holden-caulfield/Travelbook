package ar.com.travelbook.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * Public activity. Links a provider to a Travel
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class ActivityPublic extends Activity {
	private static final long serialVersionUID = 1L;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="provider_id")
	private Provider provider;

	
	protected ActivityPublic() {
		
	}
	
	public ActivityPublic(String name,Date startDateTime, Date endDateTime, Place place, Provider provider) { 
		super(name, startDateTime, endDateTime, place);
		this.provider = provider;
	}


	public Provider getProvider() {
		return provider;
	}
	
	public String getName() {
		return this.getProvider().getName();
	}
}
