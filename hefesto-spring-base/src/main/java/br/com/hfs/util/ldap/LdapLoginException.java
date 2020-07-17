package br.com.hfs.util.ldap;

import org.apache.logging.log4j.Logger;

import br.com.hfs.util.exceptions.ExceptionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class LdapLoginException.
 */
public class LdapLoginException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param mensagem
	 *            the mensagem
	 */
	public LdapLoginException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param causa
	 *            the causa
	 */
	public LdapLoginException(Throwable causa) {
		super(causa);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param log
	 *            the log
	 * @param mensagem
	 *            the mensagem
	 * @param causa
	 *            the causa
	 */
	public LdapLoginException(Logger log, String mensagem, Throwable causa) {
		super(mensagem, causa);
		ExceptionUtil.getErrors(log, this, mensagem, true);
	}

	/**
	 * Instantiates a new ldap login exception.
	 *
	 * @param log
	 *            the log
	 * @param mensagem
	 *            the mensagem
	 */
	public LdapLoginException(Logger log, String mensagem) {
		this(log, mensagem, null);
	}
}
