package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.repository.AdmParameterCategoryRepository;
import br.com.hfs.base.BaseService;

public class AdmParameterCategoryService extends BaseService<AdmParameterCategory, Long, AdmParameterCategoryRepository> {

	private static final long serialVersionUID = 1L;

	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
