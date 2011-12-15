package ar.com.travelbook.recommender;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.User;

/**
 * Gives recommendations for a user
 * @author cruz
 *
 */
@Name("userRecommender")
public class UserRecommender {

	@In
	private User user;

	@In
	private EntityManager entityManager;

	@In(create = true)
	MahoutUserRecommender mahoutUserRecommender;

	/**
	 * Returns a list of items recommended for the logged user
	 * @return
	 */
	@Factory(scope=ScopeType.EVENT)
	public List<Rankable> getRecommendationsForUser() {
		List<RecommendedItem> recommendedItems;
		List<Rankable> recomendaciones = new ArrayList<Rankable>();
		try {
			recommendedItems = mahoutUserRecommender
					.recommend(user.getId(), 5);
		} catch(NoSuchUserException e){
			return recomendaciones;
		} catch (TasteException e) {
			throw new RuntimeException(e);
		}
		for (RecommendedItem recommendedItem : recommendedItems) {
			Rankable clasificable = (Rankable) entityManager.find(Rankable.class,(int)recommendedItem.getItemID());
			recomendaciones.add(clasificable);
		}
		return recomendaciones;
	}
}
