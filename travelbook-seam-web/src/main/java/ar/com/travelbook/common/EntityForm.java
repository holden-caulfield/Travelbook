package ar.com.travelbook.common;

import java.lang.reflect.Field;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponent.AbstractVisitor;
import org.apache.wicket.model.IModel;
import org.hibernate.validator.NotNull;
import org.jboss.seam.wicket.ModelValidator;

public class EntityForm extends Form {
	private static final long serialVersionUID = 1L;

	public EntityForm(String id) {
		super(id);
	}

	public EntityForm(String id, IModel model) {
		super(id, model);
	}

	public void addEntityValidators() {
		final Object modelObject = EntityForm.this.getModelObject();
		if(modelObject==null)
			return;
		visitFormComponentsPostOrder(new AbstractVisitor() {
			
			@Override
			protected void onFormComponent(FormComponent formComponent) {
				String fieldName = formComponent.getInputName();
				Field property;
				property = findField(modelObject.getClass(),fieldName);
				if(property==null)
					return;
				ModelValidator validatorItem = new ModelValidator(
					modelObject.getClass(), fieldName);
				formComponent.add(validatorItem);
				if (property.getAnnotation(NotNull.class) != null) {
					formComponent.setRequired(true);
				}
			}
		});
	}
	
	private Field findField(Class<? extends Object> clazz, String name){
		for (Field fieldItem : clazz.getDeclaredFields()) {
			if(fieldItem.getName().equals(name))
				return fieldItem;
		}
		if(clazz.getSuperclass()!=null)
			return findField(clazz.getSuperclass(),name);
		return null;
	}
}
