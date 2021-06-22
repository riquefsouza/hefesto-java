package br.com.hfs.admin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.hfs.admin.controller.dto.MenuItemDTO;
import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.repository.AdmProfileRepository;
import br.com.hfs.admin.repository.AdmUserRepository;
import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.admin.vo.PermissionVO;
import br.com.hfs.admin.vo.ProfileVO;
import br.com.hfs.base.BaseService;
import jakarta.inject.Inject;

public class AdmProfileService extends BaseService<AdmProfile, Long, AdmProfileRepository> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AdmMenuService admMenuService;

	//@Inject
	//private AdmUserService admUserService;
	
	@Inject
	private AdmUserRepository admUserRepository;
	
	public List<AdmProfile> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmProfile> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
	public HashSet<AdmProfile> getRoles(AuthenticatedUserVO authenticatedUser) {
		HashSet<AdmProfile> hs = new HashSet<AdmProfile>();

		AdmUser admUser = new AdmUser(authenticatedUser.getUser());
		List<AdmProfile> lst = repository.findProfilesByUser(admUser.getId());		
		
		for (AdmProfile g : lst) {
			hs.add(g);
		}
		AdmProfile g = findById(1L).get(); // GERAL
		hs.add(g);

		return hs;
	}
	
	public List<AdmProfile> findByGeneral(Boolean geral) {
		return repository.findByGeneral(geral);
	}
	
	public List<PermissionVO> getPermission(AuthenticatedUserVO authenticatedUser) {
		List<PermissionVO> lista = new ArrayList<PermissionVO>();
		PermissionVO permission;
		
		AdmUser admUser = new AdmUser(authenticatedUser.getUser());
		List<AdmProfile> profiles = repository.findProfilesByUser(admUser.getId());
		List<AdmProfile> perfisGeral = repository.findByGeneral(Boolean.TRUE);
		for (AdmProfile perfilGeral : perfisGeral) {
			if (!profiles.contains(perfilGeral)) {
				profiles.add(perfilGeral);
			}
		}

		for (AdmProfile profile : profiles) {
			permission = new PermissionVO();

			permission.setProfile(profile.toProfileVO());

			for (AdmPage admPage : profile.getAdmPages()) {
				permission.getPages().add(admPage.toPageVO());
			}
			
			lista.add(permission);
		}
		
		return lista;
	}
	
	public List<AdmMenu> findAdminMenuParentByProfile(AdmProfile profile){
		List<AdmMenu> lista = repository.findAdminMenuParentByProfile(profile);
		for (AdmMenu admMenu : lista) {
			admMenu.setAdmSubMenus(repository.findAdminMenuByProfile(profile, admMenu));
		}
		return lista;
	}
		
	public List<AdmMenu> findMenuParentByProfile(AdmProfile profile){
		List<AdmMenu> lista = repository.findMenuParentByProfile(profile);
		for (AdmMenu admMenu : lista) {
			admMenu.setAdmSubMenus(repository.findMenuByProfile(profile, admMenu));
		}
		return lista;
	}
		
	public List<MenuVO> findAdminMenuParentByProfile(ProfileVO profile){
		AdmProfile profileAdminPai = new AdmProfile(profile);
		List<AdmMenu> listaAdminMenuParent = this.findAdminMenuParentByProfile(profileAdminPai);						
		return admMenuService.toListMenuVO(listaAdminMenuParent);
	}
	
	public List<MenuVO> findMenuParentByProfile(ProfileVO profile){
		AdmProfile profilePai = new AdmProfile(profile);
		List<AdmMenu> listaMenuParent = this.findMenuParentByProfile(profilePai);
		return admMenuService.toListMenuVO(listaMenuParent);		
	}
	
	public List<AdmUser> findUsersByProfile(AdmProfile profile){
		List<AdmUser> lista = new ArrayList<AdmUser>();
		List<Long> listaCod = repository.findUsersByProfile(profile);
		
		//admUserService.setAdmProfileService(this);
		for (Long item : listaCod) {
			lista.add(admUserRepository.findById(item).get());
		}
		
		return lista;
	}
	
	public List<AdmProfile> findProfilesByUser(Long idUser) {
		return repository.findProfilesByUser(idUser);
	}

	public List<AdmProfile> findProfilesByPage(Long idPage) {
		return repository.findProfilesByPage(idPage);
	}
	
	public List<AdmMenu> findMenuParentByIdPerfis(List<Long> listaIdProfile){
		List<AdmMenu> lista = repository.findMenuParentByIdPerfis(listaIdProfile);
		for (AdmMenu admMenu : lista) {
			admMenu.setAdmSubMenus(repository.findMenuByIdPerfis(listaIdProfile, admMenu));
		}
		return lista;
	}
	
	public List<MenuVO> findMenuParentByProfile(List<Long> listaIdProfile){
		List<AdmMenu> listaMenuParent = this.findMenuParentByIdPerfis(listaIdProfile);
		return admMenuService.toListMenuVO(listaMenuParent);		
	}
	
	public List<AdmMenu> findAdminMenuParentByIdPerfis(List<Long> listaIdProfile){
		List<AdmMenu> lista = repository.findAdminMenuParentByIdPerfis(listaIdProfile);
		for (AdmMenu admMenu : lista) {
			admMenu.setAdmSubMenus(repository.findAdminMenuByIdPerfis(listaIdProfile, admMenu));
		}
		return lista;
	}
	
	public List<MenuVO> findAdminMenuParentByProfile(List<Long> listaIdProfile){
		List<AdmMenu> listaAdminMenuParent = this.findAdminMenuParentByIdPerfis(listaIdProfile);						
		return admMenuService.toListMenuVO(listaAdminMenuParent);
	}

	public List<AdmPage> findPagesByProfile(AdmProfile profile){
		return repository.findPagesByProfile(profile);
	}
	
	public List<MenuItemDTO> mountMenuItem(List<Long> listaIdProfile) {
		List<MenuItemDTO> lista = new ArrayList<MenuItemDTO>();
				
		this.findMenuParentByProfile(listaIdProfile).forEach(menu -> {			
			List<MenuItemDTO> item = new ArrayList<MenuItemDTO>();
			
			menu.getSubMenus().forEach(submenu -> {
				MenuItemDTO submenuVO = new MenuItemDTO(submenu.getDescription(), submenu.getUrl());
				item.add(submenuVO);
			});
			
			MenuItemDTO vo = new MenuItemDTO(menu.getDescription(), menu.getUrl(), item);
			lista.add(vo);
		});
		
		this.findAdminMenuParentByProfile(listaIdProfile).forEach(menu -> {			
			List<MenuItemDTO> item = new ArrayList<MenuItemDTO>();
			
			menu.getSubMenus().forEach(submenu -> {
				MenuItemDTO submenuVO = new MenuItemDTO(submenu.getDescription(), submenu.getUrl());
				item.add(submenuVO);
			});
			
			MenuItemDTO vo = new MenuItemDTO(menu.getDescription(), menu.getUrl(), item);
			lista.add(vo);
		});
		
		return lista;
	}
	
}
