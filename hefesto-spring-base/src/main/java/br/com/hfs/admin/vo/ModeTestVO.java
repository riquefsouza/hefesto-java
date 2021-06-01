package br.com.hfs.admin.vo;

import java.io.Serializable;

public class ModeTestVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ativo. */
	private Boolean active;
	
	/** The login. */
	private String login;
	
	/** The login virtual. */
	private String loginVirtual;
	
	public ModeTestVO() {
		super();
		this.active = false;
		this.login = "";
		this.loginVirtual = "";
	}

	public ModeTestVO(Boolean active, String login, String loginVirtual) {
		super();
		this.active = active;
		this.login = login;
		this.loginVirtual = loginVirtual;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the login virtual.
	 *
	 * @return the login virtual
	 */
	public String getLoginVirtual() {
		return loginVirtual;
	}

	/**
	 * Sets the login virtual.
	 *
	 * @param loginVirtual the new login virtual
	 */
	public void setLoginVirtual(String loginVirtual) {
		this.loginVirtual = loginVirtual;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((loginVirtual == null) ? 0 : loginVirtual.hashCode());
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
		ModeTestVO other = (ModeTestVO) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (loginVirtual == null) {
			if (other.loginVirtual != null)
				return false;
		} else if (!loginVirtual.equals(other.loginVirtual))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModeTestVO [active=" + active + ", login=" + login + ", loginVirtual=" + loginVirtual + "]";
	}

}
