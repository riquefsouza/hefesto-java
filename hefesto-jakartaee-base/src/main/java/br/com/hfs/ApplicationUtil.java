package br.com.hfs;

import java.io.Serializable;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import br.com.hfs.admin.vo.AuthenticatedUserVO;

@Named
public class ApplicationUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public ApplicationUtil() {
		super();
	}

	public HttpSession getSession() {
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return hsr.getSession();
	}
	
	public void setAuthenticatedUser(AuthenticatedUserVO usu){
		getSession().setAttribute("authenticatedUser", usu);
	}
	
	public AuthenticatedUserVO getAuthenticatedUser(){
		AuthenticatedUserVO usu = (AuthenticatedUserVO) getSession().getAttribute("authenticatedUser");
		return usu;
	}
	
	public void removeAuthenticatedUser(){
		getSession().removeAttribute("authenticatedUser");
	}
}
