package br.com.hfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.hfs.converter.BooleanToStringConverter;

@Entity
@Table(name = "ADM_USER_IP")
public class AdmUserIp implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private AdmUserIpPK id;

	/** The active. */
	@Convert(converter=BooleanToStringConverter.class)
	@Column(name = "UIP_ATIVO")
	private Boolean active;
	
	/** The adm profile. */
	// bi-directional many-to-one association to AdmUser
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "UIP_USU_SEQ", nullable=false, insertable = false, updatable = false)
	private AdmUser admUser;

	/**
	 * Instantiates a new adm user ip.
	 */
	public AdmUserIp() {
		super();
		limpar();
	}

	/**
	 * Limpar.
	 */
	public void limpar() {
		this.id = new AdmUserIpPK();
		//this.admUser = new AdmUser();
		this.active = false;
	}

	/**
	 * Pega o the id.
	 *
	 * @return o the id
	 */
	public AdmUserIpPK getId() {
		return this.id;
	}

	/**
	 * Atribui o the id.
	 *
	 * @param id
	 *            o novo the id
	 */
	public void setId(AdmUserIpPK id) {
		this.id = id;
	}

	/**
	 * Pega o the active.
	 *
	 * @return o the active
	 */
	public Boolean getActive() {
		return this.active;
	}

	/**
	 * Atribui o the active.
	 *
	 * @param active
	 *            o novo the active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	/**
	 * Pega o the adm user.
	 *
	 * @return o the adm user
	 */
	public AdmUser getAdmUser() {
		return this.admUser;
	}

	/**
	 * Atribui o the adm user.
	 *
	 * @param admUser
	 *            o novo the adm user
	 */
	public void setAdmUser(AdmUser admUser) {
		this.admUser = admUser;
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
		AdmUserIp other = (AdmUserIp) obj;
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
		return id.getIp();
	}

}