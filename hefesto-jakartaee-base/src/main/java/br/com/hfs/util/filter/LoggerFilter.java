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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerFilter implements Filter {
	
	/** The log. */
	@Inject
	protected Logger log;

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#init(jakarta.servlet.FilterConfig)
	 */
	public void init(FilterConfig fc) throws ServletException {
		this.log = LogManager.getLogger(LoggerFilter.class);
		this.log.info("Getting Started LoggerFilter");
	}

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#doFilter(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse, jakarta.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String queryString = ((HttpServletRequest) request).getQueryString();
		boolean ignorar = (url.endsWith(".js")) || (url.endsWith(".css")) || (url.endsWith(".png"))
				|| (url.endsWith(".jpg"));
		if (!ignorar) {
			this.log.info("· IP Client: " + request.getRemoteAddr() + " | " + url + "?" + queryString);
		}
		chain.doFilter(request, response);
		if (!ignorar) {
			this.log.info("· Completed [" + url + "?" + queryString + "]");
		}
	}

	/* (non-Javadoc)
	 * @see jakarta.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
