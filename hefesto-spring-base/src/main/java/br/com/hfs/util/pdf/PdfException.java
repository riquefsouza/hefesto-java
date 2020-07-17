package br.com.hfs.util.pdf;

import org.apache.logging.log4j.Logger;

public class PdfException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new pdf exception.
	 *
	 * @param message
	 *            the message
	 */
	public PdfException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new pdf exception.
	 *
	 * @param log
	 *            the log
	 * @param message
	 *            the message
	 */
	public PdfException(Logger log, String message) {
		super(message);
		log.fatal(message);
	}

}
