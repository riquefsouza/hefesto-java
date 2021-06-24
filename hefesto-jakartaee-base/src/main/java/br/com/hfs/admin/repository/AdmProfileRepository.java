package br.com.hfs.admin.repository;

import java.util.List;

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
		return queryList("AdmProfile.findProfilesByUser", idUser);
	}

	public List<AdmProfile> findProfilesByPage(Long idPage) {
		return queryList("AdmProfile.findProfilesByPage", idPage);
	}
	
	public List<AdmProfile> findByGeneral(Boolean geral) {
		return queryList("AdmProfile.findByGeneral", geral);
	}

	public List<AdmMenu> findAdminMenuParentByProfile(AdmProfile profile) {
		return query(AdmMenu.class, "AdmProfile.findAdminMenuParentByProfile", profile)
				.getResultList();
	}

	public List<AdmMenu> findAdminMenuByProfile(AdmProfile profile, AdmMenu admMenu) {
		return query(AdmMenu.class, "AdmProfile.findAdminMenuByProfile", profile, admMenu)
				.getResultList();
	}

	public List<AdmMenu> findMenuParentByProfile(AdmProfile profile) {
		return query(AdmMenu.class, "AdmProfile.findMenuParentByProfile", profile)
				.getResultList();
	}

	public List<AdmMenu> findMenuByProfile(AdmProfile profile, AdmMenu admMenu) {
		return query(AdmMenu.class, "AdmProfile.findMenuByProfile", profile, admMenu)
				.getResultList();
	}

	public List<Long> findUsersByProfile(AdmProfile profile) {
		return queryLongList("AdmProfile.findUsersByProfile", profile);
	}

	public List<AdmMenu> findMenuParentByIdPerfis(List<Long> listaIdProfile) {
		return query(AdmMenu.class, "AdmProfile.findMenuParentByIdPerfis", listaIdProfile)
				.getResultList();
	}

	public List<AdmMenu> findMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu) {
		return query(AdmMenu.class, "AdmProfile.findMenuByIdPerfis", listaIdProfile, admMenu)
				.getResultList();
	}

	public List<AdmMenu> findAdminMenuParentByIdPerfis(List<Long> listaIdProfile) {
		return query(AdmMenu.class, "AdmProfile.findAdminMenuParentByIdPerfis", listaIdProfile)
				.getResultList();
	}

	public List<AdmMenu> findAdminMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu) {
		return query(AdmMenu.class, "AdmProfile.findAdminMenuByIdPerfis", listaIdProfile, admMenu)
				.getResultList();
	}

	public List<AdmPage> findPagesByProfile(AdmProfile profile){
		return query(AdmPage.class, "AdmProfile.findPagesByProfile", profile)
				.getResultList();
	}
}
