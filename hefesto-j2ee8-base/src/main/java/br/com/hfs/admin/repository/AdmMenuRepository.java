package br.com.hfs.admin.repository;

import java.util.List;

import javax.persistence.TypedQuery;

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
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmMenu.findChildrenMenus", this.getClazz());
		query.setParameter(1, menu);
		return query.getResultList();
	}
	
	public List<AdmMenu> findMenuRoot() {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmMenu.findMenuRoot", this.getClazz());
		return query.getResultList();
	}	

	public List<AdmMenu> findMenuRootByDescription(String nomeItemMenu) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmMenu.findMenuRootByDescription", this.getClazz());
		query.setParameter(1, nomeItemMenu);
		return query.getResultList();
	}
	
	public Integer countMenuByPage(AdmPage page) {
		TypedQuery<Integer> query = em.createNamedQuery("AdmMenu.countMenuByPage", Integer.class);
		query.setParameter(1, page);
		return query.getSingleResult();
	}
	
	public List<AdmMenu> findAdminMenuParentByPage(AdmPage page) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmMenu.findAdminMenuParentByPage", this.getClazz());
		query.setParameter(1, page);
		return query.getResultList();
	}

	public List<AdmMenu> findMenuParentByPage(AdmPage page) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmMenu.findMenuParentByPage", this.getClazz());
		query.setParameter(1, page);
		return query.getResultList();
	}
	
	public AdmPage findPageByMenu(AdmPage page, Long idMenu) {
		TypedQuery<AdmPage> query = em.createNamedQuery("AdmMenu.findPageByMenu", AdmPage.class);
		query.setParameter(1, page);
		return query.getSingleResult();
	}
}
