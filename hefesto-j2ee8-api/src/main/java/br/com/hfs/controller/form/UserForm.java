package br.com.hfs.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.hfs.dao.UserDAO;
import br.com.hfs.model.User;

public class UserForm {

	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	@NotBlank
	private boolean active;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User convert() {
		return new User(userName, password, active);
	}

	public User update(User user, UserDAO dao) {
		Optional<User> bean = dao.findById(user);
		if (bean.isPresent()) {
			bean.get().setUserName(this.userName);
			bean.get().setPassword(this.password);
			bean.get().setActive(this.active);
			return bean.get();	
		}
		return null;
	}
}
