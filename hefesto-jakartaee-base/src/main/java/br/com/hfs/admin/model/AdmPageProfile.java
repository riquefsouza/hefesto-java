package br.com.hfs.admin.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "ADM_PAGE_PROFILE", 
	uniqueConstraints = 
		@UniqueConstraint(columnNames={"PGL_PAG_SEQ", "PGL_PRF_SEQ"}))
@NamedQueries({
	@NamedQuery(name = "AdmPageProfile.deleteByProfile", query = "DELETE FROM AdmPageProfile fp WHERE fp.profileSeq = ?1"),
	@NamedQuery(name = "AdmPageProfile.deleteByCodPage", query = "DELETE FROM AdmPageProfile fp WHERE fp.pageSeq = ?1")
})	
public class AdmPageProfile implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id	
	@GenericGenerator(name = "ADM_PAGE_PROFILE_ID_GENERATOR",
	strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
    	@Parameter(name = "sequence_name", value = "ADM_PAGE_PROFILE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
	})		
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_PAGE_PROFILE_ID_GENERATOR")	
	@Column(name = "PGL_SEQ")
	private Long id;
	
	/** The cod usuario. */
	@Column(name = "PGL_PAG_SEQ", nullable=false)
	private Long pageSeq;

	/** The profile seq. */
	@Column(name = "PGL_PRF_SEQ", nullable=false)
	private Long profileSeq;	

	/* The adm profile. */
	// bi-directional many-to-one association to AdmProfile
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PGL_PRF_SEQ", nullable=false, insertable = false, updatable = false)
	private AdmProfile admProfile;

	/**
	 * Instantiates a new adm cargo profile.
	 */
	public AdmPageProfile() {
		clean();
	}
	
	public AdmPageProfile(Long pageSeq, Long profileSeq) {
		super();
		this.pageSeq = pageSeq;
		this.profileSeq = profileSeq;
	}

	/**
	 * Limpar.
	 */
	public void clean(){
		this.pageSeq = 0L;
		this.profileSeq = 0L;
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
	 * Gets the page seq.
	 *
	 * @return the page seq
	 */
	public Long getPageSeq() {
		return pageSeq;
	}

	/**
	 * Sets the page seq.
	 *
	 * @param pageSeq the new page seq
	 */
	public void setPageSeq(Long pageSeq) {
		this.pageSeq = pageSeq;
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

	/*
	 * Pega o the adm profile.
	 *
	 * @return o the adm profile
	 */
	public AdmProfile getAdmProfile() {
		return this.admProfile;
	}

	/**
	 * Atribui o the adm profile.
	 *
	 * @param admProfile
	 *            o novo the adm profile
	 */
	public void setAdmProfile(AdmProfile admProfile) {
		this.admProfile = admProfile;
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
		AdmPageProfile other = (AdmPageProfile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}