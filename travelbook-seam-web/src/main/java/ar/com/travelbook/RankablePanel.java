package ar.com.travelbook;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.rating.RatingPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.com.travelbook.domain.Place;
import ar.com.travelbook.domain.Rankable;

public class RankablePanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	public RankablePanel(String id) {
		super(id);
		this.add(new RatingPanel("rating", new PropertyModel(this, "rating"), new Model(5),
				new PropertyModel(this, "numberOfVotes"), new Model(true), false) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onRated(int rating, AjaxRequestTarget target) {
				throw new UnsupportedOperationException("Rating on RankablePanel is read-only");
			}
			
			@Override
			protected boolean onIsStarActive(int star) {
				return  Math.round((Float) this.getModelObject()) > star;
			}
		});
		this.add(this.createCountryFlagImage());
		this.add(new Label("name"));
		this.add(new Label("country"));
		this.add(new Label("region"));

	}


	private boolean isInitialized() {
		return this.getModelObject() != null;
		
	}

	private Image createCountryFlagImage() {
		Image countryFlagImage = new Image("rankableCountryFlag");
		countryFlagImage.add(new AttributeModifier("src", new PropertyModel(this, "countryFlagUrl")));
		countryFlagImage.setOutputMarkupId(true);
		return countryFlagImage;
	}

	public String getCountryFlagUrl(){
		return (this.isInitialized()) ?
				"images/countries/" + ((Place) this.getRankable()).getCode() + ".png" : "";
				
	}

	private Rankable getRankable() {
		return (Rankable) this.getModelObject();
	}

	public Integer getNumberOfVotes() {
		return (this.isInitialized()) ? this.getRankable().getTotalVotes() : 0;
	}

	public Float getRating() {
		return (this.isInitialized()) ? this.getRankable().getVotesRating() * 5 : 0;
	}
	
}

