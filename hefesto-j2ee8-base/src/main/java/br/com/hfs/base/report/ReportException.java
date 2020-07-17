package br.com.hfs.base.report;

import org.apache.logging.log4j.Logger;

import br.com.hfs.util.exceptions.ExceptionUtil;

public class ReportException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public ReportException(String message) {
		super(message);
	}

	public ReportException(Throwable causa) {
		super(causa);
	}

	public ReportException(Logger log, String message, Throwable causa) {
		super(message, causa);
		ExceptionUtil.getErrors(log, this, message, true);
	}

}
