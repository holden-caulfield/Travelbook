package ar.com.travelbook.common;

import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Creates a compoundPropertyModel with a SEAM object,
 * It can be created in the context
 * It should contemplate all the Injection cases
 * @author cruz
 *
 */
public class SeamCompoundPropertyModel extends CompoundPropertyModel {
	private static final long serialVersionUID = 1L;
	
	public SeamCompoundPropertyModel(String name) {
		super(new SeamReadOnlyModel(name));
	}
	
	public SeamCompoundPropertyModel(String name, boolean create) {
		super(new SeamReadOnlyModel(name,create));
	}


}
