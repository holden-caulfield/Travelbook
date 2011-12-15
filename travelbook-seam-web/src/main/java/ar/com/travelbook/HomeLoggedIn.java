package ar.com.travelbook;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Out;

import ar.com.travelbook.common.ContextSeamPropertyModel;
import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamModel;
import ar.com.travelbook.common.WebSite;
import ar.com.travelbook.domain.Rankable;
import ar.com.travelbook.domain.User;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.itinerary.ItineraryPage;

public class HomeLoggedIn extends WebSite {

	@In(create = true)
	private User user;

	public HomeLoggedIn() {
		this.add(new Label("username", user.getUsername()));

		this.add(new ListView("userTravels", new ContextSeamPropertyModel(
				"user", "travels")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				final int pos = item.getIndex();
				Travel travel = (Travel) item.getModelObject();
				item.add(new AjaxLink("travel") {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					@Out(required = false)
					private Travel travel;

					@Override
					public void onClick(AjaxRequestTarget target) {
						travel = user.getTravels().get(pos);
						setResponsePage(PageSolver
								.solvePage(ItineraryPage.class));
					}
				}.add(new Label("travelName", travel.getName())));
			}
		});
		this.add(new ListView("suggestions", new SeamModel(
				"recommendationsForUser")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Rankable rankable = (Rankable) item.getModelObject();
				item.add(new Label("suggestionType", rankable.getClass().getSimpleName()));
				//@TODO: En la pantalla aparece Transport, que es el nombre de la clase. Hay que traducirlo
				item.add(new Label("suggestionName", rankable.getName()));
			}
		});

	}
}
