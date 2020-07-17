/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PaginaVO.
 */
public class PageVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The descricao. */
	private String description;

	/** The url. */
	private String url;

	/** The perfils. */
	private List<ProfileVO> profiles;

	/**
	 * Instantiates a new pagina VO.
	 */
	public PageVO() {
		this.profiles = new ArrayList<ProfileVO>();
		clean();
	}
	
	/**
	 * Limpar.
	 */
	public void clean() {
		this.id = null;
		this.description = null;
		this.url = null;
		this.profiles.clear();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ProfileVO> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<ProfileVO> profiles) {
		this.profiles = profiles;
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
		PageVO other = (PageVO) obj;
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
		for (ProfileVO item : getProfiles()) {
			ret = ret.concat(item.getDescription()).concat(", ");
		}
		if (ret != "") {
			ret = ret.substring(0, ret.length() - 2);
		}
		return ret;
	}
	

}
