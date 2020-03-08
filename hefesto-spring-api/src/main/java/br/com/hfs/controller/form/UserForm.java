package br.com.hfs.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.hfs.model.User;
import br.com.hfs.repository.UserRepository;

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

	public User update(Long id, UserRepository userRepository) {
		User user = userRepository.getOne(id);
		user.setUserName(this.userName);
		user.setPassword(this.password);
		user.setActive(this.active);
		return user;
	}
}
