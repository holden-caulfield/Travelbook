package ar.com.travelbook.wizard.activity;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
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
import ar.com.travelbook.domain.ActivityPublic;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.WizardPage;

public class PublicActivityPanel extends Panel {
	private final class ActividadesListView extends ListView {
		private static final long serialVersionUID = 1L;

		private ActividadesListView(String id, IModel model) {
			super(id,model);
		}

		@Override
		protected void populateItem(ListItem item) {
			final int pos = item.getIndex();
			ActivityPublic actividad = actividadesContratadas.get(pos);  
			AjaxButton link=new AjaxButton("link"){
				private static final long serialVersionUID = 1L;
				@In private Travel travel;
				@Override
				@RaiseEvent("searchActividadesCalendar")
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					ActivityPublic actividad = actividadesContratadas.get(pos);
					travel.getDestinations().get(selectedItem).addActivity(actividad);
					WizardPage page = (WizardPage) this.findPage();
					page.updateAll(target);
				}
			}; 
			item.add(link);
			
			AjaxButton linkDetalle = new AjaxButton("linkDetalle") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					ActivityPublic actividad = actividadesContratadas.get(pos);
					Page pageDetalle = new RankableDetailPage(actividad.getProvider(), findPage());
					setResponsePage(pageDetalle);
				}
			};
			item.add(linkDetalle);
			
			String costo = ((Float)actividad.getPrice()).toString();
			item.add(new Label("costo", costo));
			link.add(new Label("nombre", (String) actividad.getName()));
			String fechaInicio = null, fechaFin = null;
			if(actividad.getStartDateTime()!=null)
				fechaInicio = ((Date)actividad.getStartDateTime()).toString();
			if(actividad.getEndDateTime()!=null)
				fechaFin = ((Date)actividad.getEndDateTime()).toString();
			item.add(new Label("fechaInicio", fechaInicio));
			item.add(new Label("fechaFin", fechaFin));
		}
	}
	
	
	private static final long serialVersionUID = 1L;

	
	@In(create=true) private List<ActivityPublic> actividadesContratadas;

	@In private Integer selectedItem;
	
	public PublicActivityPanel(String id) {
		super(id);
		
		Form searchForm = new Form("searchForm", new SeamCompoundPropertyModel("activityFilter",true)){
			private static final long serialVersionUID = 1L;
			
			@RaiseEvent("searchActividad")
			@Override
			protected void onSubmit() {
				super.onSubmit();
			}
		};
		add(searchForm);
		
		searchForm.add(new TextField("name"));
	
		DateTextField fechaInicio = new DateTextField("startDate",null,new StyleDateConverter(false));
		fechaInicio.add(new DatePicker());
		searchForm.add(fechaInicio);
		DateTextField fechaFin = new DateTextField("endDate",null, new StyleDateConverter(false));
		fechaFin.add(new DatePicker());
		
		TextField minimoPrecio = new TextField("minPrice");
		TextField maximoPrecio = new TextField("maxPrice");
		
		searchForm.add(minimoPrecio);
		searchForm.add(maximoPrecio);
		searchForm.add(fechaFin);
		
		searchForm.add(new ActividadesListView("repeating",new SeamReadOnlyModel("actividadesContratadas")));
	}


}
