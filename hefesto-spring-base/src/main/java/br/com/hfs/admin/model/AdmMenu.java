package br.com.hfs.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.hfs.admin.controller.form.AdmMenuForm;
import br.com.hfs.vo.MenuVO;

// TODO: Auto-generated Javadoc
/**
 * The persistent class for the ADM_MENU database table.
 * 
 */
@Entity
@Table(name = "ADM_MENU")
@NamedQueries({
	@NamedQuery(name = "AdmMenu.getDescriptionById", query = "SELECT c.description FROM AdmMenu c WHERE c.id = ?1"),
	@NamedQuery(name = "AdmMenu.countNovo", query = "SELECT COUNT(c) FROM AdmMenu c WHERE LOWER(c.description) = ?1"),
	@NamedQuery(name = "AdmMenu.countAntigo", query = "SELECT COUNT(c) FROM AdmMenu c WHERE LOWER(c.description) <> ?1 AND LOWER(c.description) = ?2"),	
	@NamedQuery(name = "AdmMenu.findMenuRaiz", query = "SELECT m FROM AdmMenu m left join m.admMenuParent mp left join m.admPage f WHERE m.admMenuParent IS NULL ORDER BY m.order, mp.order"),
	@NamedQuery(name = "AdmMenu.findMenuRaizByDescription", query = "SELECT m FROM AdmMenu m left join m.admMenuParent mp left join m.admPage f WHERE m.admMenuParent IS NULL AND m.description = ?1 ORDER BY m.order, mp.order"),
	@NamedQuery(name = "AdmMenu.countMenuByPage", query = "SELECT COUNT(m) FROM AdmMenu m WHERE m.admPage = ?1"),
	@NamedQuery(name = "AdmMenu.findChildrenMenus", query = "SELECT m FROM AdmMenu m left join m.admMenuParent mp WHERE m.admMenuParent = ?1 ORDER BY m.order, mp.order"),
	@NamedQuery(name = "AdmMenu.findAdminMenuParentByPage", query="SELECT t FROM AdmMenu t WHERE t.id IN (SELECT m.admMenuParent.id FROM AdmMenu m WHERE m.admPage = ?1 AND m.admMenuParent IS NULL AND m.id <= 9) ORDER BY t.id, t.order"),
	@NamedQuery(name = "AdmMenu.findMenuParentByPage", query="SELECT t FROM AdmMenu t WHERE t.id IN (SELECT m.admMenuParent.id FROM AdmMenu m WHERE m.admPage = ?1 AND m.admMenuParent IS NULL AND m.id > 9) ORDER BY t.id, t.order"),
	@NamedQuery(name = "AdmMenu.findPageByMenu", query="SELECT m.admPage FROM AdmMenu m WHERE m.admPage = ?1 AND m.id = ?2")
})

