package br.com.hfs.util.exceptions;

import javax.faces.context.ExceptionHandlerFactory;

@SuppressWarnings("deprecation")
public class SystemExceptionHandlerFactory extends ExceptionHandlerFactory {

	public SystemExceptionHandler getExceptionHandler() {
		return new SystemExceptionHandler();
	}

}
