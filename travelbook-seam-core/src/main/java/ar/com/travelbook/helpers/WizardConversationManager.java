package ar.com.travelbook.helpers;

import java.io.Serializable;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Manager;

import ar.com.travelbook.domain.Travel;

/**
 * Switches conversation between destinations
 * 
 * @author cruz
 *
 */
@Name("wizardConversationManager")
public class WizardConversationManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	@In
	private Travel travel;
	@In
	private Manager manager;

	/**
	 * Switches destination conversation
	 * 
	 * @param posItem The selected conversation
	 */
	public void switchConversation(int posItem) {
		String nameConversation = "destino_"
				+ manager.getRootConversationId() + "_" + posItem;
		if (!manager.switchConversation(nameConversation)) {
			manager.switchConversation(manager.getRootConversationId());
			Conversation.instance().beginNested();
			manager.updateCurrentConversationId(nameConversation);
		}
		Contexts.getConversationContext().set("selectedItem",
				new Integer(posItem));
	}

}
