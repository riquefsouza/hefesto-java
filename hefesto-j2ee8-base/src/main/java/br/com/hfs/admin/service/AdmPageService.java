package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.dao.AdmPageDAO;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.base.BaseService;

public class AdmPageService extends BaseService<AdmPage, Long, AdmPageDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
