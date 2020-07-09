package br.com.hfs.admin.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.hfs.admin.model.AdmMenu;
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
	
}