public class AdmMenu implements Serializable, Comparable<AdmMenu> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id	
	@GenericGenerator(name = "ADM_MENU_ID_GENERATOR",
	strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
    	@Parameter(name = "sequence_name", value = "ADM_MENU_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
	})		
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADM_MENU_ID_GENERATOR")
	@Column(name = "MNU_SEQ")
	private Long id;

	/** The description. */
	@NotNull
	@NotBlank
	@Size(min=4, max=255)
	@Column(name = "MNU_DESCRIPTION", unique = true, nullable = false, length = 255)
	private String description;

	/** The order. */
	@Column(name = "MNU_ORDER")
	private Integer order;

	/** The id pagina. */
	@Column(name = "MNU_PAG_SEQ", nullable = true)
	private Long idPage;

	/** The adm pagina. */
	// bi-directional many-to-one association to AdmPage
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "MNU_PAG_SEQ", nullable = true, insertable = false, updatable = false)
	private AdmPage admPage;

	@Column(name = "MNU_PARENT_SEQ", nullable = true)
	private Long idMenuParent;

	/** The adm menu. */
	// bi-directional many-to-one association to AdmMenu
	@ManyToOne(fetch = FetchType.LAZY) //(cascade={CascadeType.ALL}) //
	@JoinColumn(name = "MNU_PARENT_SEQ", nullable = true, insertable = false, updatable = false)
	private AdmMenu admMenuParent;

	/** The adm menus. */
	// bi-directional many-to-one association to AdmMenu
	//@OrderBy("order")
	@JsonIgnore
	//@JsonSerialize(using = AdmMenuListSerializer.class)
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "admMenuParent", orphanRemoval = true, fetch = FetchType.EAGER)	
	private List<AdmMenu> admSubMenus;

	/**
	 * Instantiates a new adm menu.
	 */
	public AdmMenu() {
		this.admSubMenus = new ArrayList<AdmMenu>();
		clean();
	}
	
	public AdmMenu(String description, Long idMenuParent, Long idPage, Integer order) {
		super();
		this.description = description;
		this.order = order;
		this.idPage = idPage;
		this.idMenuParent = idMenuParent;
	}
	
	public AdmMenu(MenuVO m) {
		this();
		this.id = m.getId();
		this.description = m.getDescription();
		this.order = m.getOrder();
		this.idPage = m.getIdPage();
		this.idMenuParent = m.getMenuParent().getId();
	}
	
	public AdmMenu(AdmMenuForm m) {
		this();
		this.id = m.getId();
		this.description = m.getDescription();
		this.order = m.getOrder();
		this.idPage = m.getIdPage();
		this.idMenuParent = m.getIdMenuParent();
	}

	/**
	 * Limpar.
	 */
	public void clean() {
		this.id = null;
		this.description = null;
		this.order = null;
		this.idPage = null;
		this.admPage = new AdmPage();
		this.admMenuParent = null;
		this.admSubMenus.clear();
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
	 * Pega o the order.
	 *
	 * @return o the order
	 */
	public Integer getOrder() {
		return this.order;
	}

	/**
	 * Atribui o the order.
	 *
	 * @param order
	 *            o novo the order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Pega o the adm pagina.
	 *
	 * @return o the adm pagina
	 */
	public AdmPage getAdmPage() {
		return this.admPage;
	}

	/**
	 * Atribui o the adm pagina.
	 *
	 * @param admPage
	 *            o novo the adm pagina
	 */
	public void setAdmPage(AdmPage admPage) {
		this.admPage = admPage;
	}

	/**
	 * Pega o the adm menu.
	 *
	 * @return o the adm menu
	 */
	public AdmMenu getAdmMenuParent() {
		return this.admMenuParent;
	}

	/**
	 * Atribui o the adm menu.
	 *
	 * @param admMenuParent
	 *            o novo the adm menu
	 */
	public void setAdmMenuParent(AdmMenu admMenuParent) {
		this.admMenuParent = admMenuParent;
	}

	/**
	 * Pega o the adm menus.
	 *
	 * @return o the adm menus
	 */
	public List<AdmMenu> getAdmSubMenus() {
		if (this.admSubMenus!=null && !this.admSubMenus.isEmpty()){
			Collections.sort(this.admSubMenus, new Comparator<AdmMenu>() {
				@Override
				public int compare(AdmMenu o1, AdmMenu o2) {
					return o1.getOrder().compareTo(o2.getOrder());
				}
			});
		}
		return this.admSubMenus;
	}

	/**
	 * Atribui o the adm menus.
	 *
	 * @param admSubMenus
	 *            o novo the adm menus
	 */
	public void setAdmSubMenus(List<AdmMenu> admSubMenus) {
		this.admSubMenus = admSubMenus;
	}

	/**
	 * Adiciona o adm sub menus.
	 *
	 * @param admSubMenus
	 *            the adm sub menus
	 * @return the adm menu
	 */
	public AdmMenu addAdmSubMenus(AdmMenu admSubMenus) {
		getAdmSubMenus().add(admSubMenus);
		admSubMenus.setAdmMenuParent(this);

		return admSubMenus;
	}

	/**
	 * Remove o adm sub menus.
	 *
	 * @param admSubMenus
	 *            the adm sub menus
	 * @return the adm menu
	 */
	public AdmMenu removeAdmSubMenus(AdmMenu admSubMenus) {
		getAdmSubMenus().remove(admSubMenus);
		admSubMenus.setAdmMenuParent(null);

		return admSubMenus;
	}

	/*
	 * Pega o the adm menu funcionarios.
	 *
	 * @return o the adm menu funcionarios
	 *
	public List<AdmMenuFuncionario> getAdmMenuFuncionarios() {
		return this.admMenuFuncionarios;
	}

	**
	 * Atribui o the adm menu funcionarios.
	 *
	 * @param admMenuFuncionarios
	 *            o novo the adm menu funcionarios
	 *
	public void setAdmMenuFuncionarios(List<AdmMenuFuncionario> admMenuFuncionarios) {
		this.admMenuFuncionarios = admMenuFuncionarios;
	}

	**
	 * Adiciona o adm menu funcionario.
	 *
	 * @param admMenuFuncionario
	 *            the adm menu funcionario
	 * @return the adm menu funcionario
	 *
	public AdmMenuFuncionario addAdmMenuFuncionario(AdmMenuFuncionario admMenuFuncionario) {
		getAdmMenuFuncionarios().add(admMenuFuncionario);
		admMenuFuncionario.setAdmMenu(this);

		return admMenuFuncionario;
	}

	**
	 * Remove o adm menu funcionario.
	 *
	 * @param admMenuFuncionario
	 *            the adm menu funcionario
	 * @return the adm menu funcionario
	 *
	public AdmMenuFuncionario removeAdmMenuFuncionario(AdmMenuFuncionario admMenuFuncionario) {
		getAdmMenuFuncionarios().remove(admMenuFuncionario);
		admMenuFuncionario.setAdmMenu(null);

		return admMenuFuncionario;
	}
	 */
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admMenuParent == null) ? 0 : admMenuParent.hashCode());
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
		AdmMenu other = (AdmMenu) obj;
		if (admMenuParent == null) {
			if (other.admMenuParent != null)
				return false;
		} else if (!admMenuParent.equals(other.admMenuParent))
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
		return getAdmPage() == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AdmMenu m) {
		return getDescription().compareTo(m.getDescription());
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.admPage != null ? this.admPage.getUrl() : null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.description;
	}

	/**
	 * Pega o the id pagina.
	 *
	 * @return o the id pagina
	 */
	public Long getIdPage() {
		return idPage;
	}

	/**
	 * Atribui o the id pagina.
	 *
	 * @param idPage
	 *            o novo the id pagina
	 */
	public void setIdPage(Long idPage) {
		this.idPage = idPage;
	}
	
	
	public Long getIdMenuParent() {
		return idMenuParent;
	}

	public void setIdMenuParent(Long idMenuParent) {
		this.idMenuParent = idMenuParent;
	}
	
	/**
	 * Gets the nome recursivo.
	 *
	 * @param m
	 *            the m
	 * @return the nome recursivo
	 */
	private String getNomeRecursivo(AdmMenu m) {
		return m.getAdmPage() == null ? m.getDescription()
				: m.getAdmMenuParent() != null ? getNomeRecursivo(m.getAdmMenuParent()) + " -> " + m.getDescription() : "";
	}
	
	/**
	 * Gets the nome recursivo.
	 *
	 * @return the nome recursivo
	 */
	public String getNomeRecursivo() {
		return this.getNomeRecursivo(this);
	}

	public MenuVO toMenuVO() {
		MenuVO m = new MenuVO();
		
		m.setId(id);
		m.setDescription(description);
		m.setOrder(order);
		m.setIdPage(idPage);
		if (admPage!=null) {
				m.setPage(admPage.toPageVO());
		}
		for (AdmMenu admSubMenu : admSubMenus) {
			m.getSubMenus().add(admSubMenu.toMenuVO());
		}
		
		return m;
	}

}