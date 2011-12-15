package ar.com.travelbook.common;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Log;
import org.jboss.seam.transaction.Transaction;

/**
 * Wicket Phase Listener.
 * Opens and closes the transaction for each request
 * 
 * @author cruz
 *
 */
@Name("wicketPhaseListener")
public class WicketPhaseListener {
	
	@Logger private Log log;

	@Observer("org.jboss.seam.wicket.beforeRequest")
	public void start() {
	      try 
	      {
	         if ( !Transaction.instance().isActiveOrMarkedRollback() )
	         {
	            log.debug("beginning transaction beforeRequest");
	            Transaction.instance().begin();
	         }
	      }
	      catch (Exception e)
	      {
	         throw new IllegalStateException("Could not start transaction", e);
	      }
	}

	@Observer("org.jboss.seam.wicket.afterRequest")
	public void end() {
		try {
			if (Transaction.instance().isActive()) {
				try {
					log.debug("committing transaction afterRequest");
					Transaction.instance().commit();

				} catch (IllegalStateException e) {
					log.warn("TX commit failed with illegal state exception. This may be "
						+ "because the tx timed out and was rolled back in the background.",
						e);
				}
			} else if (Transaction.instance().isRolledBackOrMarkedRollback()) {
				log.debug("rolling back transaction afterRequest");
				Transaction.instance().rollback();
			}

		} catch (Exception e) {
			throw new IllegalStateException("Could not commit transaction", e);
		}
	}
}
