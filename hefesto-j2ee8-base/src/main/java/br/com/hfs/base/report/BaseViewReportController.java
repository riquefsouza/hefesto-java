package br.com.hfs.base.report;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import br.com.hfs.base.BaseViewController;
import br.com.hfs.util.pdf.PdfException;
import br.com.hfs.util.pdf.PdfUtil;
import net.sf.jasperreports.engine.JRParameter;

@Named
public abstract class BaseViewReportController extends BaseViewController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<SelectItem> listTypeReport;
	
	private String typeReport;
	
	@Inject
	private ReportRender renderer;
	
	public BaseViewReportController() {
		super();
	}

	@PostConstruct
	public void init() {
		typeReport = ReportTypeEnum.PDF.name();
		//listTypeReport = Arrays.asList(ReportTypeEnum.values());
		
		SelectItemGroup gDocumentos = new SelectItemGroup("Documents");
		gDocumentos.setSelectItems(new SelectItem[] {
				new SelectItem(ReportTypeEnum.PDF.name(), ReportTypeEnum.PDF.getDescription(), ReportTypeEnum.PDF.getDescription()), 
				new SelectItem(ReportTypeEnum.DOCX.name(), ReportTypeEnum.DOCX.getDescription(), ReportTypeEnum.DOCX.getDescription()),
				new SelectItem(ReportTypeEnum.RTF.name(), ReportTypeEnum.RTF.getDescription(), ReportTypeEnum.RTF.getDescription()),
				new SelectItem(ReportTypeEnum.ODT.name(), ReportTypeEnum.ODT.getDescription(), ReportTypeEnum.ODT.getDescription()) } );

		SelectItemGroup gPlanilhas = new SelectItemGroup("Spreadsheets");
		gPlanilhas.setSelectItems(new SelectItem[] {
				//new SelectItem(ReportTypeEnum.XLS.name(), ReportTypeEnum.XLS.getDescription(), ReportTypeEnum.XLS.getDescription()), 
				new SelectItem(ReportTypeEnum.XLSX.name(), ReportTypeEnum.XLSX.getDescription(), ReportTypeEnum.XLSX.getDescription()),
				new SelectItem(ReportTypeEnum.ODS.name(), ReportTypeEnum.ODS.getDescription(), ReportTypeEnum.ODS.getDescription()) } );

		SelectItemGroup gTextoPuro = new SelectItemGroup("Pure Text");
		gTextoPuro.setSelectItems(new SelectItem[] {
				new SelectItem(ReportTypeEnum.CSV.name(), ReportTypeEnum.CSV.getDescription(), ReportTypeEnum.CSV.getDescription()),
				new SelectItem(ReportTypeEnum.TXT.name(), ReportTypeEnum.TXT.getDescription(), ReportTypeEnum.TXT.getDescription()) } );

		SelectItemGroup gOutros = new SelectItemGroup("Others");
		gOutros.setSelectItems(new SelectItem[] {
				new SelectItem(ReportTypeEnum.PPTX.name(), ReportTypeEnum.PPTX.getDescription(), ReportTypeEnum.PPTX.getDescription()),
				new SelectItem(ReportTypeEnum.HTML.name(), ReportTypeEnum.HTML.getDescription(), ReportTypeEnum.HTML.getDescription()) } );

		listTypeReport = new ArrayList<SelectItem>();
		listTypeReport.add(gDocumentos);
		listTypeReport.add(gPlanilhas);
		listTypeReport.add(gTextoPuro);
		listTypeReport.add(gOutros);		
	}
	
	public Map<String, Object> getParameters() {
		Map<String, Object> params = new HashMap<String, Object>();

		ServletContext sc = (ServletContext) context.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator);

		params.put("IMAGEM", caminho + "/static/img/logo.png");
		return params;
	}

	public void exportar(IBaseReport relatorio, Collection<?> colecao, Map<String, Object> params,		
			boolean forcarDownload) {
		exportar(relatorio, colecao, params, forcarDownload, true);
	}
	
	public byte[] exportar(IBaseReport relatorio, Collection<?> colecao, Map<String, Object> params,		
			boolean forcarDownload, boolean renderizar) {
		byte[] buffer = null;
		
		if (colecao!=null && !colecao.isEmpty()) {
			ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(typeReport);

			if (params!=null) {
				Locale locale = new Locale( "pt", "BR" );
				params.put( JRParameter.REPORT_LOCALE, locale );
			}
			
			buffer = relatorio.export(colecao, params, tipoRel);
			
			if (renderizar) {
				this.renderer.render(buffer, tipoRel, "relatorio." + tipoRel.name().toLowerCase(), forcarDownload);
			}
			
		} else {
			generateInformativeMessage("No records found for exporting report.");
		}
		
		return buffer;
	}
	
	public byte[] exportarJoinAlternate(IBaseReport relatorio1, IBaseReport relatorio2,
			boolean forcarDownload, boolean renderizar) {
		byte[] buffer = null;
		ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(typeReport);

		buffer = relatorio1.exportJoinAlternate(relatorio1.getReportObject(), relatorio2.getReportObject(), tipoRel);
		
		if (renderizar) {
			this.renderer.render(buffer, tipoRel, "relatorio." + tipoRel.name().toLowerCase(), forcarDownload);
		}
		
		return buffer;
	}
	
	public void exportarPDFjunto(byte[] buffer1, byte[] buffer2, boolean forcarDownload) throws PdfException {
		if (buffer1!=null && buffer2!=null) {
			ReportTypeEnum tipoRel = ReportTypeEnum.valueOf(typeReport);
			
			PdfUtil pu = new PdfUtil();
			byte[] buffer = pu.joinAlternate(buffer1, buffer2);
			
			this.renderer.render(buffer, tipoRel, "report." + tipoRel.name().toLowerCase(), forcarDownload);			
		} else {
			generateInformativeMessage("No records found for exporting report.");
		}		
	}
	
	public String getRealPath(){
		ServletContext sc = (ServletContext) context.getExternalContext().getContext();
		String caminho = sc.getRealPath("/WEB-INF/classes/reports");
		return caminho + File.separator;
	}
	
	public List<SelectItem> getListTypeReport() {
		return listTypeReport;
	}
	
	public void setListTypeReport(List<SelectItem> listTypeReport) {
		this.listTypeReport = listTypeReport;
	}
	
	public String getTypeReport() {
		return typeReport;
	}
	
	public void setTypeReport(String typeReport) {
		this.typeReport = typeReport;
	}
	
	public String cancel() {
		return getDesktopPage();
	}
	
}
