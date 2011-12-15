package ar.com.travelbook.wizard.transport;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.jboss.seam.annotations.In;

import ar.com.travelbook.common.EntityForm;
import ar.com.travelbook.domain.Destination;
import ar.com.travelbook.domain.TransportCategory;
import ar.com.travelbook.domain.TransportTicketPrivate;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

/// TODO agregar costo como propiedad a editar/agregar en TransportePersonal
/// TODO agregar detalle como propiedad a editar/agregar en TransportePersonal
public class PrivateTransportPanel extends Panel {
	private static final long serialVersionUID = 941943527294025869L;
	
	@In private Travel travel;

	private TransportTicketPrivate transportePersonal;
	
	@In private Integer selectedItem;
	
	public PrivateTransportPanel(String id){
		super(id);
		transportePersonal = new TransportTicketPrivate();
		EntityForm personalForm = new EntityForm("personalForm",new CompoundPropertyModel(null)
		{
			private static final long serialVersionUID = 1L;
			@Override
			public Object getObject() {
				return getTransporte();
			}
		});
		this.add(personalForm);
		List<TransportCategory> medios = Arrays.asList(TransportCategory.values());
		DropDownChoice medioTransporte=new DropDownChoice("category", medios);
		personalForm.add(medioTransporte);
		final TextField medioOtro = new TextField("otherCategory");
		medioOtro.setOutputMarkupId(true);
		medioOtro.setOutputMarkupPlaceholderTag(true);
		if(getTransporte().getType() != TransportCategory.Other)
			medioOtro.setVisible(false);
		personalForm.add(medioOtro);
		medioTransporte.add(new AjaxFormComponentUpdatingBehavior("onchange"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				TransportTicketPrivate transporte = getTransporte();
				if(transporte.getType() == TransportCategory.Other)
					medioOtro.setVisible(true);
				else
					medioOtro.setVisible(false);
				target.addComponent(medioOtro);
			}
		});
		TextField empresa = new TextField("description");
		personalForm.add(empresa);
		final DateTimeField fechaSalida = new DateTimeField("departureDateTime");
		personalForm.add(fechaSalida);
		final DateTimeField fechaLlegada = new DateTimeField("arrivalDateTime");
		personalForm.add(fechaLlegada);


		personalForm.add(new AjaxButton("pSubmit"){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onError(AjaxRequestTarget target, Form form) {
				target.addComponent(form.getPage().get("feedbackPanel"));
				super.onError(target, form);
			}
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				travel.getDestinations().get(selectedItem).setTransportTicket(getTransporte());
				PrivateTransportPanel.this.transportePersonal=new TransportTicketPrivate();
				WizardPage page = (WizardPage)this.findPage();
				page.updateAll(target);
			}
		});
		personalForm.addEntityValidators();

		personalForm.add(new AbstractFormValidator(){
			private static final long serialVersionUID = 1L;

			public FormComponent[] getDependentFormComponents() {
				FormComponent[] components;
				components = new FormComponent[] { fechaLlegada,fechaSalida };
				return components;
			}

			public void validate(Form form) {
				Date fechaLlegadaDate =(Date) fechaLlegada.getConvertedInput();
				Date fechaSalidaDate=(Date) fechaSalida.getConvertedInput();
				
				if(!fechaLlegadaDate.after(fechaSalidaDate))
					form.error("Fecha de llegada tiene que ser posterior a fecha de salida");
			}
			
		});
	}

	private TransportTicketPrivate getTransporte() {
		Destination destino = travel.getDestinations().get(selectedItem); 
		TransportTicketPrivate transporte;
		if(destino.getTransportTicket() != null
				&& destino.getTransportTicket().getClass() == TransportTicketPrivate.class)
			transporte = (TransportTicketPrivate) destino.getTransportTicket();
		else
			transporte = transportePersonal;
		return transporte;
	}
	
}
