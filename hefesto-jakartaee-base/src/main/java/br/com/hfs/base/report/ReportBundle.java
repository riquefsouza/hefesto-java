package br.com.hfs.base.report;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import jakarta.faces.context.FacesContext;

public class ReportBundle  implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bundle. */
	private static ResourceBundle bundle;

	/**
	 * Pega o the bundle.
	 *
	 * @return o the bundle
	 */
	public static ResourceBundle getBundle() {
		if (bundle == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();
			bundle = ResourceBundle.getBundle("report", locale);
		}
		return bundle;
	}

	/**
	 * Gets the string params.
	 *
	 * @param string
	 *            the string
	 * @param params
	 *            the params
	 * @return the string params
	 */
	private static String getStringParams(String string, Object... params) {
		String result = null;
		if (string != null) {
			result = new String(string);
		}
		if ((params != null) && (string != null)) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					result = result.replaceAll("\\{" + i + "\\}", Matcher.quoteReplacement(params[i].toString()));
				}
			}
		}
		return result;
	}

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @param params
	 *            the params
	 * @return the string
	 */
	public static String getString(String key, String params) {
		return getStringParams(getBundle().getString(key), params);
	}

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String getString(String key) {
		return getBundle().getString(key);
	}

}
