package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmParameter;

@Stateless
public class AdmParameterDAO extends BaseDAO<AdmParameter, Long> {

	private static final long serialVersionUID = 1L;

	public AdmParameterDAO() {
		super(AdmParameter.class);
	}
	
	public List<AdmParameter> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER", "PAR_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameter> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER", "PAR_SEQ", startInterval, endInterval);
	}
}
