package br.com.hfs;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Named
@RequestScoped
public class MessageBean {

	@Inject
	@ConfigProperty(name = "message")
	private String message;

	public String getMessage() {
		return this.message;
	}
}