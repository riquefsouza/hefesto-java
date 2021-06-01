package br.com.hfs.base.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.ModeTestVO;
import br.com.hfs.admin.vo.PermissionVO;
import br.com.hfs.admin.vo.UserVO;
import br.com.hfs.util.JSONListConverter;

@Service
public class ModeTestService {

	public static final String TEST_PASSWORD = "sghdnvmfhr53";
	
	@Autowired
	private AdmParameterService admParameterService;

	@Autowired
	private AdmProfileService profileService;
	
	@Autowired
	private AdmUserService userService;

	private JSONListConverter<ModeTestVO> conv;
	
	public ModeTestVO load(String login) {
		List<ModeTestVO> lista = findAll();
		return lista.stream().filter(item -> login.equals(item.getLogin())).findFirst().orElse(null);
	}

	public List<ModeTestVO> findAll() {
		List<ModeTestVO> lista = new ArrayList<ModeTestVO>();
		String valor;
		try {
			valor = admParameterService.getValueByCode("MODO_TESTE");
		} catch (Exception e) {
			valor = "";
		}
		if (valor!=null && !valor.isEmpty()){
			conv = new JSONListConverter<ModeTestVO>();
			lista = conv.jsonToList(valor, new TypeReference<List<ModeTestVO>>() {
			});
		}
		return lista;
	}
	
	public AuthenticatedUserVO start(UserVO userVO, AuthenticatedUserVO authenticatedUser, boolean usuarioLDAP) throws Exception {
		authenticatedUser.setModeTest(false);
		
		List<ModeTestVO> lista = findAll();
		
		Optional<ModeTestVO> mtvo = lista.stream()
				.filter(vo -> vo.getActive().equals(true))
				.filter(vo -> vo.getLogin().equals(authenticatedUser.getUserName()))
				.findFirst();
		
		if (mtvo.isPresent()){
			authenticatedUser.setModeTest(true);
			authenticatedUser.setModeTestLogin(authenticatedUser.getUserName());
			
			String virtual = mtvo.get().getLoginVirtual();			
			if (!virtual.isEmpty()) {
				return mountAuthenticatedUser(userVO, authenticatedUser, usuarioLDAP);
			}
		}
		
		return authenticatedUser;
	}	

	public AuthenticatedUserVO mountAuthenticatedUser(UserVO user, AuthenticatedUserVO authenticatedUser, 
			boolean usuarioLDAP) throws Exception {
		
		authenticatedUser.setUserName(user.getLogin());
		authenticatedUser.setDisplayName(user.getName());
		authenticatedUser.setEmail(user.getEmail());
		authenticatedUser.setListPermission(profileService.getPermission(authenticatedUser));
		
		if (!authenticatedUser.getListPermission().isEmpty()){
			
			List<Long> listaIdPerfis = new ArrayList<Long>();
			for (PermissionVO permissao: authenticatedUser.getListPermission()) {
				listaIdPerfis.add(permissao.getProfile().getId());
			}

			authenticatedUser.setListMenus(
					profileService.findMenuParentByProfile(listaIdPerfis));
			
			authenticatedUser.setListAdminMenus(
					profileService.findAdminMenuParentByProfile(listaIdPerfis));
			
		} else {
			throw new Exception("Usuário sem perfil associado!");
		}
		
		if (authenticatedUser.isModeTest()){
			authenticatedUser.setModeTestLoginVirtual(user.getLogin());
		}
		
		return authenticatedUser; 
	}
	
	public boolean enableTestPassword() {
		Boolean habilitar = false;
		String valor;
		try {
			valor = admParameterService.getValueByCode("HABILITAR_SENHA_TESTE");
			habilitar = Boolean.parseBoolean(valor);
		} catch (Exception e) {
			habilitar = false;
		}
		
		return habilitar;
	}

	public Optional<AdmUserDTO> autenticarViaSenhaTeste(String login, String password) {
		Optional<AdmUserDTO> userDTO = Optional.empty();		
		if (!login.isEmpty() && !password.isEmpty()) {
			
			String csenha = DigestUtils.sha512Hex(ModeTestService.TEST_PASSWORD);
			
			if (password.equals(csenha)) {
				userDTO = userService.findByLikeEmail(login)
						.stream()
						.findFirst();				
			}
		}
		return userDTO;
	}
	
}
