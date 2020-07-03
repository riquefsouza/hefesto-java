package br.com.hfs.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.model.AdmUser;

public class AdmUserDTO {

	private String login;

	private String password;

	public AdmUserDTO(AdmUser user) {
		this.login = user.getLogin();
		this.password = user.getPassword();
	}
	
	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public static List<AdmUserDTO> convert(List<AdmUser> admUsers) {
		return admUsers.stream().map(AdmUserDTO::new).collect(Collectors.toList());
	}

}
