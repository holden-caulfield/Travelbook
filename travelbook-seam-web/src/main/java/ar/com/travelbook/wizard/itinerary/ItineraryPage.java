package ar.com.travelbook.wizard.itinerary;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.wicket.annotations.NoConversationPage;

import ar.com.travelbook.Home;
import ar.com.travelbook.RankablePanel;
import ar.com.travelbook.common.CloudPanel;
import ar.com.travelbook.common.ContextSeamPropertyModel;
import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamReadOnlyModel;
import ar.com.travelbook.domain.Place;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.WizardConversationManager;
import ar.com.travelbook.wizard.WizardPage;
import ar.com.travelbook.wizard.action.itinerary.PlaceFilter;

@NoConversationPage(Home.class)
public class ItineraryPage extends WizardPage {
	@In(create=true) WizardConversationManager wizardConversationManager;
	
	private static final long serialVersionUID = 1L;
	private Form searchPlaceForm;

	private WebMarkupContainer mapPanel;
	private RankablePanel placeDetailPanel; 

	public ItineraryPage() {
		super(0);
		this.buildSearchPlaceForm();

		this.buildMapPanel();
		add(new CloudPanel("placesCloud", new SeamReadOnlyModel("topPlaces")));
		this.buildPlaceDetailPanel();
	}

	private void buildMapPanel() {
		this.mapPanel = new WebMarkupContainer("mapPanel");
		this.mapPanel.setOutputMarkupId(true);
		this.mapPanel.add(this.buildPinpoints());
		add(this.mapPanel);
	}

	private ListView buildPinpoints() {
		return new ListView("pinpoints", new SeamReadOnlyModel("places")) {
			private static final long serialVersionUID = 1L;
			@In private Travel travel;
			@In(create=true) private PlaceFilter placeFilter;
			
			@Override
			protected void populateItem(final ListItem item) {
				Place place = (Place) item.getModelObject();
				item.add(new AttributeAppender("style", true, 
						new Model("top: " + place.getY() + "px; left: " + place.getX() + "px;"), " " ));
				if(this.travel.contains(place)) {
					item.add(new AttributeAppender("class", true, new Model("selected"), " "));
				}
				item.add(new AjaxEventBehavior("onclick") {
					private static final long serialVersionUID = 1L;
						
					@RaiseEvent("searchPlace")
					protected void onEvent(AjaxRequestTarget target) {
							Place place = (Place) this.getComponent().getModelObject();
							travel.addDestination(place);
							placeFilter.clear();
							this.getComponent().setResponsePage(PageSolver.solvePage(ItineraryPage.class));
						}
				});		
				
				item.add(new AjaxEventBehavior("onmouseover") {
					private static final long serialVersionUID = 1L;
						
					protected void onEvent(AjaxRequestTarget target) {
						ItineraryPage.this.placeDetailPanel.setModel(
								new CompoundPropertyModel(item.getModelObject()));
						target.addComponent(ItineraryPage.this.placeDetailPanel);
						ItineraryPage.this.placeDetailPanel.setVisible(true);
					}
				});		

			}
		};
	}

	private void buildSearchPlaceForm() {
		searchPlaceForm = new Form("searchPlace"){
			private static final long serialVersionUID = 1L;

			@Override
			@RaiseEvent("searchPlace")
			protected void onSubmit() {
				
			}
		};
		searchPlaceForm.setOutputMarkupId(true);
		searchPlaceForm.add(new TextField("searchString", new ContextSeamPropertyModel("placeFilter","search")));
		add(searchPlaceForm);
	}

	private void buildPlaceDetailPanel() {
		this.placeDetailPanel = new RankablePanel("placeDetailPanel");
		this.placeDetailPanel.setOutputMarkupId(true);
		this.placeDetailPanel.setOutputMarkupPlaceholderTag(true);
		this.placeDetailPanel.setVisible(false);
		add(this.placeDetailPanel);
	}
	
	@Override
	public void updateMainPanel(AjaxRequestTarget target) {
		target.addComponent(searchPlaceForm);
		target.addComponent(this.mapPanel);
	}

}
