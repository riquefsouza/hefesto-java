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

	public AdmUser convert() {
		return new AdmUser(null, login, password);
	}

	public AdmUser update(AdmUser user, AdmUserDAO dao) {
		Optional<AdmUser> bean = dao.findById(user.getId());
		if (bean.isPresent()) {
			bean.get().setLogin(this.login);
			bean.get().setPassword(this.password);
			return bean.get();	
		}
		return null;
	}
}
