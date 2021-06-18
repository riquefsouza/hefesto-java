package br.com.hfs.admin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.hfs.admin.controller.form.AdmPageForm;
import br.com.hfs.admin.vo.PageVO;

@Entity
@Table(name="ADM_PAGE")
@NamedQueries({
	@NamedQuery(name = "AdmPage.getDescriptionById", query = "SELECT c.url FROM AdmPage c WHERE c.id = ?1"),
	@NamedQuery(name = "AdmPage.countNovo", query = "SELECT COUNT(c) FROM AdmPage c WHERE LOWER(c.url) = ?1"),
	@NamedQuery(name = "AdmPage.countAntigo", query = "SELECT COUNT(c) FROM AdmPage c WHERE LOWER(c.url) <> ?1 AND LOWER(c.url) = ?2"),	
	@NamedQuery(name = "AdmPage.findProfilesByPage", query="SELECT distinct p FROM AdmPage pag inner join pag.admProfiles p where pag = ?1")
})
public class AdmPage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id	
	@GenericGenerator(name = "ADM_PAGE_ID_GENERATOR",
	strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
    	@Parameter(name = "sequence_name", value = "ADM_PAGE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
	})		
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_PAGE_ID_GENERATOR")	
	@Column(name="PAG_SEQ")
	private Long id;

	/** The description. */
	@NotNull
	@NotBlank
	@NotEmpty
	@Size(min=4, max=255)
	@Column(name="PAG_DESCRIPTION", unique = true, nullable = false, length = 255)
	private String description;

	/** The url. */
	@NotNull
	@NotBlank
	@NotEmpty	
	@Size(min=4, max=255)
	@Column(name="PAG_URL", unique = true, nullable = false, length = 255)
	private String url;

	/** The adm profiles. */ 
	//bi-directional many-to-many association to AdmProfile
	//@ManyToMany(mappedBy="admPages", fetch = FetchType.LAZY) //, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JsonIgnore
	//@JsonSerialize(using = AdmProfileSetSerializer.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ADM_PAGE_PROFILE", joinColumns = { 
			@JoinColumn(name = "PGL_PAG_SEQ") }, inverseJoinColumns = {@JoinColumn(name = "PGL_PRF_SEQ") })
	private Set<AdmProfile> admProfiles;
	
	/** The adm menus. */
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "admPage", orphanRemoval = true, fetch = FetchType.LAZY)	
	private Set<AdmMenu> admMenus;	

	/**
	 * Instantiates a new adm pagina.
	 */
	public AdmPage() {
		super();
		this.admProfiles = new HashSet<AdmProfile>();
		this.admMenus = new HashSet<AdmMenu>();
		clean();
	}
	
	public AdmPage(String url, String description) {
		super();
		//this.id = id;
		this.description = description;
		this.url = url;
	}
	
	public AdmPage(PageVO p) {
		this();
		
		this.id = p.getId();
		this.description = p.getDescription();
		this.url = p.getUrl();
	}

	public AdmPage(AdmPageForm p) {
		this();
		
		this.id = p.getId();
		this.description = p.getDescription();
		this.url = p.getUrl();
	}
	
	/**
	 * Limpar.
	 */
	public void clean() {
		this.id = null;
		this.description = null;
		this.url = null;
		this.admProfiles.clear();
		this.admMenus.clear();
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
	 * Pega o the description.
	 *
	 * @return o the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Atribui o the description.
	 *
	 * @param description
	 *            o novo the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Pega o the url.
	 *
	 * @return o the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Atribui o the url.
	 *
	 * @param url
	 *            o novo the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Pega o the adm profiles.
	 *
	 * @return o the adm profiles
	 */
	public Set<AdmProfile> getAdmProfiles() {
		return this.admProfiles;
	}

	/**
	 * Atribui o the adm profiles.
	 *
	 * @param admProfiles
	 *            o novo the adm profiles
	 */
	public void setAdmProfiles(Set<AdmProfile> admProfiles) {
		this.admProfiles = admProfiles;
	}
	
	public Set<AdmMenu> getAdmMenus() {
		return admMenus;
	}

	public void setAdmMenus(Set<AdmMenu> admMenus) {
		this.admMenus = admMenus;
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
		AdmPage other = (AdmPage) obj;
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
		return this.url;
	}

	public String getProfilesPage() {
		String ret = "";
		for (AdmProfile item : getAdmProfiles()) {
			ret = ret.concat(item.getDescription()).concat(", ");
		}
		if (ret != "") {
			ret = ret.substring(0, ret.length() - 2);
		}
		return ret;
	}

	public PageVO toPageVO() {
		PageVO p = new PageVO();

		p.setId(id);
		p.setDescription(description);
		p.setUrl(url);
		
		return p;
	}
	
}