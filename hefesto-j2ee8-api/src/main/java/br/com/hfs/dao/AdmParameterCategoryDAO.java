package br.com.hfs.dao;

import java.util.List;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmParameterCategory;

public class AdmParameterCategoryDAO extends BaseDAO<AdmParameterCategory, Long> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryDAO() {
		super(AdmParameterCategory.class);
	}
	
	public List<AdmParameterCategory> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER_CATEGORY", "PMC_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameterCategory> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER_CATEGORY", "PMC_SEQ", startInterval, endInterval);
	}

}
