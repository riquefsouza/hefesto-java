package br.com.hfs.admin.repository;

import java.util.List;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.base.BaseRepository;

public class AdmPageRepository extends BaseRepository<AdmPage, Long> {

	private static final long serialVersionUID = 1L;

	public AdmPageRepository() {
		super(AdmPage.class);
	}
	
	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PAGE", "PAG_SEQ", pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PAGE", "PAG_SEQ", startInterval, endInterval);
	}
	
	public List<AdmProfile> findProfilesByPage(AdmPage page){
		return query(AdmProfile.class, "AdmPage.findProfilesByPage", page)
				.getResultList();
	}
}
