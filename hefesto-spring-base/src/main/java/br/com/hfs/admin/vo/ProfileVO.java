package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileVO.
 */
public class ProfileVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	private Boolean administrator;

	private String description;

	private Boolean general;
	
	private Boolean intersection;

	private List<PageVO> pages;
	
	private List<UserVO> users;
	
	private List<UserVO> excludedUsers;

	public ProfileVO() {
		this.pages = new ArrayList<PageVO>();
		this.users = new ArrayList<UserVO>();
		this.excludedUsers = new ArrayList<UserVO>();
		clean();
	}

	/**
	 * Limpar.
	 */
	public void clean() {
		this.id = null;
		this.administrator = null;
		this.description = null;
		this.general = null;
		this.intersection = null;
		this.pages.clear();
		this.users.clear();
		this.excludedUsers.clear();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getGeneral() {
		return general;
	}

	public void setGeneral(Boolean general) {
		this.general = general;
	}

	public Boolean getIntersection() {
		return intersection;
	}

	public void setIntersection(Boolean intersection) {
		this.intersection = intersection;
	}

	public List<PageVO> getPages() {
		return pages;
	}

	public void setPages(List<PageVO> pages) {
		this.pages = pages;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	public List<UserVO> getExcludedUsers() {
		return excludedUsers;
	}

	public void setExcludedUsers(List<UserVO> excludedUsers) {
		this.excludedUsers = excludedUsers;
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
		ProfileVO other = (ProfileVO) obj;
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
		return description;
	}

}
