package br.com.hfs.util.clientip;

import javax.servlet.http.HttpServletRequest;

public interface IRequestService {
	
	String getClientIp(HttpServletRequest request);
	
}
