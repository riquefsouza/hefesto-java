package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.admin.repository.AdmUserProfileRepository;
import br.com.hfs.base.BaseService;

public class AdmUserProfileService extends BaseService<AdmUserProfile, Long, AdmUserProfileRepository> {

	private static final long serialVersionUID = 1L;

	public List<AdmUserProfile> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmUserProfile> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
