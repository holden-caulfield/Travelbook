package ar.com.travelbook.wizard.summarize;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.core.Manager;
import org.jboss.seam.security.Identity;
import org.joda.time.DateMidnight;

import ar.com.travelbook.common.ContextSeamPropertyModel;
import ar.com.travelbook.common.SeamCompoundPropertyModel;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.domain.User;
import ar.com.travelbook.wizard.WizardPage;

/**
 * Shows all details from a travel
 * 
 * @author cruz
 *
 */
public class SummarizePage extends WizardPage {
	
	public SummarizePage() {
		super(4);
		ListView repeating;
		PropertyModel diasItemsModel = new ContextSeamPropertyModel("travelSummary","days",true);
		repeating = new ListView("travelItemDays", diasItemsModel) {
			private static final long serialVersionUID = 1L;
			@In(create=true) TravelSummary travelSummary;
			@Override
			protected void populateItem(final ListItem item) {
				DateMidnight day = (DateMidnight) item.getModelObject();
							
				List<TravelSummaryItem> travelItems = travelSummary.getItems().get(day);
				item.add(new Label("dayName",day.toString()));
				
				ListView repeatingItems = new ListView("travelItem",travelItems) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(final ListItem travelItem) {
						TravelSummaryItem travelSummaryItem = (TravelSummaryItem)travelItem.getModelObject();
						travelItem.add(new Label("description",travelSummaryItem.getTitle()));
					}
				};
				item.add(repeatingItems);
			}
		};
		this.add(repeating);
		Form form = new Form("save", new SeamCompoundPropertyModel("travel")){
			private static final long serialVersionUID = 1L;
			@In private Travel travel;
			
			@In private EntityManager entityManager;

			@In Identity identity;
			
			@Override
			protected void onSubmit() {
				if(!identity.isLoggedIn()){
					info("Logueate por favor");
					interceptLogin();
					return;
				}
				User user = (User) Component.getInstance("user", true);
				if(!user.getTravels().contains(travel))
					user.getTravels().add(travel);
				entityManager.flush();
				Manager.instance().endRootConversation(false);
			}
			
		};
		form.add(new TextField("name"));
		add(form);
	}
}
