package br.com.hfs.admin.view.admUser;

import java.util.Map;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.report.BaseViewReport;
import br.com.hfs.base.report.IBaseReport;
import br.com.hfs.base.report.IBaseViewReport;
import br.com.hfs.base.report.ReportPath;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmUserRelMB 
	extends BaseViewReport<AdmUser, Long, AdmUserService>
		implements IBaseViewReport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Boolean forceDownload;
	
	/** The relatorio. */
	@Inject
	@ReportPath("AdmUser")
	private IBaseReport report;

	/**
	 * Instantiates a new adm parametro categoria rel MB.
	 */
	public AdmUserRelMB() {
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
