package br.com.hfs.admin.dao;

import java.util.List;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.base.BaseDAO;

public class AdmPageDAO extends BaseDAO<AdmPage, Long> {

	private static final long serialVersionUID = 1L;

	public AdmPageDAO() {
		super(AdmPage.class);
	}
	
	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PAGE", "PAG_SEQ", pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PAGE", "PAG_SEQ", startInterval, endInterval);
	}
}
