package br.com.hfs.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.repository.AdmParameterCategoryRepository;
import br.com.hfs.base.BaseService;

@Service
public class AdmParameterCategoryService extends BaseService<AdmParameterCategory, Long, AdmParameterCategoryRepository> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryService() {
		super(AdmParameterCategory.class);
	}
	
	public Page<AdmParameterCategory> findByDescriptionLike(String description, Pageable pagination){
		return repository.findByDescriptionLike(description, pagination);
	}
	
	public List<AdmParameterCategory> findByDescriptionLike(String description){
		return repository.findByDescriptionLike(description);
	}
	
	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER_CATEGORY", "PMC_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER_CATEGORY", "PMC_SEQ", startInterval, endInterval);
	}
	
}
