package br.com.hfs.util.filter;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerProducer {

	@Produces
	public Logger getLogger(InjectionPoint p) {
		return LogManager.getLogger(p.getClass().getCanonicalName());
	}
	
}
