package br.com.hfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.model.AdmMenu;

public interface AdmMenuRepository extends JpaRepository<AdmMenu, Long> {

	@Query("AdmMenu.findChildrenMenus")
	List<AdmMenu> findChildrenMenus(AdmMenu menu);
}
