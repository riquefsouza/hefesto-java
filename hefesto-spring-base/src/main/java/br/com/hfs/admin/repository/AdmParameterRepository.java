package br.com.hfs.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.admin.model.AdmParameter;

public interface AdmParameterRepository extends JpaRepository<AdmParameter, Long> {

	@Query(name = "AdmParameter.getValueByCode")
	public String getValueByCode(String code);
	
	Page<AdmParameter> findByDescriptionLike(String description, Pageable pagination);
	
	List<AdmParameter> findByDescriptionLike(String description);
}
