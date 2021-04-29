package br.com.hfs.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmProfile;

public interface AdmProfileRepository extends JpaRepository<AdmProfile, Long> {

	Page<AdmProfile> findByDescriptionLike(String description, Pageable pagination);
	
	List<AdmProfile> findByDescriptionLike(String description);
	
	@Query(name = "AdmProfile.findProfilesByUser")
	public List<AdmProfile> findProfilesByUser(Long idUser);

	@Query(name = "AdmProfile.findProfilesByPage")
	public List<AdmProfile> findProfilesByPage(Long idPage);

	@Query(name = "AdmProfile.findByGeneral")
	public List<AdmProfile> findByGeneral(Boolean geral);

	@Query(name = "AdmProfile.findAdminMenuParentByProfile")
	public List<AdmMenu> findAdminMenuParentByProfile(AdmProfile profile);

	@Query(name = "AdmProfile.findAdminMenuByProfile")
	public List<AdmMenu> findAdminMenuByProfile(AdmProfile profile, AdmMenu admMenu);

	@Query(name = "AdmProfile.findMenuParentByProfile")
	public List<AdmMenu> findMenuParentByProfile(AdmProfile profile);

	@Query(name = "AdmProfile.findMenuByProfile")
	public List<AdmMenu> findMenuByProfile(AdmProfile profile, AdmMenu admMenu);

	@Query(name = "AdmProfile.findUsersByProfile")
	public List<Long> findUsersByProfile(AdmProfile profile);

	@Query(name = "AdmProfile.findMenuParentByIdPerfis")
	public List<AdmMenu> findMenuParentByIdPerfis(List<Long> listaIdProfile);

	@Query(name = "AdmProfile.findMenuByIdPerfis")
	public List<AdmMenu> findMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu);

	@Query(name = "AdmProfile.findAdminMenuParentByIdPerfis")
	public List<AdmMenu> findAdminMenuParentByIdPerfis(List<Long> listaIdProfile);

	@Query(name = "AdmProfile.findAdminMenuByIdPerfis")
	public List<AdmMenu> findAdminMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu);

}
