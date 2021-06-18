package br.com.hfs.base;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.vo.AuthenticatedUserVO;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.util.ExporterUtil;
import br.com.hfs.util.exceptions.ExceptionUtil;
import br.com.hfs.util.filter.NavegationFilter;

@Named
public abstract class BaseViewController {

	/** The log. */
	@Inject
	protected transient Logger log;	

	/** The context. */
	@Inject
	protected FacesContext context;
	
	/** The pdf utils. */
	@Inject
	protected ExporterUtil pdfUtils;

	protected static final String SELECT_RECORD = "Please select a record from the table to proceed with this action.";
	
	protected static final String ERROR_SAVE = "Transaction error to save: ";
	
	protected static final String ERROR_DELETE = "Transaction error to delete: ";
		
	public String getDesktopPage(){
		return "/private/"+NavegationFilter.DESKTOP_SCREEN;
	}

	public void generateErrorMessage(String message) {
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
	}

	public void generateErrorMessage(Exception e, String message) {
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ExceptionUtil.getErrors(log, e, message)));
	}

	public void generateInformativeMessage(String message) {
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, message));
	}

	public void generateWarningMessage(String message) {
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, null, message));
	}

	public void generateErrorMessage(Exception e, String message, String clientId) {
		context.addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ExceptionUtil.getErrors(log, e, message)));
	}

	public void generateInformativeMessage(String message, String clientId) {
		context.addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, message));
	}

	public void generateWarningMessage(String message, String clientId) {
		context.addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_WARN, null, message));
	}

	public static void addMessageInfoDialog(String message) {
		PrimeFaces.current().dialog()
			.showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", message));
	}

	public static void addMessageWarningDialog(String message) {
		PrimeFaces.current().dialog()
			.showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", message));
	}

	public static void addMessageErrorDialog(Exception e, String message) {
		PrimeFaces.current().dialog()
			.showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
					ExceptionUtil.getErrors(LogManager.getLogger(), e, message)));
	}
	
	public HttpSession getSession() {
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return hsr.getSession();		
	}
	
	public Map<String, String> getAttributesSession() {
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
	
	public void logAttributesSession() {
		log.info("Session: [");
		for (Entry<String, String> item : getAttributesSession().entrySet()) {
			log.info("\n" + item.getKey() + " = " + item.getValue());
		}
		log.info("]");
	}
	
	public void preProcessPDF(Object document, String reportTitle) 
			throws IOException, BadElementException, DocumentException {
		pdfUtils.preProcessaPDF(document, reportTitle);
	}

	public AuthenticatedUserVO getAuthenticatedUser() {		
		return (AuthenticatedUserVO) getSession().getAttribute("authenticatedUser");
	}

	/**
	 * Gets the id menu.
	 *
	 * @return the id menu
	 */
	private String getIdMenu() {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String sIdMenu = params.get("id");
		if (sIdMenu != null && !sIdMenu.isEmpty()) {
			return sIdMenu;
		}
		return "";
	}
	
	/**
	 * Gets the menu atual.
	 *
	 * @return the menu atual
	 */
	public MenuVO getMenuAtual(){
		String idMenu = getIdMenu();
		if (!idMenu.isEmpty())
			return getAuthenticatedUser().getMenu(idMenu);
		else 
			return null;
	}
	
	/**
	 * Data mes ano view change.
	 *
	 * @param campo the campo
	 * @param formularioCampo the formulario campo
	 * @return the date
	 */
	public Date dataMesAnoViewChange(Date campo, String formularioCampo){
		Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
		Calendar c = Calendar.getInstance();
		if (campo == null)
			campo = c.getTime();
		else
			c.setTime(campo);
		int ano = Integer.parseInt(requestParams.get(formularioCampo+"_year"));
		int mes = Integer.parseInt(requestParams.get(formularioCampo+"_month"))-1;		
		c.set(ano, mes, 1, 0, 0, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}	

}
