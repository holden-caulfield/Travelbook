package ar.com.travelbook.helpers;

import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;

/**
 * Custom restrictions for Criterias
 * 
 * @author cruz
 *
 */
public class CustomRestrictions {

	/**
	 * Cannot be instantiated
	 */
	private CustomRestrictions() {
	}

	/**
	 * Apply an "equal" constraint to the identifier property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static Criterion sameDay(String propertyName, Date value) {
		String propertyExpression = "day({alias}." + propertyName + ") = day(?)";
		return Restrictions.sqlRestriction(propertyExpression, 
				value, new DateType());
	}
}
