package ar.com.travelbook.helpers;

import java.io.Serializable;

/**
 * Abstract Filter for lists
 * 
 * @author cruz
 *
 */
public class AbstractFilter implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sortProperty;
	private Boolean ascending;

	public AbstractFilter(String sortProperty) {
		this(true, sortProperty);
	}
	
	public AbstractFilter(Boolean ascending, String sortProperty){
		this.ascending = ascending;
		this.sortProperty = sortProperty;
	}
	
	public String getSortProperty() {
		return this.sortProperty;
	}

	public Boolean getAscending() {
		return this.ascending;
	}
	
	public void clear() {
		
	}

}
