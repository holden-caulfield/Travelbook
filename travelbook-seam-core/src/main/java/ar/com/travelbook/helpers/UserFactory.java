package ar.com.travelbook.helpers;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import ar.com.travelbook.domain.User;

/**
 * Creates User logged
 * 
 * @author cruz
 * 
 */
@Name("UserFactory")
public class UserFactory {
	@In
	private EntityManager entityManager;

	/**
	 * The factory implementation that creates the user
	 * 
	 * @return User
	 */
	@Factory(scope = ScopeType.CONVERSATION)
	public User getUser() {
		Query userQuery = entityManager
				.createQuery("SELECT u FROM User AS u WHERE "
						+ "u.username=#{identity.username}");
		return (User) userQuery.getSingleResult();
	}
}
