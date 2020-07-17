package br.com.hfs.security;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.picketlink.Identity;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.Authenticator;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.Credentials;
import org.picketlink.idm.model.basic.User;

import br.com.hfs.ApplicationUtil;
import br.com.hfs.util.exceptions.ExceptionUtil;

@Named
@PicketLink
public class HfsAuthenticator extends BaseAuthenticator implements Authenticator, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
	/** The credentials. */
	@Inject
	private DefaultLoginCredentials credentials;
	
	/** The identity. */
	@Inject
	private Identity identity;
	
	@Inject
	private ApplicationUtil applicationUtil;
	
	@Inject
	private SystemMB systemMB;
	
	@Inject
	protected transient Logger log;
	
	/**
	 * Gerar mensagem erro.
	 *
	 * @param e
	 *            the e
	 * @param mensagem
	 *            the mensagem
	 */
	public void gerarMensagemErro(Exception e, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ExceptionUtil.getErrors(log, e, mensagem)));
	}

	/**
	 * Gerar mensagem informativa.
	 *
	 * @param mensagem
	 *            the mensagem
	 */
	public void gerarMensagemInformativa(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensagem));
	}

	/* (non-Javadoc)
	 * @see org.picketlink.authentication.Authenticator#authenticate()
	 */
	public void authenticate() {
		String usuario = this.credentials.getUserId();
		String senha = this.credentials.getPassword();
		try {
			boolean autenticacao = systemMB.authenticate(usuario, senha);
			//boolean autenticacao = true;
			if (autenticacao) {
				setStatus(Authenticator.AuthenticationStatus.SUCCESS);
				setAccount(new User(usuario));
			} else {
				gerarMensagemInformativa("Invalid Username or Password.");
				setStatus(Authenticator.AuthenticationStatus.FAILURE);
				redirectPageLogin();
			}
		} catch (Exception e) {
			this.log.error("User authentication error", e);
			//gerarMensagemErro(e, e.getMessage());
			gerarMensagemInformativa("Invalid Username or Password.");
			setStatus(Authenticator.AuthenticationStatus.FAILURE);
			redirectPageLogin();
		}
	}

	/**
	 * Redireciona pagina login.
	 */
	private void redirectPageLogin() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, "LOGIN_PAGE");
	}

	/**
	 * Redireciona pagina inicial.
	 */
	public void redirectHomePage() {
		if (this.identity.isLoggedIn()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			NavigationHandler nav = fc.getApplication().getNavigationHandler();
			nav.handleNavigation(fc, null, "LOGIN_HOME_PAGE");
		}
	}

	/* (non-Javadoc)
	 * @see org.picketlink.authentication.BaseAuthenticator#postAuthenticate()
	 */
	public void postAuthenticate() {
	}

	/**
	 * Log out.
	 *
	 * @return the string
	 */
	public String logOut() {
		try {
			applicationUtil.removeAuthenticatedUser();
			
			this.identity.logout();

			//systemView.logout();
			
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "SUCCESS";
		} catch (Exception e) {
		}
		return "ERROR";
	}

	/**
	 * Pega o the credentials.
	 *
	 * @return o the credentials
	 */
	public Credentials getCredentials() {
		return this.credentials;
	}

	/**
	 * Atribui o the credentials.
	 *
	 * @param credentials
	 *            o novo the credentials
	 */
	public void setCredentials(DefaultLoginCredentials credentials) {
		this.credentials = credentials;
	}

}
