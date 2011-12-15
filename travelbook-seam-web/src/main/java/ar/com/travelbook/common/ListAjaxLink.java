package ar.com.travelbook.common;

import java.util.List;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;

public abstract class ListAjaxLink extends AjaxLink {

	private static final long serialVersionUID = 1L;

	public ListAjaxLink(String id) {
		super(id);
	}

	private ListItem getListItem() {
		return (ListItem) this.getParent();
	}

	@SuppressWarnings("unchecked")
	private List getList() {
		return (List) getListItem().getParent().getModelObject();
	}
	
	protected Object getListObject() {
		return getList().get(getListItem().getIndex());
	}
}
