package ar.com.travelbook.wizard;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

@Name("ItemTypeFactory")
public class ItemTypeFactory {

	public enum ItemType {
		PUBLIC,
		PRIVATE;
	};
	
	@Factory(scope=ScopeType.CONVERSATION)
	public ItemType getActivityType(){
		return ItemType.PUBLIC;
	}
	
	@Factory(scope=ScopeType.CONVERSATION)
	public ItemType getTransportType(){
		return ItemType.PUBLIC;
	}
	
	@Factory(scope=ScopeType.CONVERSATION)
	public ItemType getAccomodationType(){
		return ItemType.PUBLIC;
	}
	
}
