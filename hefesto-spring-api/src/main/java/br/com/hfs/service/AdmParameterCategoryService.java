package br.com.hfs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hfs.base.BaseService;
import br.com.hfs.model.AdmParameterCategory;
import br.com.hfs.repository.AdmParameterCategoryRepository;

@Service
public class AdmParameterCategoryService extends BaseService<AdmParameterCategory, Long, AdmParameterCategoryRepository> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryService() {
		super(AdmParameterCategory.class);
	}
	
	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER_CATEGORY", "PMC_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER_CATEGORY", "PMC_SEQ", startInterval, endInterval);
	}
	
}
