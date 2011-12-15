package ar.com.travelbook.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.AccessType;
import org.jboss.seam.annotations.Name;

/**
 * User registered in the System
 * 
 * @author cruz
 *
 */
@Entity
@Name("newUser")
@AccessType("field")
public class User extends DomainEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private String username;
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Travel> travels;
	
	public User(){
		this.travels = new ArrayList<Travel>();
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void setViajes(List<Travel> travels) {
		this.travels = travels;
	}

	public List<Travel> getTravels() {
		return travels;
	}

	/**
	 * adds vote from a User to a Rankable
	 * 
	 * @param rankable
	 * @param positive
	 */
	public void vote(Rankable rankable, Boolean positive) {
		rankable.addVote(this, positive);
	}

	/**
	 * Returns if the rankable has been voted  
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public boolean voted(Rankable rankable) {
		return rankable.isVoted(this);
	}

	/**
	 * If has been voted positive
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public boolean hasPositiveVote(Rankable rankable) {
		return rankable.isPositiveVote(this);
	}

	/**
	 * If has been voted negative
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public boolean hasNegativeVote(Rankable rankable) {
		return rankable.isNegativeVote(this);
	}

	/**
	 * Verifies if the user has voted an item, or has added it to a travel
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public boolean hasRelation(Rankable rankable) {
		if(rankable.isVoted(this))
			return true;
		return contains(rankable);
	}

	/**
	 * Returns true if the Rankable was voted by this User
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	private boolean contains(Rankable rankable) {
		for (Travel travel : travels) {
			if(travel.contains(rankable))
				return true;
		}
		return false;
	}

	/**
	 * Gets an integer that symbolizes the Total Vote
	 * 
	 * @param rankable
	 * 
	 * @return
	 */
	public Integer getTotalVote(Rankable rankable) {
		if(this.hasPositiveVote(rankable))
			return 1;
		else if(this.hasNegativeVote(rankable))
			return 0;
		else
			return -1;
	}

	/**
	 * Authenticates a user
	 * 
	 * @param credential
	 * 
	 * @return
	 */
	public boolean authenticate(String credential) {
		return this.password.equals(credential);
	}
}
