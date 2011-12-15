package ar.com.travelbook.wizard.activity;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;

import ar.com.travelbook.common.EntityForm;
import ar.com.travelbook.domain.ActivityTicketPrivate;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

public class PrivateActivityPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private ActivityTicketPrivate activityTicketPrivate;
	
	@In private Integer selectedItem;	
	
	public PrivateActivityPanel(String id) {
		super(id);
		activityTicketPrivate = new ActivityTicketPrivate();
		EntityForm personalForm = new EntityForm("personalForm",new CompoundPropertyModel(null)
		{
			private static final long serialVersionUID = 1L;
			@Override
			public Object getObject() {
				return getActivityTicketPrivate();
			}
		});
		this.add(personalForm);
		TextField nombre = new TextField("name");
		personalForm.add(nombre);
		final DateTimeField fechaInicio = new DateTimeField("startDateTime");
		personalForm.add(fechaInicio);
		final DateTimeField fechaFin = new DateTimeField("endDateTime");
		personalForm.add(fechaFin);

		personalForm.add(new AjaxButton("pSubmit"){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onError(AjaxRequestTarget target, Form form) {
				target.addComponent(form.getPage().get("feedbackPanel"));
				super.onError(target, form);
			}
			
			@In private Travel travel;
			@Override
			@RaiseEvent("searchActividadesCalendar")
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				travel.getDestinations().get(selectedItem).addActivity(getActivityTicketPrivate());
				PrivateActivityPanel.this.activityTicketPrivate=new ActivityTicketPrivate();
				WizardPage page = (WizardPage)this.findPage();
				page.updateAll(target);
			}
		});
		personalForm.addEntityValidators();

		personalForm.add(new AbstractFormValidator(){
			private static final long serialVersionUID = 1L;

			public FormComponent[] getDependentFormComponents() {
				FormComponent[] components;
				components = new FormComponent[] { fechaFin,fechaInicio };
				return components;
			}

			public void validate(Form form) {
				Date fechaInicioDate =(Date) fechaInicio.getConvertedInput();
				Date fechaFinDate=(Date) fechaFin.getConvertedInput();
				
				if(!fechaFinDate.after(fechaInicioDate))
					form.error("Fecha de fin tiene que ser posterior a fecha de inicio");
			}
			
		});
	}

	private ActivityTicketPrivate getActivityTicketPrivate() {
		return activityTicketPrivate;
	}
	
	
}
