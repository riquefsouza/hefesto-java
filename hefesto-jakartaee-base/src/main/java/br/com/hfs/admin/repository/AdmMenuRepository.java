package br.com.hfs.admin.repository;

import java.util.List;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.base.BaseRepository;

public class AdmMenuRepository extends BaseRepository<AdmMenu, Long> {

	private static final long serialVersionUID = 1L;

	public AdmMenuRepository() {
		super(AdmMenu.class);
	}
	
	public List<AdmMenu> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_MENU", "MNU_SEQ", pageNumber, pageSize);
	}

	public List<AdmMenu> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_MENU", "MNU_SEQ", startInterval, endInterval);
	}

	public List<AdmMenu> findChildrenMenus(AdmMenu menu) {
		return queryList("AdmMenu.findChildrenMenus", menu);
	}
	
	public List<AdmMenu> findMenuRoot() {
		return queryList("AdmMenu.findMenuRoot");
	}	

	public List<AdmMenu> findMenuRootByDescription(String nomeItemMenu) {
		return queryList("AdmMenu.findMenuRootByDescription", nomeItemMenu);
	}
	
	public Integer countMenuByPage(AdmPage page) {
		return queryIntegerSingle("AdmMenu.countMenuByPage", page);
	}
	
	public List<AdmMenu> findAdminMenuParentByPage(AdmPage page) {
		return queryList("AdmMenu.findAdminMenuParentByPage", page);
	}

	public List<AdmMenu> findMenuParentByPage(AdmPage page) {
		return queryList("AdmMenu.findMenuParentByPage", page);
	}
	
	public AdmPage findPageByMenu(AdmPage page, Long idMenu) {
		return query(AdmPage.class, "AdmMenu.findPageByMenu", page, idMenu)
				.getSingleResult();
	}
}
