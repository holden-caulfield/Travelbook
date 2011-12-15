package ar.com.travelbook.common;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.com.travelbook.domain.Rankable;

public class CloudPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public CloudPanel(String id, IModel items) {
		super(id);
		add(new ListView("item", items){
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Rankable rankable = (Rankable) item.getModelObject();
				item.add(new Label("name", rankable.getName()));
				item.add(new AttributeModifier("value", new PropertyModel(rankable, "totalVotes")));
			}
			
		});
	}


}
