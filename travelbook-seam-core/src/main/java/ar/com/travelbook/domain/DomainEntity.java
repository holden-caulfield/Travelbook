package ar.com.travelbook.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.AccessType;

/**
 * Base Domain Entity
 * 
 * @author cruz
 *
 */
@AccessType("field")
@MappedSuperclass
public class DomainEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Integer id;

	protected DomainEntity() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().isInstance(obj)) {
			return false;
		}
		final DomainEntity other = (DomainEntity) obj;
		if (this.getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!this.getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
}
