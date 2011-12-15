package ar.com.travelbook.common;

import org.apache.wicket.model.PropertyModel;

/// TODO: SeamPropertyModel already exists in JBOSS, we should rename, or convince JBOSS-SEAM of replacing
public class ContextSeamPropertyModel extends PropertyModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a SeamPropertyModel with a SEAM object-name as a parameter
	 * 
	 * @param name
	 * @param expression
	 */
	public ContextSeamPropertyModel(String name, String expression) {
		super(new SeamReadOnlyModel(name), expression);
	}

	/**
	 * Creates a SeamPropertyModel attached to a SeamModel. Allows creation of object
	 * @param name Name of the object in the SEAM context
	 * @param expression Expression that access the field
	 * @param create if the object should be instantiated by SEAM
	 */
	public ContextSeamPropertyModel(String name, String expression, boolean create) {
		super(new SeamReadOnlyModel(name, create), expression);
	}
}
