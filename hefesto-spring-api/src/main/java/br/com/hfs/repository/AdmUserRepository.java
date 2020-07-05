package br.com.hfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.model.AdmUser;

public interface AdmUserRepository extends JpaRepository<AdmUser, Long> {

	@Query(value = "AdmUser.findIPByOracle", nativeQuery = true)
	public List<Object> findIPByOracle();

	@Query(value = "AdmUser.findIPByPostgreSQL", nativeQuery = true)
	public List<Object> findIPByPostgreSQL();

	@Query(value = "AdmUser.setLoginPostgreSQL", nativeQuery = true)
	public List<Object> setLoginPostgreSQL(String login);

	@Query(value = "AdmUser.setIPPostgreSQL", nativeQuery = true)
	public List<Object> setIPPostgreSQL(String ip);

}
