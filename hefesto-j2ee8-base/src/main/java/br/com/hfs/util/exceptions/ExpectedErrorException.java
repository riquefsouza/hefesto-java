package br.com.hfs.util.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ExpectedErrorException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private static final String SEPARATOR = ", ";
	
	private List<String> messages = new ArrayList<String>();

	public ExpectedErrorException() {
	}

	public ExpectedErrorException(String message) {
		this.messages.add(message);
	}

	public ExpectedErrorException(String message, Throwable cause) {
		super(cause);
		this.messages.add(message);
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public ExpectedErrorException adicionarMensagem(String message) {
		this.messages.add(message);
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		if (this.messages.size() > 0) {
			for (int i = 0; i < this.messages.size() - 1; i++) {
				String m = (String) this.messages.get(i);
				message.append(m);
				message.append(SEPARATOR);
			}
			message.append((String) this.messages.get(this.messages.size() - 1));
		}
		return message.toString();
	}

}
