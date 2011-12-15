package ar.com.travelbook.helpers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

import ar.com.travelbook.domain.User;


/**
 * This class authenticates if the user can be validated inside SEAM by
 * security:identity defined in file src/main/resources/components.xml
 * 
 * @author cruz
 * 
 */
@Name("authenticator")
public class Authenticator {

	@In
	private EntityManager entityManager;

	@In
	private Identity identity;

	/**
	 * Performs validation
	 * 
	 * @return boolean
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public boolean authenticate() {
		List users = getUserQuery().getResultList();
		if (users.size() == 1) {
			User user = (User) users.get(0);
			return user.authenticate(identity.getPassword());
		} else
			return false;
	}

	/**
	 * Returns query that searches a user with username
	 * 
	 * @return Query
	 */
	private Query getUserQuery() {
		return entityManager.createQuery("SELECT u FROM User AS u WHERE "
				+ "u.username=#{identity.username}");
	}

}
