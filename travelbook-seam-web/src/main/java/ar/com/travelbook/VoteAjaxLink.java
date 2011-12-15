package ar.com.travelbook;

import javax.persistence.EntityManager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Contexts;

import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.User;

public class VoteAjaxLink extends AjaxLink {
	private static final long serialVersionUID = 1L;
	@In EntityManager entityManager;
	@In private Rankable rankableObject;

	private boolean likes;
	VoteAjaxLink(String id, boolean likes) {
		super(id);
		this.likes=likes;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		User user = (User) Component.getInstance("user");
		user.vote(this.rankableObject, this.likes);
		Contexts.getApplicationContext().set("travelbookDataModel", null);
		Contexts.getApplicationContext().set("mahoutUserRecommender", null);
		target.appendJavascript("$(\".votePanel\").hide(\"slow\");");
		entityManager.merge(this.rankableObject);
		entityManager.flush();
	}
}

