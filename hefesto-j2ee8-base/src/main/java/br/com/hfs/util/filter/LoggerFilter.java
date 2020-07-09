package br.com.hfs.util.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerFilter implements Filter {
	
	/** The log. */
	@Inject
	protected Logger log;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fc) throws ServletException {
		this.log = LogManager.getLogger(LoggerFilter.class);
		this.log.info("Iniciando LoggerFilter");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String queryString = ((HttpServletRequest) request).getQueryString();
		boolean ignorar = (url.endsWith(".js")) || (url.endsWith(".css")) || (url.endsWith(".png"))
				|| (url.endsWith(".jpg"));
		if (!ignorar) {
			this.log.info("· IP Cliente: " + request.getRemoteAddr() + " | " + url + "?" + queryString);
		}
		chain.doFilter(request, response);
		if (!ignorar) {
			this.log.info("· Completado [" + url + "?" + queryString + "]");
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
