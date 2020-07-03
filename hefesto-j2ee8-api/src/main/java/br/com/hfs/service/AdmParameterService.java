package br.com.hfs.service;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmParameterDAO;
import br.com.hfs.model.AdmParameter;

@Stateless
public class AdmParameterService extends BaseService<AdmParameter, Long, AdmParameterDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmParameter> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmParameter> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
