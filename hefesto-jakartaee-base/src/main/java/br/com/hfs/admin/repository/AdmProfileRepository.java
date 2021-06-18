package br.com.hfs.admin.repository;

import java.util.List;

import jakarta.persistence.TypedQuery;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.base.BaseRepository;

public class AdmProfileRepository extends BaseRepository<AdmProfile, Long> {

	private static final long serialVersionUID = 1L;

	public AdmProfileRepository() {
		super(AdmProfile.class);
	}
	
	public List<AdmProfile> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PROFILE", "PRF_SEQ", pageNumber, pageSize);
	}

	public List<AdmProfile> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PROFILE", "PRF_SEQ", startInterval, endInterval);
	}

	public List<AdmProfile> findProfilesByUser(Long idUser) {
		TypedQuery<AdmProfile> query = em.createNamedQuery("AdmProfile.findProfilesByUser", AdmProfile.class);
		query.setParameter(1, idUser);
		return query.getResultList();
	}

	public List<AdmProfile> findProfilesByPage(Long idPage) {
		TypedQuery<AdmProfile> query = em.createNamedQuery("AdmProfile.findProfilesByPage", AdmProfile.class);
		query.setParameter(1, idPage);
		return query.getResultList();
	}
	
	public List<AdmProfile> findByGeneral(Boolean geral) {
		TypedQuery<AdmProfile> query = em.createNamedQuery("AdmProfile.findByGeneral", AdmProfile.class);
		query.setParameter(1, geral);
		return query.getResultList();
	}

	public List<AdmMenu> findAdminMenuParentByProfile(AdmProfile profile) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findAdminMenuParentByProfile", AdmMenu.class);
		query.setParameter(1, profile);
		return query.getResultList();
	}

	public List<AdmMenu> findAdminMenuByProfile(AdmProfile profile, AdmMenu admMenu) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findAdminMenuByProfile", AdmMenu.class);
		query.setParameter(1, profile);
		query.setParameter(2, admMenu);
		return query.getResultList();
	}

	public List<AdmMenu> findMenuParentByProfile(AdmProfile profile) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findMenuParentByProfile", AdmMenu.class);
		query.setParameter(1, profile);
		return query.getResultList();
	}

	public List<AdmMenu> findMenuByProfile(AdmProfile profile, AdmMenu admMenu) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findMenuByProfile", AdmMenu.class);
		query.setParameter(1, profile);
		query.setParameter(2, admMenu);
		return query.getResultList();
	}

	public List<Long> findUsersByProfile(AdmProfile profile) {
		TypedQuery<Long> query = em.createNamedQuery("AdmProfile.findUsersByProfile", Long.class);
		query.setParameter(1, profile);
		return query.getResultList();
	}

	public List<AdmMenu> findMenuParentByIdPerfis(List<Long> listaIdProfile) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findMenuParentByIdPerfis", AdmMenu.class);
		query.setParameter(1, listaIdProfile);
		return query.getResultList();
	}

	public List<AdmMenu> findMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findMenuByIdPerfis", AdmMenu.class);
		query.setParameter(1, listaIdProfile);
		query.setParameter(2, admMenu);
		return query.getResultList();
	}

	public List<AdmMenu> findAdminMenuParentByIdPerfis(List<Long> listaIdProfile) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findAdminMenuParentByIdPerfis", AdmMenu.class);
		query.setParameter(1, listaIdProfile);
		return query.getResultList();
	}

	public List<AdmMenu> findAdminMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu) {
		TypedQuery<AdmMenu> query = em.createNamedQuery("AdmProfile.findAdminMenuByIdPerfis", AdmMenu.class);
		query.setParameter(1, listaIdProfile);
		query.setParameter(2, admMenu);
		return query.getResultList();
	}

	public List<AdmPage> findPagesByProfile(AdmProfile profile){
		TypedQuery<AdmPage> query = em.createNamedQuery("AdmProfile.findPagesByProfile", AdmPage.class);
		query.setParameter(1, profile);
		return query.getResultList();
	}
}
