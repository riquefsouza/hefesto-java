package br.com.hfs.admin.service;

import java.util.List;

import br.com.hfs.admin.dao.AdmParameterDAO;
import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.base.BaseService;

public class AdmParameterService extends BaseService<AdmParameter, Long, AdmParameterDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmParameter> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmParameter> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
	public String getValueByCode(String code){
		try {
			return repository.getValueByCode(code);
		} catch (Exception e) {
			return null;
		}
	}
}
