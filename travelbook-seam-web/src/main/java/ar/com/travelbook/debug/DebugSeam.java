package ar.com.travelbook.debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.jboss.seam.annotations.In;
import org.jboss.seam.core.ConversationEntry;
import org.jboss.seam.core.Manager;
import org.jboss.seam.debug.Introspector;

public class DebugSeam extends WebPage {
	private final class StringListView extends ListView {
		private static final long serialVersionUID = 1L;

		private StringListView(String id, List<String> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem item) {
			String name = (String) item.getModelObject();
			PageParameters params = new PageParameters();
			params.put("name", name);
			params.put("cid", Manager.instance().getCurrentConversationId());
			item.add(new BookmarkablePageLink("viewObject", DebugSeam.class,
					params).add(new Label("name", name)));

		}
	}

	private final class AttributeObject implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;
		private String stringValue;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public String getStringValue() {
			return stringValue;
		}

	}

	@In(create = true, value = "org.jboss.seam.debug.contexts")
	org.jboss.seam.debug.Contexts context;

	@In(create = true, value = "org.jboss.seam.debug.introspector")
	org.jboss.seam.debug.Introspector introspector;

	public DebugSeam(PageParameters parameters) {
		ListView lv = new ListView("listConversation", context
				.getConversationEntries()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				final ConversationEntry conversation = (ConversationEntry) item
						.getModelObject();
				item.add(new Label("id", conversation.getId().toString()));
				DateLabel label = DateLabel.forShortStyle("startDateTime",
						new Model(conversation.getStartDatetime()));
				item.add(label);
				label = DateLabel.forShortStyle("lastDateTime", new Model(
						conversation.getLastDatetime()));
				item.add(label);
				item
						.add(new Label("description", conversation
								.getDescription()));
				item.add(new Label("viewId", conversation.getViewId()));
				PageParameters params = new PageParameters();
				params.put("cid", conversation.getId().toString());
				item.add(new BookmarkablePageLink("viewConversation",
						DebugSeam.class, params));
			}
		};
		add(lv);
		

		List<AttributeObject> listAttributes = new ArrayList<AttributeObject>();
		Introspector.Attribute[] arrayAtt = null;
		if (parameters.get("name") != null) {
			try {
				arrayAtt = introspector.getAttributes();
			} catch (Exception e) {
				System.out.println("couldn't load attributes of object" + parameters.getString("name"));
			}
		}
		if (arrayAtt != null) {
			for (Introspector.Attribute att : arrayAtt) {
				if (att != null) {
					AttributeObject attributeObj = new AttributeObject();
					attributeObj.setName(att.getName());
					attributeObj.setStringValue(att.getStringValue());
					listAttributes.add(attributeObj);
				}
			}
		}
		ListView listAttributeslv = new ListView("listAttributes",
				listAttributes) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				AttributeObject attribute = (AttributeObject) item
						.getModelObject();
				item.add(new Label("name", attribute.getName()));
				item.add(new Label("stringValue", attribute.getStringValue()));
			}
		};
		add(listAttributeslv);

		addListObjects(context.getConversation(), "listContext");
		addListObjects(context.getBusinessProcess(),"listBusiness");
		addListObjects(context.getSession(),"listSession");
		addListObjects(context.getApplication(), "listApplication");
	}

	private void addListObjects(String[] arrayStrings,String listId) {
		List<String> listObjects = new ArrayList<String>();
		for (String name : arrayStrings) {
			listObjects.add(name);
		}
		ListView sessionLv = new StringListView(listId, listObjects);
		add(sessionLv);
	}

}
