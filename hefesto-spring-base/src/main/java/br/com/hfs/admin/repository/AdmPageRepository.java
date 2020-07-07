package br.com.hfs.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.admin.model.AdmPage;

public interface AdmPageRepository extends JpaRepository<AdmPage, Long> {

	Page<AdmPage> findByDescriptionLike(String description, Pageable pagination);
	
	List<AdmPage> findByDescriptionLike(String description);
	
}
