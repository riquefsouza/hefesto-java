package br.com.hfs.admin.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.base.BaseDAO;

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

	public List<Object> findIPByOracle() {
		TypedQuery<Object> query = em.createNamedQuery("AdmUser.findIPByOracle", Object.class);
		return query.getResultList();
	}

	public List<Object> findIPByPostgreSQL() {
		TypedQuery<Object> query = em.createNamedQuery("AdmUser.findIPByPostgreSQL", Object.class);
		return query.getResultList();
	}

	public List<Object> setLoginPostgreSQL(String login) {
		TypedQuery<Object> query = em.createNamedQuery("AdmUser.setLoginPostgreSQL", Object.class);
		query.setParameter(1, login);
		return query.getResultList();
	}

	public List<Object> setIPPostgreSQL(String ip) {
		TypedQuery<Object> query = em.createNamedQuery("AdmUser.setIPPostgreSQL", Object.class);
		query.setParameter(1, ip);
		return query.getResultList();
	}

}
