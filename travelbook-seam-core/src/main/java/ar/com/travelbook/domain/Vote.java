package ar.com.travelbook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * Vote of a Rankable Entity
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class Vote extends DomainEntity{
	private static final long serialVersionUID = 1L;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
	private User user;
	
	private Boolean positive;
	
	@ManyToOne
	@JoinColumn(name = "rankable_id", insertable = false, updatable = false)
	private Rankable rankable;
	
	public Vote() {
		
	}
	
	public Vote(User user, Boolean positive) {
		this.user = user;
		this.positive = positive;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setPositive(Boolean positive) {
		this.positive = positive;
	}
	public Boolean getPositive() {
		return positive;
	}

	public void setRankable(Rankable rankable) {
		this.rankable = rankable;
	}

	public Rankable getRankable() {
		return rankable;
	}
	
}
