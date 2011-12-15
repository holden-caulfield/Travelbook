package ar.com.travelbook.wizard;

import org.apache.wicket.ajax.AjaxRequestTarget;

import ar.com.travelbook.common.WebSite;

public abstract class WizardPage extends WebSite {

	private int currentStep;
	private TravelDetailsPanel detailsPanel;
	private StepsPanel stepsPanel;
	
	public WizardPage(int currentStep) {
		this.currentStep=currentStep;
		add(stepsPanel=new StepsPanel("stepsPanel",this));
		add(detailsPanel=new TravelDetailsPanel("detailsPanel",this));
	}

	/**
	 * This should update the main panel, where the "editor" or "search" of the editable item exists
	 * @param target 
	 */
	public void updateMainPanel(AjaxRequestTarget target) {
		
	}

	public void updateAll(AjaxRequestTarget target){
		updateMainPanel(target);
		updateDetails(target);
		target.addComponent(stepsPanel);
	}
	
	public void updateDetails(AjaxRequestTarget target){
		target.addComponent(detailsPanel);
	}
	
	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public int getCurrentStep() {
		return currentStep;
	}
	
	
}
