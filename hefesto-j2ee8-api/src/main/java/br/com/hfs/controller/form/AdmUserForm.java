package br.com.hfs.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.hfs.dao.AdmUserDAO;
import br.com.hfs.model.AdmUser;

public class AdmUserForm {

	@NotBlank
	private String login;

	@NotBlank
	private String password;
	
	@NotBlank
	private String name;

	@NotBlank
	private String email;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public AdmUser convert() {
		return new AdmUser(null, login, password, name, email);
	}

	public AdmUser update(AdmUser user, AdmUserDAO dao) {
		Optional<AdmUser> bean = dao.findById(user.getId());
		if (bean.isPresent()) {
			bean.get().setLogin(this.login);
			bean.get().setPassword(this.password);
			bean.get().setName(this.name);
			bean.get().setEmail(this.email);
			return bean.get();	
		}
		return null;
	}
}
