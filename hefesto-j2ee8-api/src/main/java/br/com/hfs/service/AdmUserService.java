package br.com.hfs.service;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmUserDAO;
import br.com.hfs.model.AdmUser;

@Stateless
public class AdmUserService extends BaseService<AdmUser, Long, AdmUserDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmUser> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmUser> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
