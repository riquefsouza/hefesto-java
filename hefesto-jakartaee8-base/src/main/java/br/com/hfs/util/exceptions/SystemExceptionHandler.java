package br.com.hfs.util.exceptions;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("deprecation")
@Named("exceptionHandler")
public class SystemExceptionHandler extends ExceptionHandlerWrapper {
	
	/** The log. */
	private Logger log = LogManager.getLogger(SystemExceptionHandler.class);

	/* (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable t = context.getException();
			
			FacesContext fc = FacesContext.getCurrentInstance();
			//Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			NavigationHandler nav = fc.getApplication().getNavigationHandler();

			HttpSession session = ((HttpServletRequest) fc.getExternalContext().getRequest()).getSession();
			session.setAttribute("errorType", t.getClass());
			session.setAttribute("errorCause", ExceptionUtils.getRootCauseMessage(t));
			session.setAttribute("errorRootCause", ExceptionUtils.getRootCause(t));
			session.setAttribute("errorMsg", t.getMessage());
			session.setAttribute("errorStackTrace", ExceptionUtils.getStackTrace(t));
			try {
				if (!testAncestorCause(t, ExpectedErrorException.class)) {
					this.log.error("Exception occurred: " + t.toString(), t);
					nav.handleNavigation(fc, null, "/error.xhtml?nocid=true");
					fc.getExternalContext()
							.redirect(fc.getExternalContext().getRequestContextPath() + "/error.xhtml?nocid=true");
				}
			} catch (Exception localException) {
			} finally {
				i.remove();
			}
		}
		getWrapped().handle();
	}

	/**
	 * Test ancestor cause.
	 *
	 * @param target
	 *            the target
	 * @param cause
	 *            the cause
	 * @return true, if successful
	 */
	private boolean testAncestorCause(Throwable target, Class<? extends Throwable> cause) {
		do {
			if (target.getClass() == cause) {
				return true;
			}
			target = target.getCause();
		} while (target.getCause() != null);
		return false;
	}

}
