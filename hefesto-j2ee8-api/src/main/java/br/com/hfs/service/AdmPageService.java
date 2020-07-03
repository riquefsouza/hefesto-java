package br.com.hfs.service;

import java.util.List;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmPageDAO;
import br.com.hfs.model.AdmPage;

public class AdmPageService extends BaseService<AdmPage, Long, AdmPageDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
