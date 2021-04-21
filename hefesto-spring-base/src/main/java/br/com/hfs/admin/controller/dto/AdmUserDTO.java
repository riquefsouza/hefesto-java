package br.com.hfs.admin.controller.dto;

import java.util.ArrayList;
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

	private List<Long> admIdProfiles;
	
	private Boolean active;
	
	private String userProfiles;
	
	private String currentPassword;
	
	private String newPassword;
	
	private String confirmNewPassword;

	public AdmUserDTO() {
		super();
		this.admIdProfiles = new ArrayList<Long>();
	}

	public AdmUserDTO(AdmUser obj) {
		this.id = obj.getId();
		this.email = obj.getEmail();
		this.login = obj.getLogin();
		this.name = obj.getName();
		this.password = obj.getPassword();
		this.active = obj.getActive();
		this.admIdProfiles = obj.getAdmIdProfiles();
	    this.userProfiles = obj.getUserProfiles();
	    this.currentPassword = "";
	    this.newPassword = "";
	    this.confirmNewPassword = "";
		
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

	public List<Long> getAdmIdProfiles() {
		return admIdProfiles;
	}

	public Boolean getActive() {
		return active;
	}

	public String getUserProfiles() {
		return userProfiles;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
}
