package br.com.hfs.util.filter;

import java.io.IOException;

import br.com.hfs.security.Identity;
import br.com.hfs.util.CDIUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
	
	/** The Constant LOGIN. */
	private static final String LOGIN = "/login.xhtml";
	
	/** The Constant FACES_REDIRECT_XML. */
	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#doFilter(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse, jakarta.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		Identity identity = (Identity) CDIUtil.lookup(Identity.class);
		if ((identity == null) || (!identity.isLoggedIn())) {
			redirectToLogin(req, resp);
		} else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * Redirect to login.
	 *
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
			res.setContentType("text/xml");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().printf(FACES_REDIRECT_XML, new Object[] { request.getContextPath() + LOGIN });
		} else {
			String url = request.getContextPath() + LOGIN;
			HttpServletResponse resHttp = (HttpServletResponse) res;
			resHttp.sendRedirect(url);
		}
	}

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#init(jakarta.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
