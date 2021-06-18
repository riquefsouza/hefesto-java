package br.com.hfs.util;

import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;

public class MessageBundleProvider {

	/** The bundle. */
	private ResourceBundle bundle;

	/**
	 * Pega o the bundle.
	 *
	 * @return o the bundle
	 */
	@Produces
	@MessageBundle
	public ResourceBundle getBundle() {
		if (this.bundle == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();
			bundle = ResourceBundle.getBundle("messages", locale);
		}
		return this.bundle;
	}
	
}
