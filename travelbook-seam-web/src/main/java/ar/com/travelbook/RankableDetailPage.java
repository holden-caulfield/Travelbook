package ar.com.travelbook;

import javax.persistence.EntityManager;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.security.Identity;

import ar.com.travelbook.common.ContextSeamPropertyModel;
import ar.com.travelbook.common.WebSite;
import ar.com.travelbook.domain.Comment;
import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.User;

public class RankableDetailPage extends WebSite {

	private String comments;
	private WebMarkupContainer detailPanel;
	@In Identity identity;
	
	public RankableDetailPage(Rankable rankable, final Page previous) {
		super();
		Contexts.getConversationContext().set("rankableObject", rankable);

		Label lblNombre = new Label("name",rankable.getName());
		this.add(lblNombre);
		this.add(new AjaxLink("back"){
			private static final long serialVersionUID = 1L;

			@In EntityManager entityManager;
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				entityManager.flush();
				setResponsePage(previous);
			}
		});
		
		detailPanel = new WebMarkupContainer("detailPanel");
		
		Label lblVotosTotal = new Label("totalVotes",
				new ContextSeamPropertyModel("rankableObject","votesRatio"));
		detailPanel.add(lblVotosTotal);
		
		ListView commentsItems = new ListView("commentsItems", new ContextSeamPropertyModel("rankableObject","comments")) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem item) {
				Comment comment = (Comment) item.getModelObject();
				item.add(new Label("previousComments",comment.getText()));
				item.add(new Label("user",comment.getUset().getUsername()));
			}
		};
		
		detailPanel.add(commentsItems);
		
		WebMarkupContainer votePanel = new WebMarkupContainer("votePanel"){
			private static final long serialVersionUID = 1L;
			
			@In private Rankable rankableObject;
			
			@Override
			public boolean isVisible() {
				if(identity.isLoggedIn()){
					User user = (User) Component.getInstance("user");
					return !user.voted(rankableObject);
				}
				return false;
			}
		};
		
		votePanel.add(new VoteAjaxLink("iLike", true));
		votePanel.add(new VoteAjaxLink("iDontLike", false));
		
		detailPanel.add(votePanel);
		detailPanel.setOutputMarkupId(true);

		this.add(detailPanel);
		
		this.add(new AjaxLink("loginToPost"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				interceptLogin();
			}
			
			@Override
			public boolean isVisible() {
				return !identity.isLoggedIn();
			}
		});
		
		Form postComments = new Form("postComments"){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isVisible() {
				return identity.isLoggedIn();
			}
		}; 
		this.add(postComments);
		
		postComments.add(new TextField("comments", new PropertyModel(this,"comments")));
		
		postComments.add(new AjaxLink("submit"){
			private static final long serialVersionUID = 1L;

			@In private Rankable rankableObject;
			
			@In EntityManager entityManager;
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				User user = (User) Component.getInstance("user");
				Comment comment = new Comment(user,comments);
				rankableObject.addComment(comment);
				
				target.addComponent(detailPanel);
				entityManager.flush();
				comments = "";
			}
		});
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComments() {
		return comments;
	}
}
