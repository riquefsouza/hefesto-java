package br.com.hfs.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.admin.model.AdmParameterCategory;

public interface AdmParameterCategoryRepository extends JpaRepository<AdmParameterCategory, Long> {

	Page<AdmParameterCategory> findByDescriptionLike(String description, Pageable pagination);
	
	List<AdmParameterCategory> findByDescriptionLike(String description);
}
