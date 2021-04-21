package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioVO.
 */
public class UserVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long id;

	/** The ip. */
	private String ip;

	/** The data. */
	private LocalDateTime date;

	/** The email. */
	private String email;

	/** The login. */
	private String login;

	/** The nome. */
	private String name;
	
	private String password;
	
	private Boolean active;
	
	private List<Long> admIdProfiles;
	
	private String userProfiles;
	
	private String currentPassword;
	
	private String newPassword;
	
	private String confirmNewPassword;

	/**
	 * Instantiates a new adm usuario.
	 */
	public UserVO() {
		super();
		clean();
	}

	public UserVO(Long id, String email, String login, String name, Boolean active) {
		super();
		this.id = id;
		this.email = email;
		this.login = login;
		this.name = name;
		this.active = active;
	}

	public void clean() {
		this.ip = "";
		this.date = LocalDateTime.now();
		this.email = "";
		this.login = "";
		this.name = "";
		this.password = "";
		this.active = false;
		this.admIdProfiles = new ArrayList<Long>();
		this.userProfiles = "";
		this.currentPassword = "";
		this.newPassword = "";
		this.confirmNewPassword = "";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Long> getAdmIdProfiles() {
		return admIdProfiles;
	}

	public void setAdmIdProfiles(List<Long> admIdProfiles) {
		this.admIdProfiles = admIdProfiles;
	}

	public String getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(String userProfiles) {
		this.userProfiles = userProfiles;
	}

}
