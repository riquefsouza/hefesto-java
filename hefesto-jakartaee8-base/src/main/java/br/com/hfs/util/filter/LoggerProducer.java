package br.com.hfs.util.filter;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerProducer {

	@Produces
	public Logger getLogger(InjectionPoint p) {
		return LogManager.getLogger(p.getClass().getCanonicalName());
	}
	
}
