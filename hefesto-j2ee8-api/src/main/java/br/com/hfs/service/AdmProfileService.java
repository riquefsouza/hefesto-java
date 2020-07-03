package br.com.hfs.service;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmProfileDAO;
import br.com.hfs.model.AdmProfile;

@Stateless
public class AdmProfileService extends BaseService<AdmProfile, Long, AdmProfileDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmProfile> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmProfile> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
