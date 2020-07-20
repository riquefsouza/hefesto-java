package br.com.hfs.base;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.util.AuthenticationUtil;

public abstract class BaseViewController {

	protected transient Logger log;	
	
	protected static final String SELECIONAR_REGISTRO = "Please select a table record to do this action!";
	
	protected static final String ERRO_SALVAR = "Transaction Error When Saving: ";
	
	protected static final String ERRO_DELETE = "Error Transaction When Excluding: ";
	
	protected String authServerURL;
	
	protected String accesToken;
	
	@Autowired
	protected MessageSource messageSource;
	
	public String getDesktopPage(){
		return "/index.html";
	}

	public void showPrimaryMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("primaryMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showSecondaryMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("secondaryMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showSuccessMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("successMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showWarningMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("warningMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}
	
	public void showInfoMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("infoMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showLightMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("lightMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showDarkMessage(RedirectAttributes attributes, String messageCode) {
		attributes.addFlashAttribute("darkMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showDangerMessage(RedirectAttributes attributes, Exception e) {
		attributes.addFlashAttribute("dangerMessage", e.getMessage());
	}


	public void showPrimaryMessage(ModelAndView mv, String messageCode) {
		mv.addObject("primaryMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showSecondaryMessage(ModelAndView mv, String messageCode) {
		mv.addObject("secondaryMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showSuccessMessage(ModelAndView mv, String messageCode) {
		mv.addObject("successMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showWarningMessage(ModelAndView mv, String messageCode) {
		mv.addObject("warningMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}
	
	public void showInfoMessage(ModelAndView mv, String messageCode) {
		mv.addObject("infoMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showLightMessage(ModelAndView mv, String messageCode) {
		mv.addObject("lightMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showDarkMessage(ModelAndView mv, String messageCode) {
		mv.addObject("darkMessage", messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale()));
	}

	public void showDangerMessage(ModelAndView mv, Exception e) {
		mv.addObject("dangerMessage", e.getMessage());
	}

	protected void logBindingResultErrors(BindingResult result, Logger log) {
		result.getAllErrors().forEach(item -> {
			log.error("ObjectName: " + item.getObjectName());
			Arrays.asList(item.getArguments()).forEach(arg -> log.error("Argument: " +	arg));
			log.error("Code: " + item.getCode());
			Arrays.asList(item.getCodes()).forEach(code -> log.error("Code: " + code));
			log.error("DefaultMessage: " + item.getDefaultMessage());
			log.info("Item: " + item.toString());
		});
	}	
	
	public void generateErrorMessage(String mensagem) {
	}

	public void generateErrorMessage(Exception e, String mensagem) {
	}

	public void generateInfoMessage(String mensagem) {
	}

	public void gerarMensagemAviso(String mensagem) {
	}

	public void generateErrorMessage(Exception e, String mensagem, String clientId) {
	}

	public void generateInfoMessage(String mensagem, String clientId) {
	}

	public void generateWarnMessage(String mensagem, String clientId) {
	}
	
	public static void addMessageInfoDialog(String mensagem) {
	}

	public static void addMessageWarnDialog(String mensagem) {
	}

	public static void addMessageErrorDialog(Exception e, String mensagem) {
	}
		
	public HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession();
		
	}
	
	public Map<String, String> getSessionAttributes() {
		HttpSession sessao = getSession();
		HashMap<String, String> mapa = new HashMap<String, String>();
		String atributo, valor;
		for (Enumeration<String> item = sessao.getAttributeNames(); item.hasMoreElements();) {
			atributo = item.nextElement();
			valor = sessao.getAttribute(atributo).toString();
			mapa.put(atributo, valor);
		}
		return mapa;
	}
	
	public void logSessionAttributes() {
		log.info("Sessão: [");
		for (Entry<String, String> item : getSessionAttributes().entrySet()) {
			log.info("\n" + item.getKey() + " = " + item.getValue());
		}
		log.info("]");
	}
	
	public AuthenticatedUserVO getAuthenticatedUser() {		
		return (AuthenticatedUserVO) getSession().getAttribute("userAuthenticated");
	}
	
	public void setUserAuthenticated(AuthenticatedUserVO usu){
		getSession().setAttribute("userAuthenticated", usu);
	}
	
	private String getIdMenu() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();		
		Map<String, String[]> params = attr.getRequest().getParameterMap();
		String[] sIdMenu = params.get("id");
		if (sIdMenu != null && sIdMenu.length > 0 && !sIdMenu[0].isEmpty()) {
			return sIdMenu[0];
		}
		return "";
	}
	
	public MenuVO getCurrentMenu(){
		String idMenu = getIdMenu();
		if (!idMenu.isEmpty())
			return getAuthenticatedUser().getMenu(idMenu);
		else 
			return null;
	}
	
	public Optional<ModelAndView> getPage(String pagina) {		
		if (AuthenticationUtil.getPrincipal().isPresent()) {
			ModelAndView mv = new ModelAndView(pagina);
			
			//mv.getModelMap().get(key)
			
			//if (!mv.getModel().containsKey("userLogged")) {		
				//mv.addObject("userLogged", getPrincipal().get());
			//}
			
			return Optional.of(mv);
		}		
		return Optional.empty();
	}	
	
}
