package br.com.hfs.util.exceptions;

import java.util.ArrayList;
import java.util.List;

public class UnexpectedErrorException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public UnexpectedErrorException(String message, Exception cause) {
		super(message, cause);
	}

	public UnexpectedErrorException(String message) {
		super(message);
	}

	public UnexpectedErrorException(Throwable e) {
		super(e);
	}

	public List<String> getMessages() {
		List<String> messages = new ArrayList<String>();
		messages.add(getMessage());
		return messages;
	}

}
