package ar.com.travelbook.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Place that can be a Destination in a Travel
 * 
 * @author cruz
 *
 */
@Entity
@Name("place")
@Scope(ScopeType.CONVERSATION)
@AccessType("field")
public class Place extends Rankable {
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	
	private String country;
	private String region;
	private String code;
	
	public Place() {
	
	}
	
	public Place(String name) {
		this.setName(name);
	}
	
	public Place(String name, int x, int y) {
		this.setName(name);
		this.setPosition(x, y);
	}
	
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
