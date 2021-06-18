/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.util.ldap;

import org.apache.logging.log4j.Logger;

import br.com.hfs.util.exceptions.ExceptionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class LdapConfiguracaoException.
 */
public class LdapConfigurationException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ldap configuracao exception.
	 *
	 * @param mensagem
	 *            the mensagem
	 */
	public LdapConfigurationException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Instantiates a new ldap configuracao exception.
	 *
	 * @param causa
	 *            the causa
	 */
	public LdapConfigurationException(Throwable causa) {
		super(causa);
	}

	/**
	 * Instantiates a new ldap configuracao exception.
	 *
	 * @param log
	 *            the log
	 * @param mensagem
	 *            the mensagem
	 * @param causa
	 *            the causa
	 */
	public LdapConfigurationException(Logger log, String mensagem, Throwable causa) {
		super(mensagem, causa);
		ExceptionUtil.getErrors(log, this, mensagem, true);
	}

	/**
	 * Instantiates a new ldap configuracao exception.
	 *
	 * @param log
	 *            the log
	 * @param mensagem
	 *            the mensagem
	 */
	public LdapConfigurationException(Logger log, String mensagem) {
		this(log, mensagem, null);
	}

}
