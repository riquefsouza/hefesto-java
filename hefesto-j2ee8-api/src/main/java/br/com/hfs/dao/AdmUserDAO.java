package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmUser;

@Stateless
public class AdmUserDAO extends BaseDAO<AdmUser, Long> {

	private static final long serialVersionUID = 1L;

	public AdmUserDAO() {
		super(AdmUser.class);
	}
	
	public List<AdmUser> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_USER", "USU_SEQ", pageNumber, pageSize);
	}

	public List<AdmUser> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_USER", "USU_SEQ", startInterval, endInterval);
	}

}
