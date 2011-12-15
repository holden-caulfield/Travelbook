package ar.com.travelbook.wizard;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.jboss.seam.annotations.In;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.accomodation.AccomodationPage;
import ar.com.travelbook.wizard.activity.ActivityPage;
import ar.com.travelbook.wizard.itinerary.ItineraryPage;
import ar.com.travelbook.wizard.summarize.SummarizePage;
import ar.com.travelbook.wizard.transport.TransportPage;

public class StepsPanel extends Panel {
	private class Step extends AjaxLink {
		private static final long serialVersionUID = 1L;
		private int step;
		
		private Step(String id, int step) {
			super(id);
			this.step=step;
			if(step==wizardPage.getCurrentStep()){
				this.add(new AttributeAppender("class", new Model("current"), " "));
			}
		}
		@Override
		public boolean isEnabled() {
			if(step>=wizardPage.getCurrentStep())
				return false;
			else
				return true;
		}
		@Override
		public void onClick(AjaxRequestTarget target) {
			switch (step) {
			case 0:
				setResponsePage(PageSolver.solvePage(ItineraryPage.class));
				break;
			case 1:
				setResponsePage(PageSolver.solvePage(TransportPage.class));
			default:
				break;
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private WizardPage wizardPage;
	
	@In(create=true) private Travel travel;
	
	public StepsPanel(String id, WizardPage wizardPage) {
		super(id);
		this.wizardPage=wizardPage;
		add(new Step("itineraryLink",0));
		add(new Step("transportLink",1){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isEnabled() {
				return travel.canChooseTransport();
			}
		});
		add(new Step("accomodationLink",2){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return travel.canChooseAccomodation();
			}
			
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(PageSolver.solvePage(AccomodationPage.class));
			}
		});
		add(new Step("activityLink",3){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isEnabled() {
				return travel.canChooseAccomodation();
			}
			
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(PageSolver.solvePage(ActivityPage.class));
			}
			
		});
		add(new Step("confirmationLink",4){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isEnabled() {
				return travel.canChooseAccomodation();
			}
			
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				setResponsePage(PageSolver.solvePage(SummarizePage.class));
			}
			
		});
		setOutputMarkupId(true);
	}
}
