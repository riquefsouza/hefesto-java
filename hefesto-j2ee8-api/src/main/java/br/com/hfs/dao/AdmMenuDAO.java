package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmMenu;

@Stateless
public class AdmMenuDAO extends BaseDAO<AdmMenu, Long> {

	private static final long serialVersionUID = 1L;

	public AdmMenuDAO() {
		super(AdmMenu.class);
	}
	
	public List<AdmMenu> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_MENU", "MNU_SEQ", pageNumber, pageSize);
	}

	public List<AdmMenu> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_MENU", "MNU_SEQ", startInterval, endInterval);
	}

}
