package br.com.hfs.service;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseService;
import br.com.hfs.dao.AdmMenuDAO;
import br.com.hfs.model.AdmMenu;

@Stateless
public class AdmMenuService extends BaseService<AdmMenu, Long, AdmMenuDAO> {

	private static final long serialVersionUID = 1L;

	public List<AdmMenu> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmMenu> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
}
