package br.com.hfs.admin.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.admin.model.AdmUser;

public class AdmUserDTO {

	private Long id;
	
	private String email;

	private String login;

	private String name;

	private String password;

	public AdmUserDTO() {
		super();
	}

	public AdmUserDTO(AdmUser obj) {
		this.id = obj.getId();
		this.email = obj.getEmail();
		this.login = obj.getLogin();
		this.name = obj.getName();
		this.password = obj.getPassword();
	}

	public static List<AdmUserDTO> convert(List<AdmUser> admUsers) {
		return admUsers.stream().map(AdmUserDTO::new).collect(Collectors.toList());
	}
	
	public static Page<AdmUserDTO> convert(Page<AdmUser> pg) {
		return pg.map(AdmUserDTO::new);
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}
}
