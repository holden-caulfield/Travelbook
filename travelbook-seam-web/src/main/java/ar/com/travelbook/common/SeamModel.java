package ar.com.travelbook.common;

import org.apache.wicket.Component;
import org.apache.wicket.model.ComponentDetachableModel;
import org.jboss.seam.RequiredException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.contexts.ServerConversationContext;

public class SeamModel extends ComponentDetachableModel {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean create;
	private ScopeType scopeType;

	public SeamModel(String name){
		this.name = name;
		this.create = true;
		this.scopeType = ScopeType.UNSPECIFIED;
	}

	public SeamModel(String name, ScopeType scopeType){
		this.name = name;
		this.create = true;
		this.scopeType = scopeType;
	}

	@Override
	protected Object getObject(Component component) {
		Object seamObject;
		ServerConversationContext context = (ServerConversationContext) Contexts.getConversationContext();
		context.unflush();

		if (scopeType == ScopeType.UNSPECIFIED)
			seamObject = org.jboss.seam.Component.getInstance(name, create);
		else
			seamObject = org.jboss.seam.Component.getInstance(name, scopeType, create);

		if (seamObject == null)
			throw new RequiredException("Couldn't find an instance '" + name
					+ "' of object");
		return seamObject;
	}
	
	@Override
	protected void setObject(Component component, Object object) {
		if(scopeType == ScopeType.UNSPECIFIED)
			ScopeType.CONVERSATION.getContext().set(name, object);
		else
			scopeType.getContext().set(name, object);
	}

	
}
