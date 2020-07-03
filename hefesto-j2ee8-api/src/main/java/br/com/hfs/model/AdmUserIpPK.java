package br.com.hfs.model;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class AdmUserIpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The userSeq. */
	@Column(name = "UIP_USU_SEQ")
	private Long userSeq;

	/** The ip. */
	@Column(name = "UIP_IP")
	private String ip;

	/**
	 * Instantiates a new adm user PK.
	 */
	public AdmUserIpPK() {
		super();
		limpar();
	}

	/**
	 * Limpar.
	 */
	public void limpar() {
		this.userSeq = 0L;
		this.ip = "";
	}

	/**
	 * Gets the user seq.
	 *
	 * @return the user seq
	 */
	public Long getUserSeq() {
		return userSeq;
	}

	/**
	 * Sets the user seq.
	 *
	 * @param userSeq
	 *            the new user seq
	 */
	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdmUserIpPK)) {
			return false;
		}
		AdmUserIpPK castOther = (AdmUserIpPK) other;
		return (this.userSeq == castOther.userSeq) && this.ip.equals(castOther.ip);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.userSeq ^ (this.userSeq >>> 32)));
		hash = hash * prime + this.ip.hashCode();

		return hash;
	}
}