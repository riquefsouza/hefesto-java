package br.com.hfs.admin.vo;

import java.io.Serializable;

public class TestModeVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ativo. */
	private Boolean active;
	
	/** The login. */
	private String login;
	
	/** The login virtual. */
	private String loginVirtual;
	
	public TestModeVO() {
		super();
		this.active = false;
		this.login = "";
		this.loginVirtual = "";
	}

	public TestModeVO(Boolean active, String login, String loginVirtual) {
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLoginVirtual() {
		return loginVirtual;
	}

	public void setLoginVirtual(String loginVirtual) {
		this.loginVirtual = loginVirtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((loginVirtual == null) ? 0 : loginVirtual.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestModeVO other = (TestModeVO) obj;
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

	@Override
	public String toString() {
		return "TestModeVO [active=" + active + ", login=" + login + ", loginVirtual=" + loginVirtual + "]";
	}

}
