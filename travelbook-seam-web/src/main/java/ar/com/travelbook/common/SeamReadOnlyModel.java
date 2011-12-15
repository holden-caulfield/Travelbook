package ar.com.travelbook.common;

import org.apache.wicket.model.LoadableDetachableModel;
import org.jboss.seam.Component;
import org.jboss.seam.RequiredException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.contexts.ServerConversationContext;

/**
 * A wrapper model for getting a SEAM object by its name. It should contemplate
 * all the cases of Injection
 * 
 * @author cruz
 * 
 */
public class SeamReadOnlyModel extends LoadableDetachableModel {
	private static final long serialVersionUID = 1L;

	private String name;
	private boolean create;
	private ScopeType scopeType;

	public SeamReadOnlyModel(String name) {
		this.name = name;
		create = true;
		scopeType = ScopeType.UNSPECIFIED;
	}

	public SeamReadOnlyModel(String name, boolean create) {
		this.name = name;
		this.create = create;
		scopeType = ScopeType.UNSPECIFIED;
	}

	public SeamReadOnlyModel(String name, boolean create, ScopeType scopeType) {
		this.name = name;
		this.create = create;
		this.scopeType = scopeType;
	}

	public SeamReadOnlyModel(String name, ScopeType scopeType) {
		this.name = name;
		this.create = true;
		this.scopeType = scopeType;
	}

	@Override
	protected Object load() {
		Object seamObject;
		ServerConversationContext context = (ServerConversationContext) Contexts.getConversationContext();
		context.unflush();

		if (scopeType == ScopeType.UNSPECIFIED)
			seamObject = Component.getInstance(name, create);
		else
			seamObject = Component.getInstance(name, scopeType, create);

		if (seamObject == null)
			throw new RequiredException("Couldn't find an instance '" + name
					+ "' of object");
		return seamObject;
	}
}
