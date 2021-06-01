package br.com.hfs.base.security;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.admin.vo.PageVO;
import br.com.hfs.admin.vo.PermissionVO;
import br.com.hfs.admin.vo.UserVO;

@Service
public class SystemService {
	
	private static Logger log = LogManager.getLogger(SystemService.class);
	
	@Autowired
	private AuthenticatedUserVO authenticatedUser;
	
	@Autowired
	private ModeTestService modeTestService;
	
	@Autowired
	private AdmUserService userService;
	
	//@Autowired
	//private ApplicationUtil applicationUtil;

	public boolean authenticate(AdmUser admUser) throws Exception {
		UserVO userVO;
		
		/*
		if (MailUtilImpl.isValidEmail(login)) {
			func = admAutenticadoBC.autenticar(login, password);
			
			if (func.isPresent()){
				String ldapDN = "CN="+login+",OU="+func.get().getSetor();
				userVO = new UserVO(func.get().getId(), func.get().getEmail(),
						ldapDN, login, func.get().getNome(), func.get().getCpf());
				
				if (func.get().getPrimeiroAcesso()){
					this.authenticatedUser.setPrimeiroAcesso(true);
					this.authenticatedUser.setUsuarioLDAP(false);
					this.authenticatedUser.setUsuario(userVO);
					aplicacaoUtil.setUsuarioAutenticado(this.authenticatedUser);
					//admAutenticadoBC.atualizarSenhaByCPF(null, false, func.get().getCpf().longValue());
				} else {
					authenticatedUser.setPrimeiroAcesso(false);
					setPropriedadesAutenticado(login, userVO);	
				}
				
				return true;	
			} else {
				return false;
			}
		}
		
		if (modoTesteBC.habilitarSenhaTeste()){
			func = modoTesteBC.autenticarViaSenhaTeste(login, password);
			
			if (func.isPresent()){
				String ldapDN = "CN="+login+",OU="+func.get().getSetor()+",OU=TRT,DC=trtrio,DC=gov,DC=br";
				userVO = new UsuarioVO(func.get().getId(), func.get().getEmail(),
						ldapDN, login, func.get().getNome(), func.get().getCpf());
				setPropriedades(login, userVO);
				return true;
			}
		}
		
		if (autenticarViaLDAP(login, password)) {
			userVO = new UsuarioVO(ldapUtil.getMatricula(), ldapUtil.getEmail(), 
					ldapUtil.getLdapDN(), ldapUtil.getLogin(), ldapUtil.getNome());
			setPropriedades(login, userVO);
			return true;
		}
		
		return false;
		*/
		
		userVO = new UserVO(admUser.getId(), admUser.getEmail(), admUser.getLogin(), 
				admUser.getName(), admUser.getActive());
		setProperties(admUser.getLogin(), userVO);
		return true;
		
	}

	private void setProperties(String login, UserVO userVO) throws Exception {
		this.authenticatedUser.setUserName(login);
		//if (!aplicacaoUtil.isDebugMode() && aplicacaoConfig.isHabilitarControlePerfil()) {
		/*
			AdmFuncionarioDTO funcionario;
			Long matricula = userVO.getMatricula();
			
			if (ldapUtil.isCpf()){
				BigDecimal cpf = new BigDecimal(matricula);
				funcionario = admFuncionarioBC.findByCPF(cpf);
			} else {
				funcionario = admFuncionarioBC.load(matricula);
			}
			
			if (funcionario != null) {
				this.authenticatedUser = modoTesteBC.montarUsuarioAutenticado(login, 
						funcionario.toFuncionarioVO(), this.authenticatedUser, true);
			}
		*/	
			this.authenticatedUser.setUser(userVO);
			this.authenticatedUser = modeTestService.mountAuthenticatedUser(userVO, 
					this.authenticatedUser, true);
		
			//try {
				this.authenticatedUser = modeTestService.start(userVO, this.authenticatedUser, true);
				
				if (this.authenticatedUser.isModeTest()
						&& !this.authenticatedUser.getModeTestLoginVirtual().isEmpty()){
					
					this.authenticatedUser.setUser(userService.getUser(
							this.authenticatedUser.getUserName(),
							this.authenticatedUser.getDisplayName(),
							this.authenticatedUser.getEmail(), false).toUserVO());											
					
				} else {			
					this.authenticatedUser.setUser(userVO);											
				}
				
				//applicationUtil.setAuthenticatedUser(this.authenticatedUser);
				
			//} catch (Exception e) {
				//gerarMensagemErro(e, e.getMessage());
			//}
			
			log.info(this.authenticatedUser.getUserName() + ", Profiles: "
					+ this.authenticatedUser.getListPermission().toString());
			showProfileURL();
			showMenus();
		//}
	}
	
	public void showProfileURL() {
		for (PermissionVO permissao : this.authenticatedUser.getListPermission()) {
			for (PageVO admPagina : permissao.getPages()) {
				log.info("Profile: " + permissao.getProfile().getDescription() + " -> Page URL: " + admPagina.getUrl());
			}
		}
	}

	public void showMenus() {		
		for (MenuVO menu : this.authenticatedUser.getListMenus()) {
			log.info("Menu: " + menu.toString());
		}
		for (MenuVO menu : this.authenticatedUser.getListAdminMenus()) {
			log.info("Menu Admin: " + menu.toString());
		}		
	}
	
	public List<MenuVO> getListaMenus() {
		return this.authenticatedUser.getListMenus();
	}

	public List<MenuVO> getListaAdminMenus() {
		return this.authenticatedUser.getListAdminMenus();
	}

	public PageVO getPagina(Long idMenu) {
		return this.authenticatedUser.getPageByMenu(idMenu);
	}
	
	public AuthenticatedUserVO getAuthenticatedUser() {
		return authenticatedUser;
	}
	
}
