package br.com.hfs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hfs.model.AdmMenu;
import br.com.hfs.model.AdmProfile;

public interface AdmProfileRepository extends JpaRepository<AdmProfile, Long> {

	@Query("AdmProfile.findProfilesByUser")
	public List<AdmProfile> findProfilesByUser(Long idUser);
	
	@Query("AdmProfile.findByGeneral")
	public List<AdmProfile> findByGeneral(Boolean geral);

	@Query("AdmProfile.findAdminMenuParentByProfile")
	public List<AdmMenu> findAdminMenuParentByProfile(AdmProfile profile);

	@Query("AdmProfile.findAdminMenuByProfile")
	public List<AdmMenu> findAdminMenuByProfile(AdmProfile profile, AdmMenu admMenu);

	@Query("AdmProfile.findMenuParentByProfile")
	public List<AdmMenu> findMenuParentByProfile(AdmProfile profile);

	@Query("AdmProfile.findMenuByProfile")
	public List<AdmMenu> findMenuByProfile(AdmProfile profile, AdmMenu admMenu);

	@Query("AdmProfile.findUsersByProfile")
	public List<Long> findUsersByProfile(AdmProfile profile);

	@Query("AdmProfile.findMenuParentByIdPerfis")
	public List<AdmMenu> findMenuParentByIdPerfis(List<Long> listaIdProfile);

	@Query("AdmProfile.findMenuByIdPerfis")
	public List<AdmMenu> findMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu);

	@Query("AdmProfile.findAdminMenuParentByIdPerfis")
	public List<AdmMenu> findAdminMenuParentByIdPerfis(List<Long> listaIdProfile);

	@Query("AdmProfile.findAdminMenuByIdPerfis")
	public List<AdmMenu> findAdminMenuByIdPerfis(List<Long> listaIdProfile, AdmMenu admMenu);

}
