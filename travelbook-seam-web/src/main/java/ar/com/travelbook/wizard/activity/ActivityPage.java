package ar.com.travelbook.wizard.activity;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.contexts.Contexts;
import org.joda.time.DateTime;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamModel;
import ar.com.travelbook.common.SeamReadOnlyModel;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.ChoicePanel;
import ar.com.travelbook.wizard.WizardPage;
import ar.com.travelbook.wizard.summarize.SummarizePage;

public class ActivityPage extends WizardPage {

	private FeedbackPanel feedbackPanel;
	private Form activityTicketTypeForm;
	private Label placeName;

	@In private Travel travel;
	@In private Integer selectedItem;
	
	public ActivityPage() {
		super(3);
		
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		add(feedbackPanel);
		feedbackPanel.setOutputMarkupId(true);
		activityTicketTypeForm = new Form("activityTicketTypeForm");
		placeName = new Label("placeName", new PropertyModel(this,"placeName"));
		add(placeName);
		placeName.setOutputMarkupId(true);
		
		activityTicketTypeForm.add(new ChoicePanel("choicePanel", 
				new SeamModel("activityType",ScopeType.CONVERSATION),
				new PublicActivityPanel("publicDiv"),
				new PrivateActivityPanel("privateDiv")));
		add(activityTicketTypeForm);
		
		activityTicketTypeForm.setOutputMarkupId(true);

		activityTicketTypeForm.add(new AjaxButton("prev"){
			private static final long serialVersionUID = 1L;
			@Override
			@RaiseEvent("searchActividadesCalendar")
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				changeSelectedDate(-1);
				updateMainPanel(target);
			}
		});
		activityTicketTypeForm.add(new AjaxButton("next"){
			private static final long serialVersionUID = 1L;
			@Override
			@RaiseEvent("searchActividadesCalendar")
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				changeSelectedDate(1);
				updateMainPanel(target);
			}
		});
		activityTicketTypeForm.add(new CalendarPanel("calendarPanel",new SeamReadOnlyModel("activitiesCalendar")));
		
	}
	
	public String getPlaceName(){
		return travel.getDestinations().get(selectedItem).getPlace().getName();
	}

	@Override
	@RaiseEvent("searchActividadesCalendar")
	public void updateMainPanel(AjaxRequestTarget target) {
		target.addComponent(activityTicketTypeForm);
	}
	
	@In(create=true) private Date selectedWeek;
	
	public void changeSelectedDate(int i){
		DateTime dtOrg = new DateTime(selectedWeek);
		selectedWeek = dtOrg.plusDays(i).toDate();
		Contexts.getConversationContext().set("selectedWeek", selectedWeek);
	}
}
