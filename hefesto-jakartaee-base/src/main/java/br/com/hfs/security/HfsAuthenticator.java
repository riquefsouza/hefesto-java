package br.com.hfs.security;

import java.io.Serializable;

import org.apache.logging.log4j.Logger;

import br.com.hfs.ApplicationUtil;
import br.com.hfs.util.exceptions.ExceptionUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class HfsAuthenticator implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
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

	public void login() {
		this.identity.setLoggedIn(false);
		String usuario = this.identity.getUserId();
		String senha = this.identity.getPassword();
		try {
			boolean autenticacao = systemMB.authenticate(usuario, senha);
			if (autenticacao) {
				this.identity.setLoggedIn(true);
				//setStatus(Authenticator.AuthenticationStatus.SUCCESS);
				//setAccount(new User(usuario));
				redirectHomePage();
			} else {
				gerarMensagemInformativa("Invalid Username or Password.");
				//setStatus(Authenticator.AuthenticationStatus.FAILURE);
				//redirectPageLogin();
				return;
			}
		} catch (Exception e) {
			this.log.error("User authentication error", e);
			//gerarMensagemErro(e, e.getMessage());
			gerarMensagemInformativa("Invalid Username or Password.");
			//setStatus(Authenticator.AuthenticationStatus.FAILURE);
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

	/**
	 * Log out.
	 *
	 * @return the string
	 */
	public String logOut() {
		try {
			applicationUtil.removeAuthenticatedUser();
			
			//this.identity.logout();
			
			//systemView.logout();
			
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			//return "SUCCESS";
		} catch (Exception e) {
		}
		//return "ERROR";
		return "/login.xhtml?faces-redirect=true";
	}

}
