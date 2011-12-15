package ar.com.travelbook.wizard.transport;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.wicket.annotations.NoConversationPage;

import ar.com.travelbook.Home;
import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamModel;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.helpers.WizardConversationManager;
import ar.com.travelbook.wizard.ChoicePanel;
import ar.com.travelbook.wizard.WizardPage;
import ar.com.travelbook.wizard.accomodation.AccomodationPage;

@NoConversationPage(Home.class)
public class TransportPage extends WizardPage {
	private static final long serialVersionUID = 1L;

	@In private Travel travel;
	@In Integer selectedItem;
	
	@In(create=true) WizardConversationManager wizardConversationManager;
	private Form tipoTransporteForm;
	private Label title;

	private FeedbackPanel feedbackPanel;

	public TransportPage() {
		super(1);
		
		if(selectedItem==0){
			wizardConversationManager.switchConversation(1);
			setResponsePage(PageSolver.solvePage(TransportPage.class));
		}
			
		this.title = new Label("title",new PropertyModel(this,"title")); 
		add(title);
		title.setOutputMarkupId(true);
		feedbackPanel = new FeedbackPanel("feedbackPanel");
		add(feedbackPanel);
		feedbackPanel.setOutputMarkupId(true);
		this.tipoTransporteForm = new Form("tipoTransporteForm");
		
		tipoTransporteForm.add(new ChoicePanel("choicePanel", 
				new SeamModel("transportType",ScopeType.CONVERSATION),
				new PublicTransportPanel("publicDiv"),
				new PrivateTransportPanel("privateDiv")));
		add(tipoTransporteForm);
		
		tipoTransporteForm.add(new AjaxButton("siguiente"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return travel.canChooseAccomodation();
			}
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				setResponsePage(PageSolver.solvePage(AccomodationPage.class));
			}
			
		});
		tipoTransporteForm.setOutputMarkupId(true);
	}

	public String getTitle(){
		Destination destino = travel.getDestinations().get(selectedItem);
		Destination origen = travel.getDestinations().get(selectedItem-1);
		return "Buscar Transporte de "+origen.getPlace().getName() + " a " + destino.getPlace().getName();
	}

	@Override
	public void updateMainPanel(AjaxRequestTarget target) {
		super.updateMainPanel(target);
		target.addComponent(this.feedbackPanel);
		target.addComponent(this.title);
		target.addComponent(this.tipoTransporteForm);
	}
	
}
