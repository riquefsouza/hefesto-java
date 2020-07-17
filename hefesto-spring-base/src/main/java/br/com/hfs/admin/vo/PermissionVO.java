package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PermissaoVO.
 */
public class PermissionVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The perfil. */
	private ProfileVO profile;
	
	/** The paginas. */
	private List<PageVO> pages;

	/**
	 * Instantiates a new permissao VO.
	 */
	public PermissionVO() {
		super();
		this.pages = new ArrayList<PageVO>();
		clean();
	}

	public void clean() {
		this.profile = new ProfileVO();
		this.pages.clear();
	}

	public ProfileVO getProfile() {
		return profile;
	}

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	public List<PageVO> getPages() {
		return pages;
	}

	public void setPages(List<PageVO> pages) {
		this.pages = pages;
	}

}
