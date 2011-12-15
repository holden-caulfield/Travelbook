package ar.com.travelbook;

import org.apache.wicket.markup.html.link.Link;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.WebSite;
import ar.com.travelbook.wizard.itinerary.ItineraryPage;

public class Home extends WebSite {

	public Home(){
		add(new Link("myTravel"){
			private static final long serialVersionUID = 1L;
			@Override
			@Begin(flushMode=FlushModeType.MANUAL, join=true)
			public void onClick() {
				setResponsePage(PageSolver.solvePage(ItineraryPage.class));
			}
		});
		add(new SignUpPanel("register"));
	}
}
