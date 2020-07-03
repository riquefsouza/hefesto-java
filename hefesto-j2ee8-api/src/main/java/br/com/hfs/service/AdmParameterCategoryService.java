package br.com.hfs.service;

import java.util.List;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmParameterCategoryDAO;
import br.com.hfs.model.AdmParameterCategory;

public class AdmParameterCategoryService extends BaseService<AdmParameterCategory, Long, AdmParameterCategoryDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
