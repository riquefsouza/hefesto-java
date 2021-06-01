package br.com.hfs;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.hfs.admin.vo.AuthenticatedUserVO;

@Service
public class ApplicationUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public ApplicationUtil() {
		super();
	}

	public HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession();		
	}
	
	public void setAuthenticatedUser(AuthenticatedUserVO vo){
		getSession().setAttribute("authenticatedUser", vo);
	}

	public AuthenticatedUserVO getAuthenticatedUser(){
		AuthenticatedUserVO usu = (AuthenticatedUserVO) getSession().getAttribute("authenticatedUser");
		return usu;
	}
	
	public void removeAuthenticatedUser(){
		getSession().removeAttribute("authenticatedUser");
	}

}
