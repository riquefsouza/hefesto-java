package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.model.AdmPageProfile;
import br.com.hfs.admin.repository.AdmPageProfileRepository;
import br.com.hfs.base.BaseService;

public class AdmPageProfileService extends BaseService<AdmPageProfile, Long, AdmPageProfileRepository> {

	private static final long serialVersionUID = 1L;

	public List<AdmPageProfile> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmPageProfile> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
