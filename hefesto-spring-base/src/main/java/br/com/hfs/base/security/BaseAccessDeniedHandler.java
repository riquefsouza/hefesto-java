package br.com.hfs.base.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class BaseAccessDeniedHandler implements AccessDeniedHandler {

	public static final Logger log = LoggerFactory.getLogger(BaseAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
			throws IOException, ServletException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			log.warn("User: " + authentication.getName() + " attempted to access the protected URL: "
					+ request.getRequestURI());
		}

		response.sendRedirect(request.getContextPath() + "/403.html");
	}

}
