package br.com.hfs.util.zip;

import org.apache.logging.log4j.Logger;

import br.com.hfs.util.exceptions.ExceptionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ZipException.
 */
public class ZipException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param message
	 *            the message
	 */
	public ZipException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param causa
	 *            the causa
	 */
	public ZipException(Throwable causa) {
		super(causa);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param log
	 *            the log
	 * @param message
	 *            the message
	 * @param causa
	 *            the causa
	 */
	public ZipException(Logger log, String message, Throwable causa) {
		super(message, causa);
		ExceptionUtil.getErrors(log, this, message, true);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param log
	 *            the log
	 * @param message
	 *            the message
	 */
	public ZipException(Logger log, String message) {
		this(log, message, null);
	}
}
