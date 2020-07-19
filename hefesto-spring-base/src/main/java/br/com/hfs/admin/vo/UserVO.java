package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

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

	/**
	 * Instantiates a new adm usuario.
	 */
	public UserVO() {
		super();
		clean();
	}

	public UserVO(Long id, String email, String login, String name) {
		super();
		this.id = id;
		this.email = email;
		this.login = login;
		this.name = name;
	}

	public void clean() {
		this.ip = "";
		this.date = LocalDateTime.now();
		this.email = "";
		this.login = "";
		this.name = "";
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

}