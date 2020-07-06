package br.com.hfs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.model.AdmUser;

public interface AdmUserRepository extends JpaRepository<AdmUser, Long> {

	Optional<AdmUser> findByLogin(String login);
	
	Page<AdmUser> findByNameLike(String name, Pageable pagination);
	
	List<AdmUser> findByNameLike(String name);
	
	@Query(name = "AdmUser.findIPByOracle", nativeQuery = true)
	public List<Object> findIPByOracle();

	@Query(name = "AdmUser.findIPByPostgreSQL", nativeQuery = true)
	public List<Object> findIPByPostgreSQL();

	@Query(name = "AdmUser.setLoginPostgreSQL", nativeQuery = true)
	public List<Object> setLoginPostgreSQL(String login);

	@Query(name = "AdmUser.setIPPostgreSQL", nativeQuery = true)
	public List<Object> setIPPostgreSQL(String ip);

}
