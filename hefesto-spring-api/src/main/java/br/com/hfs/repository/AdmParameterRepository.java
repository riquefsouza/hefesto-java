package br.com.hfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.model.AdmParameter;

public interface AdmParameterRepository extends JpaRepository<AdmParameter, Long> {

	@Query("AdmParameter.getValueByCode")
	public String getValueByCode(String code);
	
}
