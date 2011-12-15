package ar.com.travelbook.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.AccessType;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * A domain entity that can have votes and comments
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Rankable extends DomainEntity {

	private static final long serialVersionUID = 1L;
		
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="rankable_id")
	private List<Comment> comments;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="rankable_id")
	private List<Vote> votes;
	
	@NotNull @Length(min=3, max=100)
	private String name;
	
	private int votesRatio;
	
	@Transient private int totalVotes;
	@Transient private int positiveVotes;
	@Transient private int negativeVotes;
	@Transient private boolean initialized;
	
	public Rankable() 
	{
		this.comments = new ArrayList<Comment>();
		this.votes = new ArrayList<Vote>();
		this.negativeVotes = this.positiveVotes = this.totalVotes = 0;
		this.votesRatio = 0;
		this.initialized = false;
	}

	/**
	 * Counts votes loaded from DB
	 */
	private void initialize() {
		for (Vote voto : this.votes) {
			this.totalVotes++;
			if (voto.getPositive()) { 
				this.positiveVotes++; 
			} else { 
				this.negativeVotes++;
			}
		}
		this.initialized = true;
	}

	public String getName() {
		return name;
	}

	public int getTotalVotes() {
		if (!isInitialized()) 
			initialize();
		return this.totalVotes;
	}

	public int getVotesRatio() {
		return votesRatio;
	}

	public int getPositiveVotes() {
		if (!isInitialized()) 
			initialize();
		return positiveVotes;
	}

	public int getNegativeVotes() {
		if (!isInitialized()) 
			initialize();
		return negativeVotes;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	public List<Vote> getVotes() {
		return votes;
	}
	
	/**
	 * Adds a vote
	 * 
	 * @param user User that voted
	 * @param positive Positive or negative vote
	 * 
	 */
	void addVote(User user, Boolean positive) {
		if (positive){
			this.votesRatio++; 
		} else {
			this.votesRatio--;
		}
		this.votes.add(new Vote(user, positive));
	}

	/**
	 * If the user already voted this Rankable
	 * 
	 * @param user User
	 * 
	 * @return boolean
	 */
	public boolean isVoted(User user) {
		for (Vote vote : votes) 
			if(vote.getUser()==user)
				return true;
		
		return false;
	}

	/**
	 * Returns true if the user voted this entity as positive
	 * 
	 * @param user User
	 * 
	 * @return boolean
	 */
	public boolean isPositiveVote(User user) {
		for (Vote vote : votes) 
			if(vote.getUser()==user && vote.getPositive())
				return true;
		return false;
	}
	
	/**
	 * Returns true if the user voted this entity as negative
	 * 
	 * @param user User
	 * 
	 * @return boolean
	 */
	public boolean isNegativeVote(User user) {
		for (Vote vote : votes) 
			if(vote.getUser()==user && !vote.getPositive())
				return true;
		return false;
	}

	public void addComment(Comment comment) {
		this.getComments().add(comment);
		comment.setRankable(this);
	}
	
	/**
	 *  Returns the ratio of positive votes against the total of votes
	 *  
	 *	@return float a number from 0 to 1 representing the ratio of positive votes
	 */
	public float getVotesRating() {
		if (this.getTotalVotes() == 0) return 0;
		return this.getPositiveVotes() / (float)this.getTotalVotes();
	}
}
