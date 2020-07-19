package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.repository.AdmPageRepository;
import br.com.hfs.base.BaseService;

public class AdmPageService extends BaseService<AdmPage, Long, AdmPageRepository> {

	private static final long serialVersionUID = 1L;

	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
	public List<AdmProfile> findProfilesByPage(AdmPage page){
		return repository.findProfilesByPage(page);
	}
}
