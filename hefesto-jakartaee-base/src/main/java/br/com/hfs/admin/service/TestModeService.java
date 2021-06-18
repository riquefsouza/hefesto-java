package br.com.hfs.admin.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.PermissionVO;
import br.com.hfs.admin.vo.TestModeVO;
import br.com.hfs.admin.vo.UserVO;
import br.com.hfs.util.JSONListConverter;

public class TestModeService implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final String TEST_PASSWORD = "6594fb4f65fcda7a286ac1b60958839842407ac8bab3638815ee26f9bddccc12";

	@Inject
	private AdmProfileService admProfileService;

	@Inject
	private AdmUserService admUserService;

	@Inject
	private AdmParameterService admParameterService;
	
	/** The conv. */
	@Inject
	private JSONListConverter<TestModeVO> conv;

	/**
	 * Load.
	 *
	 * @param login the login
	 * @return the modo teste VO
	 */
	public TestModeVO load(String login) {
		List<TestModeVO> lista = findAll();
		return lista.stream().filter(item -> login.equals(item.getLogin())).findFirst().orElse(null);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<TestModeVO> findAll() {
		List<TestModeVO> lista = new ArrayList<TestModeVO>();
		String valor;
		try {
			valor = admParameterService.getValueByCode("TEST_MODE");
		} catch (Exception e) {
			valor = "";
		}
		if (valor!=null && !valor.isEmpty()){
			lista = conv.jsonToList(valor, new TypeReference<List<TestModeVO>>() {
			});
		}
		return lista;
	}
		
	/**
	 * Iniciar.
	 *
	 * @param usuario the usuario
	 * @return the usuario autenticado VO
	 * @throws Exception the exception
	 */
	public AuthenticatedUserVO iniciar(AuthenticatedUserVO usuario) throws Exception {
		usuario.setModeTest(false);
		
		List<TestModeVO> lista = findAll();
		
		Optional<TestModeVO> mtvo = lista.stream()
				.filter(vo -> vo.getActive().equals(true))
				.filter(vo -> vo.getLogin().equals(usuario.getUserName()))
				.findFirst();
		
		if (mtvo.isPresent()){
			usuario.setModeTest(true);
			usuario.setModeTestLogin(usuario.getUserName());
			
			String virtual = mtvo.get().getLoginVirtual();			
			if (!virtual.isEmpty()) {
				Optional<UserVO> userVO = 
						admUserService.findByLikeEmail(virtual)
						.stream()
						.findFirst();
				
				if (userVO.isPresent())
					return mountAuthenticatedUser(virtual, userVO.get(), usuario);
				else
					throw new Exception("Funcionário inativo ou não existe!");		
			}
		}
		
		return usuario;
	}

	public AuthenticatedUserVO mountAuthenticatedUser(String login, UserVO userVO, 
			AuthenticatedUserVO authenticatedUser) throws Exception {
		
		authenticatedUser.setUserName(login);
		authenticatedUser.setUser(userVO);
		authenticatedUser.setDisplayName(authenticatedUser.getUser().getName());
		authenticatedUser.setEmail(authenticatedUser.getUser().getEmail());
		authenticatedUser.setListPermission(admProfileService.getPermission(authenticatedUser));

		if (!authenticatedUser.getListPermission().isEmpty()){
			
			List<Long> listaIdPerfis = new ArrayList<Long>();
			for (PermissionVO permissao: authenticatedUser.getListPermission()) {
				listaIdPerfis.add(permissao.getProfile().getId());
			}

			authenticatedUser.setListMenus(
					admProfileService.findMenuParentByProfile(listaIdPerfis));
			
			authenticatedUser.setListAdminMenus(
					admProfileService.findAdminMenuParentByProfile(listaIdPerfis));
			
		} else {
			throw new Exception("User without associated profile!");
		}
		
		if (authenticatedUser.isModeTest()){
			authenticatedUser.setModeTestLoginVirtual(login);
		}
		
		return authenticatedUser; 
	}

	/**
	 * Habilitar senha teste.
	 *
	 * @return true, if successful
	 */
	public boolean habilitarSenhaTeste() {
		Boolean habilitar = false;
		String valor;
		try {
			valor = admParameterService.getValueByCode("ENABLE_TEST_PASSWORD");
			habilitar = Boolean.valueOf(valor);
		} catch (Exception e) {
			habilitar = false;
		}
		
		return habilitar;
	}

	/**
	 * Autenticar via senha teste.
	 *
	 * @param login the login
	 * @param senha the senha
	 * @return the optional
	 */
	public Optional<UserVO> autenticarViaSenhaTeste(String login, String senha) {
		Optional<UserVO> userVO = Optional.empty();		
		if (!login.isEmpty() && !senha.isEmpty()) {
			
			String csenha = DigestUtils.sha512Hex(TestModeService.TEST_PASSWORD);
			
			if (senha.equals(csenha)) {
				userVO = admUserService.findByLikeEmail(login)
						.stream()
						.findFirst();				
			}
		}
		return userVO;
	}
	
}
