package ar.com.travelbook.recommender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.User;

/**
 * Implementation of DataModel for Mahout Library
 * 
 * @author cruz
 *
 */
@Scope(ScopeType.APPLICATION)
@Name("travelbookDataModel")
public class TravelbookDataModel implements DataModel {

	private DataModel dataModel;

	@In
	private EntityManager entityManager;

	/**
	 * Creates the DataModel and loads from the Domain 
	 */
	@Create
	public void create() {
		try {
			dataModel = new FileDataModel(createTempFile());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates an empty file for storing the mapped data
	 * 
	 * @return File
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private File createTempFile() throws IOException {
		File resultFile = new File(new File(System
				.getProperty("java.io.tmpdir")), "ratings.txt");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(resultFile), Charset.forName("UTF-8")));
		} catch (IOException ioe) {
			resultFile.delete();
			throw ioe;
		}

		
		List<Rankable> clasificables = entityManager.createQuery(
				"SELECT DISTINCT c FROM Rankable AS c LEFT JOIN FETCH c.votes"
		).getResultList();
		
		List<User> users = entityManager.createQuery("FROM User").getResultList();
		for (User user : users) {
			for (Rankable clasificable : clasificables) {
				writeVoteUser(writer, user, clasificable);
			}
		}
		writer.flush();
		return resultFile;
	}

	/**
	 * Writes data of a User in the file
	 * 
	 * @param writer
	 * @param user
	 * @param clasificable
	 */
	private void writeVoteUser(PrintWriter writer, User user,
			Rankable clasificable) {
		int votoTotal =user.getTotalVote(clasificable);
		if(votoTotal != -1)
			writer.println(user.getId() + "," + clasificable.getId() + ","
					+ votoTotal);
	}

	/**
	 * Gets all the Item IDs
	 */
	public LongPrimitiveIterator getItemIDs() throws TasteException {
		return dataModel.getItemIDs();
	}

	/**
	 * Gets ItemsId voted from one User
	 */
	public FastIDSet getItemIDsFromUser(long userID) throws TasteException {
		return dataModel.getItemIDsFromUser(userID);
	}

	/**
	 * The total number of voted items 
	 */
	public int getNumItems() throws TasteException {
		return dataModel.getNumItems();
	}

	/**
	 * The total number of users that have voted
	 */
	public int getNumUsers() throws TasteException {
		return dataModel.getNumUsers();
	}

	/**
	 * The users that voted several items.
	 */
	public int getNumUsersWithPreferenceFor(long... itemIDs)
			throws TasteException {
		return dataModel.getNumUsersWithPreferenceFor(itemIDs);
	}

	/**
	 * Gets the preference of one user with one item
	 */
	public Float getPreferenceValue(long userID, long itemID)
			throws TasteException {
		return dataModel.getPreferenceValue(userID, itemID);
	}

	/**
	 * Gets all the votes of an item 
	 */
	public PreferenceArray getPreferencesForItem(long itemID)
			throws TasteException {
		return dataModel.getPreferencesForItem(itemID);
	}

	/**
	 * Gets all votes from a user
	 */
	public PreferenceArray getPreferencesFromUser(long userID)
			throws TasteException {
		return dataModel.getPreferencesFromUser(userID);
	}

	/**
	 * get all user ids that voted
	 */
	public LongPrimitiveIterator getUserIDs() throws TasteException {
		return dataModel.getUserIDs();
	}

	/**
	 * Removes a preference from a user for an item
	 */
	public void removePreference(long userID, long itemID)
			throws TasteException {
		dataModel.removePreference(userID, itemID);
	}

	/**
	 * Sets a preference from a user for an item
	 */
	public void setPreference(long userID, long itemID, float value)
			throws TasteException {
		
		dataModel.setPreference(userID, itemID, value);
	}

	/**
	 * Refresh data
	 */
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		dataModel.refresh(alreadyRefreshed);
	}

}
