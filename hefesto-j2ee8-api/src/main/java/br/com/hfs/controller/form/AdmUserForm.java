package br.com.hfs.controller.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import br.com.hfs.dao.AdmUserDAO;
import br.com.hfs.model.AdmUser;

public class AdmUserForm {
	
	private Long id;

	@NotBlank
	private String login;

	@NotBlank
	private String password;
		
	private String name;

	private String email;
	
	public AdmUserForm() {
		super();
	}

	public AdmUserForm(AdmUser obj) {
		this.id = obj.getId();
		this.email = obj.getEmail();
		this.login = obj.getLogin();
		this.name = obj.getName();
		this.password = obj.getPassword();
	}
	
	public AdmUser convert() {
		return new AdmUser(login, password);
	}

	public AdmUser update(Long id, AdmUserDAO dao) {
		Optional<AdmUser> bean = dao.findById(id);
		if (bean.isPresent()) {
			bean.get().setLogin(this.login);
			bean.get().setPassword(this.password);
			bean.get().setName(this.name);
			bean.get().setEmail(this.email);
			return bean.get();	
		}
		return null;
	}

	public static List<AdmUserForm> convert(List<AdmUser> admUsers) {
		return admUsers.stream().map(AdmUserForm::new).collect(Collectors.toList());
	}
	
	public static List<AdmUser> convertFromForm(List<AdmUserForm> admUsers) {
		return admUsers.stream().map(AdmUser::new).collect(Collectors.toList());
	}
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
