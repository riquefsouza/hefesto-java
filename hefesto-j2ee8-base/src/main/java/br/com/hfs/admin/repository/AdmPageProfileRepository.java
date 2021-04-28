package br.com.hfs.admin.repository;

import java.util.List;

import br.com.hfs.admin.model.AdmPageProfile;
import br.com.hfs.base.BaseRepository;

public class AdmPageProfileRepository extends BaseRepository<AdmPageProfile, Long> {

	private static final long serialVersionUID = 1L;

	public AdmPageProfileRepository() {
		super(AdmPageProfile.class);
	}
	
	public List<AdmPageProfile> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PAGE_PROFILE", "PGL_SEQ", pageNumber, pageSize);
	}

	public List<AdmPageProfile> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PAGE_PROFILE", "PGL_SEQ", startInterval, endInterval);
	}

}
