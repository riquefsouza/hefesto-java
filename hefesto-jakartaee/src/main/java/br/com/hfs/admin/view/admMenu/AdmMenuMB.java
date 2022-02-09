package br.com.hfs.admin.view.admMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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

	@Inject
	private AdmPageService admPaginaService;

	private TreeNode<AdmMenu> menuRoot;

	private TreeNode<AdmMenu> menuSelected;

	private AdmMenu newItemMenu;
	
	private List<AdmPage> listAdmPage;

	private List<AdmMenu> listMenusParent;
	
	public AdmMenuMB() {
		super("admin/admMenu/listAdmMenu");
	}

	@PostConstruct
	public void init() {
		updateTreeMenus();
		initListPage();
		initListMenusParent();
		this.newItemMenu = new AdmMenu();
	}

	public void delete(AdmMenu entity) {
		// super.excluir(entity);
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to continue.");
			return;
		}
		AdmMenu m = (AdmMenu) this.menuSelected.getData();
		this.getService().delete(m);
		updateTreeMenus();
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
	public TreeNode<AdmMenu> getMenuRoot() {
		return menuRoot;
	}

	/**
	 * Atribui o the menu root.
	 *
	 * @param menuRoot
	 *            o novo the menu root
	 */
	public void setMenuRoot(TreeNode<AdmMenu> menuRoot) {
		this.menuRoot = menuRoot;
	}

	/**
	 * Pega o the menu selecionado.
	 *
	 * @return o the menu selecionado
	 */
	public TreeNode<AdmMenu> getMenuSelected() {
		return menuSelected;
	}

	/**
	 * Atribui o the menu selecionado.
	 *
	 * @param menuSelected
	 *            o novo the menu selecionado
	 */
	public void setMenuSelected(TreeNode<AdmMenu> menuSelected) {
		this.menuSelected = menuSelected;
	}

	public AdmMenu getNewItemMenu() {
		return newItemMenu;
	}

	public void setNewItemMenu(AdmMenu newItemMenu) {
		this.newItemMenu = newItemMenu;
	}

	public List<AdmPage> getListAdmPage() {
		return listAdmPage;
	}

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

	public void setListMenusParent(List<AdmMenu> listMenusParent) {
		this.listMenusParent = listMenusParent;
	}

	private void initListMenusParent() {
		this.listMenusParent = new ArrayList<AdmMenu>();
		List<AdmMenu> registrosCadastrados = this.getService().findAll();
		for (AdmMenu menu : registrosCadastrados) {
			if ((menu.getAdmSubMenus() != null) && (menu.getAdmPage() == null)) {
				this.listMenusParent.add(menu);
			}
		}
	}

	private void initListPage() {
		this.listAdmPage = admPaginaService.findAll();
	}

	public void updateTreeMenus() {
		List<AdmMenu> listaMenus = this.getService().getRepository().findMenuRoot();

		AdmMenu menu = new AdmMenu();
		menu.setDescription("System Menu");
		this.menuRoot = new DefaultTreeNode<AdmMenu>(menu, null);
		for (AdmMenu itemMenu : listaMenus) {
			TreeNode<AdmMenu> m = new DefaultTreeNode<AdmMenu>(itemMenu, this.menuRoot);
			mountSubMenu(itemMenu, m);
		}
	}

	private List<TreeNode<AdmMenu>> mountSubMenu(AdmMenu menu, TreeNode<AdmMenu> pMenu) {
		List<TreeNode<AdmMenu>> lstSubMenu = new ArrayList<TreeNode<AdmMenu>>();
		if (menu.getAdmSubMenus() != null) {
			for (AdmMenu subMenu : menu.getAdmSubMenus()) {
				if (subMenu.isSubMenu()) {
					TreeNode<AdmMenu> m = new DefaultTreeNode<AdmMenu>(subMenu, pMenu);
					mountSubMenu(subMenu, m);
				} else {
					new DefaultTreeNode<AdmMenu>(subMenu, pMenu);
				}
			}
		}
		return lstSubMenu;
	}

	public void suggestNameItemMenu() {
		if ((getNewItemMenu().getAdmPage() != null) && (getNewItemMenu().getDescription() == null)) {
			getNewItemMenu().setDescription(getNewItemMenu().getAdmPage().getDescription());
		}
	}

	/**
	 * On drag drop.
	 *
	 * @param event
	 *            the event
	 */
	public void onDragDrop(TreeDragDropEvent event) {
		TreeNode<AdmMenu> dragNode = event.getDragNode();
		TreeNode<AdmMenu> dropNode = event.getDropNode();
		int dropIndex = event.getDropIndex();

		AdmMenu menuMover = (AdmMenu) dragNode.getData();
		AdmMenu menuPai = (AdmMenu) dropNode.getData();

		int ordem = 1;
		for (TreeNode<AdmMenu> tn : dropNode.getChildren()) {
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

		updateTreeMenus();
	}

	public static String getRecursiveName(AdmMenu m) {
		return m.getAdmPage() == null ? m.getDescription()
				: m.getAdmMenuParent() != null ? getRecursiveName(m.getAdmMenuParent()) + " -> " + m.getDescription() : "";
	}

	public void listenerMenuSelected(NodeSelectEvent event) {
		this.menuSelected = event.getTreeNode();
	}
	
	public void onInsertItemMenu() {
		this.menuSelected = null;
		this.newItemMenu = new AdmMenu();
		updateTreeMenus();
		initListMenusParent();
	}

	public void onEditItemMenu() {
		updateTreeMenus();
		initListMenusParent();
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to proceed with this action.");
			context.validationFailed();
		} else {
			this.newItemMenu = ((AdmMenu) this.getService()
					.findById(((AdmMenu) this.menuSelected.getData()).getId().longValue()).get());
		}
	}

	public String saveItemMenu() {
		//int ordem;
		
		if ((this.newItemMenu.getDescription() == null) || (this.newItemMenu.getDescription().trim().isEmpty())) {
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
				if (this.newItemMenu.getAdmPage()!=null){
					this.newItemMenu.setIdPage(this.newItemMenu.getAdmPage().getId());
				} else {
					this.newItemMenu.setIdPage(null);
				}
				this.getService().saveOrUpdate(this.newItemMenu);
				this.newItemMenu = new AdmMenu();
				updateTreeMenus();
			} catch (Exception e) {
				generateErrorMessage(e, ERROR_SAVE);
				return null;
			}			
		}
		return getPageList();
	}

	public void deleteItemMenu() {
		if (this.menuSelected == null) {
			generateErrorMessage("Please select a menu item to proceed with this action.");
			return;
		}
		try {
			AdmMenu m = (AdmMenu) this.menuSelected.getData();
			this.getService().delete(m);
			updateTreeMenus();
		} catch (Exception e) {
			generateErrorMessage(e, ERROR_DELETE);
			return;
		}
	}

}
