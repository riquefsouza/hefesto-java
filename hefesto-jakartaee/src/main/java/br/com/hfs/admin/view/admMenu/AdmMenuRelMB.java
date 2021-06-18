package br.com.hfs.admin.view.admMenu;

import java.util.Map;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.base.report.BaseViewReport;
import br.com.hfs.base.report.IBaseReport;
import br.com.hfs.base.report.IBaseViewReport;
import br.com.hfs.base.report.ReportPath;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmMenuRelMB 
	extends BaseViewReport<AdmMenu, Long, AdmMenuService>
		implements IBaseViewReport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The forcar download. */
	private Boolean forceDownload;
	
	/** The report. */
	@Inject
	@ReportPath("AdmMenu")
	private IBaseReport report;

	/**
	 * Instantiates a new adm menu rel MB.
	 */
	public AdmMenuRelMB() {
		super();
		this.forceDownload = false;
	}

	public void export() {
		Map<String, Object> params = getParameters();
		params.put("PARAMETER1", "");
		
		super.exportar(report, getService().findAll(), params, forceDownload);
	}

	public Boolean getForceDownload() {
		return forceDownload;
	}

	public void setForceDownload(Boolean forceDownload) {
		this.forceDownload = forceDownload;
	}

}
