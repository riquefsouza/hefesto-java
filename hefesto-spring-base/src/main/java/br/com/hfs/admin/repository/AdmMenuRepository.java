package br.com.hfs.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.admin.model.AdmMenu;

public interface AdmMenuRepository extends JpaRepository<AdmMenu, Long> {

	@Query(name = "AdmMenu.findChildrenMenus")
	List<AdmMenu> findChildrenMenus(AdmMenu menu);
	
	Page<AdmMenu> findByDescriptionLike(String description, Pageable pagination);
	
	List<AdmMenu> findByDescriptionLike(String description);
}
