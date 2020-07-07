package br.com.hfs.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdmUserProfilePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cod usuario. */
	@Column(name = "USP_USE_SEQ")
	private Long userSeq;

	/** The profile seq. */
	@Column(name = "USP_PRF_SEQ", insertable = false, updatable = false)
	private Long profileSeq;

	/**
	 * Instantiates a new adm usuario profile PK.
	 */
	public AdmUserProfilePK() {
		super();
		clean();
	}

	/**
	 * Limpar.
	 */
	public void clean() {
		this.userSeq = 0L;
		this.profileSeq = 0L;
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
	 * @param userSeq the new user seq
	 */
	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	/**
	 * Pega o the profile seq.
	 *
	 * @return o the profile seq
	 */
	public Long getProfileSeq() {
		return profileSeq;
	}

	/**
	 * Atribui o the profile seq.
	 *
	 * @param profileSeq
	 *            o novo the profile seq
	 */
	public void setProfileSeq(Long profileSeq) {
		this.profileSeq = profileSeq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdmUserProfilePK)) {
			return false;
		}
		AdmUserProfilePK castOther = (AdmUserProfilePK) other;
		return (this.userSeq == castOther.userSeq) && (this.profileSeq == castOther.profileSeq);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.userSeq ^ (this.userSeq >>> 32)));
		hash = hash * prime + ((int) (this.profileSeq ^ (this.profileSeq >>> 32)));

		return hash;
	}


}