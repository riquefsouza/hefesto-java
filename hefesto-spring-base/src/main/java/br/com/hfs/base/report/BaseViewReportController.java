/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base.report;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.hfs.base.BaseViewController;
import br.com.hfs.util.pdf.PdfException;
import br.com.hfs.util.pdf.PdfUtil;

public abstract class BaseViewReportController
		extends BaseViewController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String reportType;

	/** The renderer. */
	@Autowired
	private ReportRender renderer;
	
	/**
	 * Instantiates a new base view relatorio controller.
	 */
	public BaseViewReportController() {
		super();
		reportType = ReportTypeEnum.PDF.name();
	}
	
	@PostConstruct
	public void init() {
		//reportType = ReportTypeEnum.PDF.name();
	}

	/**
	 * Gets the parametros.
	 *
	 * @return the parametros
	 */
	public Map<String, Object> getParametros() {
		Map<String, Object> params = new HashMap<String, Object>();

	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    ServletContext sc = (ServletContext) attr.getRequest().getServletContext();
		String caminho = sc.getRealPath(File.separator);
		
		params.put("IMAGE", caminho + "/WEB-INF/static/img/logo.png");
		return params;
	}

	public String export(HttpServletResponse response, Optional<byte[]> report, ReportParamsDTO reportParamsDTO) {
		if (report.isPresent()) {
			this.renderer.render(response, report.get(), reportParamsDTO);
		}
		
		return "";
	}
	
	public void export(IBaseReport relatorio, Collection<?> colecao, Map<String, Object> params,		
			boolean forcarDownload) {
		export(relatorio, colecao, params, forcarDownload, true);
	}
	
	public byte[] export(IBaseReport relatorio, Collection<?> colecao, Map<String, Object> params,		
			boolean forcarDownload, boolean renderizar) {
		byte[] buffer = null;
		
		if (colecao!=null) { // && !colecao.isEmpty()) {
			ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(reportType);
			
			buffer = relatorio.export(colecao, params, tipoRel);
			
			if (renderizar) {
				this.renderer.render(buffer, tipoRel, "report." + tipoRel.name().toLowerCase(), forcarDownload);
			}
			
		} else {
			generateInfoMessage("No records found to export report.");
		}
		
		return buffer;
	}

	public byte[] exportTogetherAlternate(IBaseReport relatorio1, IBaseReport relatorio2,
			boolean forcarDownload, boolean renderizar) {
		byte[] buffer = null;
		ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(reportType);

		buffer = relatorio1.exportJuntoAlternado(relatorio1.getReportObject(), relatorio2.getReportObject(), tipoRel);
		
		if (renderizar) {
			this.renderer.render(buffer, tipoRel, "report." + tipoRel.name().toLowerCase(), forcarDownload);
		}
		
		return buffer;
	}

	public void exportPDFTogether(byte[] buffer1, byte[] buffer2, boolean forcarDownload) throws PdfException {
		if (buffer1!=null && buffer2!=null) {
			ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(reportType);
			
			PdfUtil pu = new PdfUtil();
			byte[] buffer = pu.joinAlternate(buffer1, buffer2);
			
			this.renderer.render(buffer, tipoRel, "report." + tipoRel.name().toLowerCase(), forcarDownload);			
		} else {
			generateInfoMessage("No records found to export report.");
		}		
	}

	public String getRealPath(){
		//ServletContext sc = (ServletContext) context.getExternalContext().getContext();
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    ServletContext sc = (ServletContext) attr.getRequest().getServletContext();		
		String caminho = sc.getRealPath("/WEB-INF/classes/reports");
		return caminho + File.separator;
	}

	public List<ReportGroupVO> getListReportType() {
		List<ReportGroupVO> listaVO = new ArrayList<ReportGroupVO>();
		ReportGroupVO grupoVO;
		List<ReportTypeEnum> listaEnum = Arrays.asList(ReportTypeEnum.values());
		List<ReportTypeEnum> subtipos;
		
		for (String grupo : ReportTypeEnum.getGroups()) {
			String igrupo = "";
			
			subtipos = listaEnum
				.stream()
				.filter(item -> item.getGroup().equals(grupo))
				.collect(Collectors.toList());
			
			if (grupo.equals(ReportTypeEnum.getGroups()[0])) 
				igrupo = messageSource.getMessage("reportTypeGroups.docs", null, LocaleContextHolder.getLocale());		
			if (grupo.equals(ReportTypeEnum.getGroups()[1])) 
				igrupo = messageSource.getMessage("reportTypeGroups.sheets", null, LocaleContextHolder.getLocale());
			if (grupo.equals(ReportTypeEnum.getGroups()[2])) 
				igrupo = messageSource.getMessage("reportTypeGroups.text", null, LocaleContextHolder.getLocale());
			if (grupo.equals(ReportTypeEnum.getGroups()[3])) 
				igrupo = messageSource.getMessage("reportTypeGroups.others", null, LocaleContextHolder.getLocale());

			grupoVO = new ReportGroupVO(igrupo, subtipos);
			listaVO.add(grupoVO);
		}
		
		return listaVO;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String cancel() {
		return getDesktopPage();
	}

}
