package ar.com.travelbook.wizard.transport;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.RaiseEvent;

import ar.com.travelbook.RankableDetailPage;
import ar.com.travelbook.common.SeamCompoundPropertyModel;
import ar.com.travelbook.common.SeamReadOnlyModel;
import ar.com.travelbook.domain.TransportCategory;
import ar.com.travelbook.domain.TransportTicketPublic;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

public class PublicTransportPanel extends Panel {

	private final class TransportesListView extends ListView {
		private static final long serialVersionUID = 1L;

		private TransportesListView(String id, IModel model) {
			super(id,model);
		}

		@Override
		protected void populateItem(ListItem item) {
			final int pos = item.getIndex();
			TransportTicketPublic traslado = transportesContratados.get(pos);  
			AjaxButton link=new AjaxButton("link"){
				private static final long serialVersionUID = 1L;
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					TransportTicketPublic transporte = transportesContratados.get(pos);
					travel.getDestinations().get(selectedItem).setTransportTicket(transporte);
					WizardPage page = (WizardPage) this.findPage();
					page.updateAll(target);
				}
			};
			item.add(link);
			
			AjaxButton linkDetalle = new AjaxButton("linkDetalle") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					TransportTicketPublic transporte = transportesContratados.get(pos);
					Page pageDetalle = new RankableDetailPage(transporte.getTransport(), findPage());
					setResponsePage(pageDetalle);
				}
			};
			item.add(linkDetalle);
			
			TransportCategory elegido=traslado.getType();
			item.add(new Label("medio", elegido.name()));
			String costo = ((Float)traslado.getPrice()).toString();
			item.add(new Label("costo", costo));
			link.add(new Label("nombre", (String) traslado.getDescription()+ (String) traslado.getTransport().getName()));
			String fechaSalida = null, fechaLlegada = null;
			if(traslado.getArrivalDateTime()!=null)
				fechaSalida = ((Date)traslado.getArrivalDateTime()).toString();
			if(traslado.getDepartureDateTime()!=null)
				fechaLlegada = ((Date)traslado.getDepartureDateTime()).toString();
			item.add(new Label("fechaSalida", fechaSalida));
			item.add(new Label("fechaLlegada", fechaLlegada));
		}
	}
	
	private static final long serialVersionUID = -5321475128208543954L;
	
	@In private Travel travel;
	
	@In(create=true) private List<TransportTicketPublic> transportesContratados;

	@In private Integer selectedItem;
	
	public PublicTransportPanel(String id){
		super(id);
		Form searchForm = new Form("searchForm", new SeamCompoundPropertyModel("transporteFilter",true)){
			private static final long serialVersionUID = 1L;
			
			@RaiseEvent("searchTransporte")
			@Override
			protected void onSubmit() {
				super.onSubmit();
			}
		};
		add(searchForm);
		
		List<TransportCategory> medios = Arrays.asList(TransportCategory.values());
		DropDownChoice medioTransporte=new DropDownChoice("medioTransporte", medios);
		medioTransporte.setRequired(false);
		medioTransporte.setNullValid(true);
		searchForm.add(medioTransporte);

		searchForm.add(new TextField("empresa"));
		
		DateTextField fechaSalida = new DateTextField("fechaSalida",null,new StyleDateConverter(false));
		fechaSalida.add(new DatePicker());
		searchForm.add(fechaSalida);
		DateTextField fechaLlegada = new DateTextField("fechaLlegada",null, new StyleDateConverter(false));
		fechaLlegada.add(new DatePicker());
		
		TextField minimoPrecio = new TextField("minimoPrecio");
		TextField maximoPrecio = new TextField("maximoPrecio");
		
		searchForm.add(minimoPrecio);
		searchForm.add(maximoPrecio);
		searchForm.add(fechaLlegada);

		ListView repeating = new TransportesListView("repeating", new SeamReadOnlyModel("transportesContratados"));
		searchForm.add(repeating);

	}
	
}
