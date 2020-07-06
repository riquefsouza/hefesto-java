package br.com.hfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.com.hfs.controller.form.AdmUserForm;
import br.com.hfs.vo.UserVO;

@Entity
@Table(name = "ADM_USER")

@NamedQueries({
	@NamedQuery(name = "AdmUser.findByLogin", query = "SELECT DISTINCT a FROM AdmUser a WHERE a.login=?1"),
	@NamedQuery(name = "AdmUser.login", query = "SELECT a FROM AdmUser a WHERE a.login=?1 AND a.password=?2")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "AdmUser.findIPByOracle", query = "SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS', 15) FROM DUAL"),
	@NamedNativeQuery(name = "AdmUser.findIPByPostgreSQL", query = "SELECT substr(CAST(inet_client_addr() AS VARCHAR),1,strpos(CAST(inet_client_addr() AS VARCHAR),'/')-1)"),
	@NamedNativeQuery(name = "AdmUser.setLoginPostgreSQL", query = "select set_config('myvars.usuario_login', ?1, false)" ),
	@NamedNativeQuery(name = "AdmUser.setIPPostgreSQL", query = "select set_config('myvars.usuario_ip', ?1, false)")
})
/*
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "AdmUser.setOracleLoginAndIP", procedureName = "pkg_adm.setar_usuario_ip",
				// resultClasses = { LoginModel.class },
				parameters = { @StoredProcedureParameter(name = "login", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "password", type = String.class, mode = ParameterMode.IN) })

})
*/
public class AdmUser implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id	
	@GenericGenerator(name = "ADM_USER_ID_GENERATOR",
	strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
    	@Parameter(name = "sequence_name", value = "ADM_USER_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
	})		
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_USER_ID_GENERATOR")	
	@Column(name = "USU_SEQ")
	private Long id;

	/** The email. */
	//@NotEmpty
	@Size(min=4, max=255)
	@Email
	@Column(name = "USU_EMAIL", length = 255)
	private String email;

	/** The login. */
	@NotEmpty
	@Size(min=4, max=64)
	@Column(name = "USU_LOGIN", nullable = false, length = 64)
	private String login;

	/** The name. */
	//@NotEmpty
	@Size(min=4, max=64)
	@Column(name = "USU_NAME", length = 64)
	private String name;

	/** The password. */
	//@JsonIgnore
	@NotEmpty
	@Size(min=4, max=128)
	@Column(name = "USU_PASSWORD", nullable = false, length = 128)
	private String password;
    
	/**
	 * Instantiates a new adm usuario.
	 */
	public AdmUser() {
		super();
		clean();
	}

	public AdmUser(String login, String password) {
		super();
		//this.id = id;
		this.login = login;
		this.password = password;
	}
		
	public AdmUser(UserVO vo) {
		this.id = vo.getId();
		this.email = vo.getEmail();
		this.login = vo.getLogin();
		this.name = vo.getName();
		//this.password = vo.ge;
	}
	
	public AdmUser(AdmUserForm vo) {
		this.id = vo.getId();
		this.email = vo.getEmail();
		this.login = vo.getLogin();
		this.name = vo.getName();
		this.password = vo.getPassword();
	}
	
	public void clean() {
		this.id = 0L;
		this.email = "";
		this.login = "";
		this.name = "";
		this.password = "";
	}

	/**
	 * Pega o the id.
	 *
	 * @return o the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Atribui o the id.
	 *
	 * @param id
	 *            o novo the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Pega o the email.
	 *
	 * @return o the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Atribui o the email.
	 *
	 * @param email
	 *            o novo the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Pega o the login.
	 *
	 * @return o the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Atribui o the login.
	 *
	 * @param login
	 *            o novo the login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Pega o the name.
	 *
	 * @return o the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Atribui o the name.
	 *
	 * @param name
	 *            o novo the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdmUser other = (AdmUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public UserVO toUserVO() {
		UserVO u = new UserVO();

		u.setId(this.id);
		//u.setIp(this.ip);
		u.setEmail(email);
		u.setLogin(login);
		u.setName(name);
		
		return u;
	}

 }