package br.com.hfs.util;

import java.io.Serializable;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionUtil implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static String getErrors(Logger log, Throwable e, String mensagem, boolean mostrarStackTrace) {
		String mens = "<br/>[Tipo]: " + e.getClass();
		mens += "<br/>[Causa]: " + ExceptionUtils.getRootCauseMessage(e);
		mens += "<br/>[Causa Raiz]: " + ExceptionUtils.getRootCause(e);
		mens += "<br/>[Mensagem]: " + e.getMessage();
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
