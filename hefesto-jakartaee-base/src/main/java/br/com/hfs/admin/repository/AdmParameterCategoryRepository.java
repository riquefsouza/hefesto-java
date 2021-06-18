package br.com.hfs.admin.repository;

import java.util.List;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.base.BaseRepository;

public class AdmParameterCategoryRepository extends BaseRepository<AdmParameterCategory, Long> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryRepository() {
		super(AdmParameterCategory.class);
	}
	
	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER_CATEGORY", "PMC_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER_CATEGORY", "PMC_SEQ", startInterval, endInterval);
	}

}
