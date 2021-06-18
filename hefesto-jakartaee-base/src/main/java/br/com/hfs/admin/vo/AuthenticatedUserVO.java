package br.com.hfs.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("authenticatedUser")
@SessionScoped
public class AuthenticatedUserVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	private String userName;

	/** The display name. */
	private String displayName;
	
	/** The email. */
	private String email;
	
	/** The lista permissao. */
	private List<PermissionVO> listPermission;

	/** The lista menus. */
	private List<MenuVO> listMenus;
	
	/** The lista admin menus. */
	private List<MenuVO> listAdminMenus;
	
	/** The usuario. */
	private UserVO user;
	
	/** The modo teste. */
	private boolean modeTest;
	
	/** The modo teste login. */
	private String modeTestLogin;
	
	/** The login virtual. */
	private String modeTestLoginVirtual;
	
	/**
	 * Instantiates a new usuario.
	 */
	public AuthenticatedUserVO() {
		super();
		
		this.listPermission = new ArrayList<PermissionVO>();
		this.user = new UserVO();
		this.listMenus = new ArrayList<MenuVO>();
		this.listAdminMenus = new ArrayList<MenuVO>();
				
		clean();
		
		this.modeTest = false;
		this.modeTestLogin = "";
		this.modeTestLoginVirtual = "";
	}

	/**
	 * Limpar.
	 */
	public void clean() {
		this.userName = "";
		this.displayName = "";
		this.email = "";
		this.listPermission.clear();
		this.listMenus.clear();
		this.listAdminMenus.clear();
		this.user.clean();
		this.modeTestLogin = "";
		this.modeTestLoginVirtual = "";
	}
		
	/**
	 * Instantiates a new usuario autenticado VO.
	 *
	 * @param userName
	 *            the user name
	 */
	public AuthenticatedUserVO(String userName) {
		super();
		this.userName = userName;
	}

	/**
	 * Pega o the user name.
	 *
	 * @return o the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Atribui o the user name.
	 *
	 * @param userName
	 *            o novo the user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PermissionVO> getListPermission() {
		return listPermission;
	}

	public void setListPermission(List<PermissionVO> listPermission) {
		this.listPermission = listPermission;
	}

	public List<MenuVO> getListMenus() {
		return listMenus;
	}

	public void setListMenus(List<MenuVO> listMenus) {
		this.listMenus = listMenus;
	}

	public List<MenuVO> getListAdminMenus() {
		return listAdminMenus;
	}

	public void setListAdminMenus(List<MenuVO> listAdminMenus) {
		this.listAdminMenus = listAdminMenus;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public boolean isModeTest() {
		return modeTest;
	}

	public void setModeTest(boolean modeTest) {
		this.modeTest = modeTest;
	}

	public String getModeTestLogin() {
		return modeTestLogin;
	}

	public void setModeTestLogin(String modeTestLogin) {
		this.modeTestLogin = modeTestLogin;
	}

	public String getModeTestLoginVirtual() {
		return modeTestLoginVirtual;
	}

	public void setModeTestLoginVirtual(String modeTestLoginVirtual) {
		this.modeTestLoginVirtual = modeTestLoginVirtual;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticatedUserVO other = (AuthenticatedUserVO) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}	

	/**
	 * Gets the profile.
	 *
	 * @param idProfile
	 *            the id profile
	 * @return the profile
	 */
	public ProfileVO getProfile(Long idProfile){
		ProfileVO admProfile = null;
		for (PermissionVO permissaoVO : listPermission) {
			if (permissaoVO.getProfile().getId() == idProfile){
				admProfile = permissaoVO.getProfile(); 
				break;
			}
		}
		return admProfile;
	}

	/**
	 * Checks if is profile.
	 *
	 * @param idProfile
	 *            the id profile
	 * @return true, if is profile
	 */
	public boolean isProfile(Long idProfile){
		return (getProfile(idProfile)!=null);
	}

	/**
	 * Existe profile.
	 *
	 * @param profile
	 *            the profile
	 * @return the profile
	 */
	public ProfileVO getProfile(String profile){
		ProfileVO admProfile = null;
		for (PermissionVO permissaoVO : listPermission) {
			if (permissaoVO.getProfile().getDescription().equalsIgnoreCase(profile)){
				admProfile = permissaoVO.getProfile(); 
				break;
			}
		}
		return admProfile;
	}

	/**
	 * Checks if is profile.
	 *
	 * @param profile
	 *            the profile
	 * @return true, if is profile
	 */
	public boolean isProfile(String profile){
		return (getProfile(profile)!=null);
	}
	
	/**
	 * Gets the profile general.
	 *
	 * @return the profile general
	 */
	public ProfileVO getProfileGeneral(){
		ProfileVO admProfile = null;
		for (PermissionVO permissaoVO : listPermission) {
			if (permissaoVO.getProfile().getGeneral()){
				admProfile = permissaoVO.getProfile(); 
				break;
			}
		}
		return admProfile;
	}

	/**
	 * Gets the profile administrator.
	 *
	 * @return the profile administrator
	 */
	public ProfileVO getProfileAdministrator(){
		ProfileVO admProfile = null;
		for (PermissionVO permissaoVO : listPermission) {
			if (permissaoVO.getProfile().getAdministrator()){
				admProfile = permissaoVO.getProfile(); 
				break;
			}
		}
		return admProfile;
	}
	
	/**
	 * Checks if is general.
	 *
	 * @return true, if is general
	 */
	public boolean isGeneral(){
        ProfileVO profile = this.getProfileGeneral();
        if (profile!=null){
        	return profile.getGeneral();
        }
        return false;
	}

	/**
	 * Checks if is administrator.
	 *
	 * @return true, if is administrator
	 */
	public boolean isAdministrator(){
        ProfileVO profile = this.getProfileAdministrator();
        if (profile!=null){
        	return profile.getAdministrator();
        }
        return false;
	}

	/**
	 * Gets the page by menu.
	 *
	 * @param idMenu
	 *            the id menu
	 * @return the page by menu
	 */
	public PageVO getPageByMenu(Long idMenu) {
		PageVO admPage = null;
		
		if (listMenus!= null && !listMenus.isEmpty()){
			for (MenuVO admMenu : listMenus) {
				admPage = admMenu.getPage();
				break;
			}			
		}
		
		if (listAdminMenus!= null && !listAdminMenus.isEmpty()){
			for (MenuVO admMenu : listAdminMenus) {
				admPage = admMenu.getPage();
				break;
			}			
		}
		
		return admPage;
	}
	
	public boolean hasPermission(String url, String tela) {

		if (url == null) {
			return false;
		}
		int pos = url.indexOf("private");
		url = pos > -1 ? url.substring(pos + 8, url.length()) : url;

		if (url.equals(tela)) {
			return true;
		}

		for (PermissionVO permissao : this.getListPermission()) {
			for (PageVO admPage : permissao.getPages()) {
				if (admPage.getUrl().equals(url)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Gets the menu.
	 *
	 * @param sidMenu the sid menu
	 * @return the menu
	 */
	public MenuVO getMenu(String sidMenu){
		MenuVO menu = null;
		Long idMenu = Long.valueOf(sidMenu);
		
		menu = listMenus.stream()
			.flatMap(pai -> pai.getSubMenus().stream())			
			.filter(submenu -> submenu.getId().equals(idMenu))
			.findFirst()
			.orElse(null);

		if (menu == null) { 
			menu = listAdminMenus.stream()
					.flatMap(pai -> pai.getSubMenus().stream())			
					.filter(submenu -> submenu.getId().equals(idMenu))
					.findFirst()
					.orElse(null);			
		}
		
		return menu;
	}

}
