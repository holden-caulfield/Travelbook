package ar.com.travelbook.wizard;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ar.com.travelbook.wizard.ItemTypeFactory.ItemType;


public class ChoicePanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private RadioGroup itemTypeRadioGroup;
	private Panel privatePanel;
	private Panel publicPanel;
	private Radio publicRadio;
	private Radio privateRadio;
	private WebMarkupContainer publicTab;
	private WebMarkupContainer privateTab;

	public ChoicePanel(String id, IModel model, Panel publicPanel, Panel privatePanel) {
		super(id, model);
		this.publicPanel = publicPanel;
		this.privatePanel = privatePanel;
		
		itemTypeRadioGroup = new RadioGroup("itemType", model);
		this.publicRadio = new Radio("public",new Model(ItemType.PUBLIC));
		this.privateRadio = new Radio("private",new Model(ItemType.PRIVATE));
		this.publicTab = new WebMarkupContainer("publicTab");
		itemTypeRadioGroup.add(this.publicTab);
		publicTab.add(this.publicRadio);
		this.privateTab = new WebMarkupContainer("privateTab");
		itemTypeRadioGroup.add(this.privateTab);
		this.privateTab.add(this.privateRadio);
		add(itemTypeRadioGroup);
		add(this.publicPanel);
		add(this.privatePanel);
		
		/* Formulario Tipo Alojamiento */
		itemTypeRadioGroup.add(new AjaxFormChoiceComponentUpdatingBehavior(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				((WizardPage)ChoicePanel.this.getPage()).updateAll(target);
			}
		});

		
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		if((ItemType)this.getModelObject() == ItemType.PUBLIC){
			publicTab.add(new AttributeModifier("class", true, new Model("active")));
			publicPanel.setVisible(true);
			privateTab.add(new AttributeModifier("class", true, new Model("")));
			privatePanel.setVisible(false);
		}else {
			privateTab.add(new AttributeModifier("class", true, new Model("active")));
			publicTab.add(new AttributeModifier("class", true, new Model("")));
			publicPanel.setVisible(false);
			privatePanel.setVisible(true);
		}
		itemTypeRadioGroup.clearInput();
		
	}
}
