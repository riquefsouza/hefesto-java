package br.com.hfs.admin.repository;

import java.util.List;
import java.util.Optional;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.base.BaseRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class AdmUserRepository extends BaseRepository<AdmUser, Long> {

	private static final long serialVersionUID = 1L;

	public AdmUserRepository() {
		super(AdmUser.class);
	}
	
	public List<AdmUser> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_USER", "USU_SEQ", pageNumber, pageSize);
	}

	public List<AdmUser> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_USER", "USU_SEQ", startInterval, endInterval);
	}

	public Optional<AdmUser> findByLogin(String login) {
		try {
			TypedQuery<AdmUser> tquery = query(AdmUser.class, "AdmUser.findByLogin", login);
			return Optional.of(tquery.getSingleResult());
		} catch (Exception e) {
			return Optional.empty();
		}		
	}
	
	public List<AdmUser> findByLikeEmail(String email){
		return queryList("AdmUser.findByLikeEmail", email + "%");
	}
	
	public List<?> findIPByOracle() {
		//TypedQuery<Object> query = em.createNamedQuery("AdmUser.findIPByOracle", Object.class);
		Query query = em.createNativeQuery("SELECT SYS_CONTEXT('USERENV', 'IP_ADDRESS', 15) FROM DUAL");
		return query.getResultList();
	}

	public List<?> findIPByPostgreSQL() {
		//TypedQuery<Object> query = em.createNamedQuery("AdmUser.findIPByPostgreSQL", Object.class);
		Query query = em.createNativeQuery("SELECT substr(CAST(inet_client_addr() AS VARCHAR),1,strpos(CAST(inet_client_addr() AS VARCHAR),'/')-1)");
		return query.getResultList();
	}

	public List<?> setLoginPostgreSQL(String login) {
		//TypedQuery<Object> query = em.createNamedQuery("AdmUser.setLoginPostgreSQL", Object.class);
		Query query = em.createNativeQuery("select set_config('myvars.usuario_login', ?1, false)");
		query.setParameter(1, login);
		return query.getResultList();
	}

	public List<?> setIPPostgreSQL(String ip) {
		//TypedQuery<Object> query = em.createNamedQuery("AdmUser.setIPPostgreSQL", Object.class);
		Query query = em.createNativeQuery("select set_config('myvars.usuario_ip', ?1, false)");
		query.setParameter(1, ip);
		return query.getResultList();
	}

}
