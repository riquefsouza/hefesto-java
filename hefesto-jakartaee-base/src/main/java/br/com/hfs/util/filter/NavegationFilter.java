package br.com.hfs.util.filter;

import java.io.IOException;

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.hfs.ApplicationConfig;
import br.com.hfs.admin.vo.AuthenticatedUserVO;

public class NavegationFilter implements Filter {

	private static final String ERROR_SCREEN = "/authorizationDenied.xhtml";

	public static final String DESKTOP_SCREEN = "desktop.xhtml";

	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Inject
	private AuthenticatedUserVO authenticatedUser;

	@Inject
	private ApplicationConfig applicationConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jakarta.servlet.Filter#doFilter(jakarta.servlet.ServletRequest,
	 * jakarta.servlet.ServletResponse, jakarta.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();

		// log.info("URL: " + url);

		if (applicationConfig.isEnableProfileControl()) {

			if (authenticatedUser.hasPermission(url, DESKTOP_SCREEN)) {
				chain.doFilter(req, resp);
			} else {
				redirectToLogin(req, resp);
			}

		} else {
			chain.doFilter(req, resp);
		}

	}

	/**
	 * Redirect to login.
	 *
	 * @param req the req
	 * @param res the res
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
			res.setContentType("text/xml");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().printf(FACES_REDIRECT_XML, new Object[] { request.getContextPath() + ERROR_SCREEN });
		} else {
			String url = request.getContextPath() + ERROR_SCREEN;
			HttpServletResponse resHttp = (HttpServletResponse) res;
			resHttp.sendRedirect(url);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jakarta.servlet.Filter#init(jakarta.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jakarta.servlet.Filter#destroy()
	 */
	public void destroy() {
	}
}
