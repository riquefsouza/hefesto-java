package br.com.hfs.admin.view.admMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.base.BaseViewQuery;
import br.com.hfs.base.IBaseViewQuery;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmMenuMB
		extends BaseViewQuery<AdmMenu, Long, AdmMenuService>
		implements IBaseViewQuery<AdmMenu> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The adm pagina Service. */
	@Inject
	private AdmPageService admPaginaService;

	/** The menu root. */
	private TreeNode menuRoot;

	/** The menu selecionado. */
	private TreeNode menuSelected;

	/** The novo item menu. */
	private AdmMenu novoItemMenu;
	
	/** The lista adm pagina. */
	private List<AdmPage> listAdmPage;

	/** The lista menus pai. */
	private List<AdmMenu> listMenusParent;
	
	/**
	 * Instantiates a new adm menu MB.
	 */
	public AdmMenuMB() {
		super("ListAdmMenu");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#init()
	 */
	@PostConstruct
	public void init() {
		atualizarArvoreMenus();
		initListaPagina();
		initListaMenusPai();
		this.novoItemMenu = new AdmMenu();
	}

	public void excluir(AdmMenu entity) {
		// super.excluir(entity);
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to continue.");
			return;
		}
		AdmMenu m = (AdmMenu) this.menuSelected.getData();
		this.getService().delete(m);
		atualizarArvoreMenus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.jus.trt1.hfsframework.base.IBaseViewCadastro#preProcessPDF(java.lang
	 * .Object)
	 */
	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmMenu");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getBean()
	 */
	@Override
	public AdmMenu getBean() {
		return super.getEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#setBean(java.lang.
	 * Object)
	 */
	@Override
	public void setBean(AdmMenu entity) {
		super.setEntity(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getListaBean()
	 */
	@Override
	public List<AdmMenu> getListBean() {
		return super.getListEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.jus.trt1.hfsframework.base.IBaseViewCadastro#setListaBean(java.util.
	 * List)
	 */
	@Override
	public void setListBean(List<AdmMenu> listEntity) {
		super.setListEntity(listEntity);
	}

	/**
	 * Pega o the menu root.
	 *
	 * @return o the menu root
	 */
	public TreeNode getMenuRoot() {
		return menuRoot;
	}

	/**
	 * Atribui o the menu root.
	 *
	 * @param menuRoot
	 *            o novo the menu root
	 */
	public void setMenuRoot(TreeNode menuRoot) {
		this.menuRoot = menuRoot;
	}

	/**
	 * Pega o the menu selecionado.
	 *
	 * @return o the menu selecionado
	 */
	public TreeNode getMenuSelected() {
		return menuSelected;
	}

	/**
	 * Atribui o the menu selecionado.
	 *
	 * @param menuSelected
	 *            o novo the menu selecionado
	 */
	public void setMenuSelected(TreeNode menuSelected) {
		this.menuSelected = menuSelected;
	}

	/**
	 * Pega o the novo item menu.
	 *
	 * @return o the novo item menu
	 */
	public AdmMenu getNovoItemMenu() {
		return novoItemMenu;
	}

	/**
	 * Atribui o the novo item menu.
	 *
	 * @param novoItemMenu
	 *            o novo the novo item menu
	 */
	public void setNovoItemMenu(AdmMenu novoItemMenu) {
		this.novoItemMenu = novoItemMenu;
	}

	/**
	 * Pega o the lista adm pagina.
	 *
	 * @return o the lista adm pagina
	 */
	public List<AdmPage> getListAdmPage() {
		return listAdmPage;
	}

	/**
	 * Atribui o the lista adm pagina.
	 *
	 * @param listaAdmPage
	 *            o novo the lista adm pagina
	 */
	public void setListAdmPage(List<AdmPage> listAdmPage) {
		this.listAdmPage = listAdmPage;
	}

	/**
	 * Pega o the lista menus pai.
	 *
	 * @return o the lista menus pai
	 */
	public List<AdmMenu> getListMenusParent() {
		return listMenusParent;
	}

	/**
	 * Atribui o the lista menus pai.
	 *
	 * @param listMenusParent
	 *            o novo the lista menus pai
	 */
	public void setListMenusParent(List<AdmMenu> listMenusParent) {
		this.listMenusParent = listMenusParent;
	}

	/**
	 * Inicia o lista menus pai.
	 */
	private void initListaMenusPai() {
		this.listMenusParent = new ArrayList<AdmMenu>();
		List<AdmMenu> registrosCadastrados = this.getService().findAll();
		for (AdmMenu menu : registrosCadastrados) {
			if ((menu.getAdmSubMenus() != null) && (menu.getAdmPage() == null)) {
				this.listMenusParent.add(menu);
			}
		}
	}

	/**
	 * Inicia o lista pagina.
	 */
	private void initListaPagina() {
		this.listAdmPage = admPaginaService.findAll();
	}

	/**
	 * Atualizar arvore menus.
	 */
	public void atualizarArvoreMenus() {
		List<AdmMenu> listaMenus = this.getService().getRepository().findMenuRoot();

		AdmMenu menu = new AdmMenu();
		menu.setDescription("System Menu");
		this.menuRoot = new DefaultTreeNode(menu, null);
		for (AdmMenu itemMenu : listaMenus) {
			TreeNode m = new DefaultTreeNode(itemMenu, this.menuRoot);
			montaSubMenu(itemMenu, m);
		}
	}

	/**
	 * Monta sub menu.
	 *
	 * @param menu
	 *            the menu
	 * @param pMenu
	 *            the menu
	 * @return the list
	 */
	private List<TreeNode> montaSubMenu(AdmMenu menu, TreeNode pMenu) {
		List<TreeNode> lstSubMenu = new ArrayList<TreeNode>();
		if (menu.getAdmSubMenus() != null) {
			for (AdmMenu subMenu : menu.getAdmSubMenus()) {
				if (subMenu.isSubMenu()) {
					TreeNode m = new DefaultTreeNode(subMenu, pMenu);
					montaSubMenu(subMenu, m);
				} else {
					new DefaultTreeNode(subMenu, pMenu);
				}
			}
		}
		return lstSubMenu;
	}

	/**
	 * Sugerir nome item menu.
	 */
	public void sugerirNomeItemMenu() {
		if ((getNovoItemMenu().getAdmPage() != null) && (getNovoItemMenu().getDescription() == null)) {
			getNovoItemMenu().setDescription(getNovoItemMenu().getAdmPage().getDescription());
		}
	}

	/**
	 * On drag drop.
	 *
	 * @param event
	 *            the event
	 */
	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();

		AdmMenu menuMover = (AdmMenu) dragNode.getData();
		AdmMenu menuPai = (AdmMenu) dropNode.getData();

		int ordem = 1;
		for (TreeNode tn : dropNode.getChildren()) {
			AdmMenu menu = (AdmMenu) tn.getData();
			menu.setOrder(Integer.valueOf(ordem));
			ordem++;
		}
		ordem = 1;
		menuMover.setOrder(Integer.valueOf(dropIndex + 1));
		if ((menuMover.getAdmMenuParent() != null) && (!menuMover.getAdmMenuParent().equals(menuPai))) {
			for (AdmMenu menu : menuMover.getAdmMenuParent().getAdmSubMenus()) {
				menu.setOrder(Integer.valueOf(ordem));
				ordem++;
			}
		}
		if (menuPai.getId() == null) {
			menuPai = null;
		}
		this.getService().saveOrUpdateDragReordering(menuMover.getAdmMenuParent(), menuPai, menuMover);

		atualizarArvoreMenus();
	}

	/**
	 * Gets the nome recursivo.
	 *
	 * @param m
	 *            the m
	 * @return the nome recursivo
	 */
	public static String getNomeRecursivo(AdmMenu m) {
		return m.getAdmPage() == null ? m.getDescription()
				: m.getAdmMenuParent() != null ? getNomeRecursivo(m.getAdmMenuParent()) + " -> " + m.getDescription() : "";
	}

	/**
	 * Listener menu selecionado.
	 *
	 * @param event
	 *            the event
	 */
	public void listenerMenuSelected(NodeSelectEvent event) {
		this.menuSelected = event.getTreeNode();
	}
	
	/**
	 * On incluir item menu.
	 */
	public void onIncluirItemMenu() {
		this.menuSelected = null;
		this.novoItemMenu = new AdmMenu();
		atualizarArvoreMenus();
		initListaMenusPai();
	}

	/**
	 * On editar item menu.
	 */
	public void onEditarItemMenu() {
		atualizarArvoreMenus();
		initListaMenusPai();
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to proceed with this action.");
			context.validationFailed();
		} else {
			this.novoItemMenu = ((AdmMenu) this.getService()
					.findById(((AdmMenu) this.menuSelected.getData()).getId().longValue()).get());
		}
	}

	/**
	 * Salvar item menu.
	 *
	 * @return the string
	 */
	public String salvarItemMenu() {
		//int ordem;
		
		if ((this.novoItemMenu.getDescription() == null) || (this.novoItemMenu.getDescription().trim().isEmpty())) {
			generateErrorMessage("Error. Name of the Menu Item. Required field.");
			context.validationFailed();
		} else {
			/*
			if (getService().existeNovo(this.novoItemMenu.getDescricao())){
				generateErrorMessage("Nome do Item de Menu já existe.");
				return null;					
			}
			*/
			try {
//				if (this.novoItemMenu.getAdmMenuPai()!=null)
//					ordem = this.novoItemMenu.getAdmMenuPai().getAdmSubMenus().size() + 2;
//				else
//					ordem = 1;
//				
//				this.novoItemMenu.setOrdem(ordem);
				if (this.novoItemMenu.getAdmPage()!=null){
					this.novoItemMenu.setIdPage(this.novoItemMenu.getAdmPage().getId());
				} else {
					this.novoItemMenu.setIdPage(null);
				}
				this.getService().saveOrUpdate(this.novoItemMenu);
				this.novoItemMenu = new AdmMenu();
				atualizarArvoreMenus();
			} catch (Exception e) {
				generateErrorMessage(e, ERROR_SAVE);
				return null;
			}			
		}
		return getPageList();
	}

	/**
	 * Excluir item menu.
	 */
	public void excluirItemMenu() {
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to proceed with this action.");
			return;
		}
		try {
			AdmMenu m = (AdmMenu) this.menuSelected.getData();
			this.getService().delete(m);
			atualizarArvoreMenus();
		} catch (Exception e) {
			generateErrorMessage(e, ERROR_DELETE);
			return;
		}
	}

}
