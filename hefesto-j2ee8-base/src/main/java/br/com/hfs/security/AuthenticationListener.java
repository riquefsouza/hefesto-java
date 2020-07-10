package br.com.hfs.security;

import javax.enterprise.event.Observes;

import org.omnifaces.util.Faces;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;
import org.picketlink.authentication.event.PostLoggedOutEvent;

public class AuthenticationListener {

	/**
	 * Handle logged in.
	 *
	 * @param event
	 *            the event
	 */
	public void handleLoggedIn(@Observes LoggedInEvent event) {
		//this.viewNavigationHandler.navigateTo(loggedInAccessDecisionVoter.getDeniedPage());
		Faces.redirect("/private/desktop.xhtml");
	}

	/**
	 * Handle failed.
	 *
	 * @param event
	 *            the event
	 */
	public void handleFailed(@Observes LoginFailedEvent event) {
		//this.viewNavigationHandler.navigateTo(Pages.Login.class);
		Faces.redirect("login.xhtml");
	}

	/**
	 * Handle logout.
	 *
	 * @param event
	 *            the event
	 */
	public void handleLogout(@Observes PostLoggedOutEvent event) {
		//this.viewNavigationHandler.navigateTo(Pages.Login.class);
		Faces.redirect("login.xhtml");
	}
	
}
