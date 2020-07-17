package br.com.hfs.util.exceptions;

import java.io.Serializable;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionUtil implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static String getErrors(Logger log, Throwable e, String mensagem, boolean mostrarStackTrace) {
		String mens = "<br/>[Type]: " + e.getClass();
		mens += "<br/>[Cause]: " + ExceptionUtils.getRootCauseMessage(e);
		mens += "<br/>[Root cause]: " + ExceptionUtils.getRootCause(e);
		mens += "<br/>[Message]: " + e.getMessage();
		if (mostrarStackTrace) {
			mens += "<br/>[StackTrace]: " + ExceptionUtils.getStackTrace(e);
		}
		log.error(mensagem + "\n" + mens);
		return mensagem + "<br/>" + mens;
	}

	public static String getErrors(Logger log, Throwable e, String mensagem) {
		return getErrors(log, e, mensagem, false);
	}

}
