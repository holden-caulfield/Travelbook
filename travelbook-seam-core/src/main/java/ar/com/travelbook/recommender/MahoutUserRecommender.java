package ar.com.travelbook.recommender;

import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Mahout User Recommender. Recommends items to users (according to their preferences).
 * 
 */
@Scope(ScopeType.APPLICATION)
@Name("mahoutUserRecommender")
public class MahoutUserRecommender implements Recommender {

	private Recommender recommender;

	@In(create=true) private TravelbookDataModel travelbookDataModel;
	
	/**
	 * Creates and loads all data from the Domain
	 * 
	 * @throws TasteException
	 */
	@Create
	public void create() throws TasteException {
		UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(travelbookDataModel);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(3,
				userSimilarity, travelbookDataModel);
		recommender = new CachingRecommender(new GenericUserBasedRecommender(
				travelbookDataModel, neighborhood, userSimilarity));
	}

	/**
	 * Estimate preference for a user and an item
	 */
	public float estimatePreference(long userID, long itemID)
			throws TasteException {
		return recommender.estimatePreference(userID, itemID);
	}

	/**
	 * Returns datamodel with all data
	 */
	public DataModel getDataModel() {
		return recommender.getDataModel();
	}
	
	/**
	 * Get recommendations for a userID. HowMany is the number of preferences top
	 */
	public List<RecommendedItem> recommend(long userID, int howMany)
			throws TasteException {
		return recommender.recommend(userID, howMany);
	}

	/**
	 * Get recommendations for a userID. HowMany is the number of preferences top, 
	 * and Rescorer modifies the scoring for each item 
	 * 
	 */
	public List<RecommendedItem> recommend(long userID, int howMany,
			Rescorer<Long> rescorer) throws TasteException {
		return recommend(userID, howMany, rescorer);
	}

	/**
	 * Deletes a preference of a user for an item
	 */
	public void removePreference(long userID, long itemID)
			throws TasteException {
		recommender.removePreference(userID, itemID);
	}

	/**
	 * Sets a preference of a user for an item
	 */
	public void setPreference(long userID, long itemID, float value)
			throws TasteException {
		recommender.setPreference(userID, itemID, value);
	}

	/**
	 * Recalculates the estimations
	 */
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		recommender.refresh(alreadyRefreshed);
	}

}
