package br.com.hfs.admin.repository;

import java.util.List;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.base.BaseRepository;

public class AdmUserProfileRepository extends BaseRepository<AdmUserProfile, Long> {

	private static final long serialVersionUID = 1L;

	public AdmUserProfileRepository() {
		super(AdmUserProfile.class);
	}
	
	public List<AdmUserProfile> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_USER_PROFILE", "USP_SEQ", pageNumber, pageSize);
	}

	public List<AdmUserProfile> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_USER_PROFILE", "USP_SEQ", startInterval, endInterval);
	}

}
