package ar.com.travelbook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.AccessType;

/**
 * Comment from a user on a Rankable Entity
 * 
 * @author cruz
 *
 */
@Entity
@AccessType("field")
public class Comment extends DomainEntity{
	private static final long serialVersionUID = 1L;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
	private User user;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "rankable_id", insertable = false, updatable = false)
	private Rankable rankable;
	
	public Comment() {
		
	}
	
	public Comment(User user, String text) {
		this.user = user;
		this.text = text;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUset() {
		return user;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	public void setRankable(Rankable rankable) {
		this.rankable = rankable;
	}

	public Rankable getRankable() {
		return rankable;
	}
	
}
