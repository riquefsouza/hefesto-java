package br.com.hfs.util.exceptions;

import org.apache.logging.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class TransacaoException.
 */
public class TransactionException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new transacao exception.
	 *
	 * @param mensagem
	 *            the mensagem
	 */
	public TransactionException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Instantiates a new transacao exception.
	 *
	 * @param causa
	 *            the causa
	 */
	public TransactionException(Throwable causa) {
		super(causa);
	}

	/**
	 * Instantiates a new transacao exception.
	 *
	 * @param log
	 *            the log
	 * @param mensagem
	 *            the mensagem
	 * @param causa
	 *            the causa
	 */
	public TransactionException(Logger log, String mensagem, Throwable causa) {
		super(mensagem, causa);
		ExceptionUtil.getErrors(log, this, mensagem, true);
	}

}
