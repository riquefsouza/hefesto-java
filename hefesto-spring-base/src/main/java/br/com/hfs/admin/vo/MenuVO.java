package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuVO.
 */
public class MenuVO implements Serializable, Comparable<MenuVO> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The descricao. */
	private String description;

	/** The ordem. */
	private Integer order;

	/** The id pagina. */
	private Long idPage;

	/** The Pagina. */
	private PageVO Page;

	/** The menu pai. */
	private MenuVO menuParent;

	/** The sub menus. */
	private List<MenuVO> subMenus;

	/**
	 * Instantiates a new menu VO.
	 */
	public MenuVO() {
		this.subMenus = new ArrayList<MenuVO>();
		clean();
	}

	/**
	 * Limpar.
	 */
	public void clean() {
		this.id = null;
		this.description = null;
		this.order = null;
		this.idPage = null;
		this.Page = new PageVO();
		this.menuParent = null;
		this.subMenus.clear();
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getIdPage() {
		return idPage;
	}

	public void setIdPage(Long idPage) {
		this.idPage = idPage;
	}

	public PageVO getPage() {
		return Page;
	}

	public void setPage(PageVO page) {
		Page = page;
	}

	public MenuVO getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(MenuVO menuParent) {
		this.menuParent = menuParent;
	}

	/**
	 * Pega o the adm menus.
	 *
	 * @return o the adm menus
	 */
	public List<MenuVO> getSubMenus() {
		if (this.subMenus!=null && !this.subMenus.isEmpty()){
			Collections.sort(this.subMenus, new Comparator<MenuVO>() {
				@Override
				public int compare(MenuVO o1, MenuVO o2) {
					if (o1 == null || o1.getOrder() == null || o2 == null || o2.getOrder() == null) {
						return 0;
					}
					return o1.getOrder().compareTo(o2.getOrder());
				}
			});
		}
		return this.subMenus;
	}

	/**
	 * Atribui o the adm menus.
	 *
	 * @param subMenus
	 *            o novo the adm menus
	 */
	public void setSubMenus(List<MenuVO> subMenus) {
		this.subMenus = subMenus;
	}

	/**
	 * Adiciona o adm sub menus.
	 *
	 * @param subMenus
	 *            the adm sub menus
	 * @return the adm menu
	 */
	public MenuVO addSubMenus(MenuVO subMenus) {
		getSubMenus().add(subMenus);
		subMenus.setMenuParent(this);

		return subMenus;
	}

	/**
	 * Remove o adm sub menus.
	 *
	 * @param subMenus
	 *            the adm sub menus
	 * @return the adm menu
	 */
	public MenuVO removeSubMenus(MenuVO subMenus) {
		getSubMenus().remove(subMenus);
		subMenus.setMenuParent(null);

		return subMenus;
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuParent == null) ? 0 : menuParent.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		MenuVO other = (MenuVO) obj;
		if (menuParent == null) {
			if (other.menuParent != null)
				return false;
		} else if (!menuParent.equals(other.menuParent))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Checks if is sub menu.
	 *
	 * @return true, if is sub menu
	 */
	public boolean isSubMenu() {
		return getPage() == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MenuVO m) {
		return getDescription().compareTo(m.getDescription());
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.Page != null ? this.Page.getUrl() : null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.description;
	}


}
