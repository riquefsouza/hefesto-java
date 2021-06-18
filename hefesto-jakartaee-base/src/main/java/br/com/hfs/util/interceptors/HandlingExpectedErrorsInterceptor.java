package br.com.hfs.util.interceptors;

import java.io.Serializable;

import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.OptimisticLockException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.hfs.util.exceptions.ExpectedErrorException;
import br.com.hfs.util.exceptions.UnexpectedErrorException;

@Interceptor
@HandlingExpectedErrors
public class HandlingExpectedErrorsInterceptor implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Logger log = LogManager.getLogger(HandlingExpectedErrorsInterceptor.class);

	@AroundInvoke
	public Object tratamentoDeErrosEsperados(InvocationContext invocation) throws Exception {
		try {
			return invocation.proceed();
		} catch (ExpectedErrorException e) {
			for (String errorMessage : e.getMessages()) {
				this.log.debug("Expected error occurred: [" + errorMessage + "] in method: "
						+ invocation.getMethod().getName());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMessage));
			}
			return null;
		} catch (EJBException e) {
			boolean foundExpectedErrorException = false;

			Object causa = e;
			while ((causa = (Exception) ((Exception) causa).getCause()) != null) {
				if ((causa instanceof ExpectedErrorException)) {
					for (String errorMessage : ((ExpectedErrorException) causa).getMessages()) {
						foundExpectedErrorException = true;
						this.log.debug("Expected error occurred: [" + errorMessage + "] in method: "
								+ invocation.getMethod().getName());
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMessage));
					}
				} else {
					if ((causa instanceof OptimisticLockException)) {
						String errorMessage = "This record has been updated or deleted by another user. Please return to the initial system screen and repeat an operation.";
						this.log.debug(errorMessage);
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, null, errorMessage));

						return null;
					}
				}
			}
			if (foundExpectedErrorException) {
				return null;
			}
			throw e;
		} catch (Exception e) {
			this.log.error(e);
			throw new UnexpectedErrorException(e);
		}
	}
}
