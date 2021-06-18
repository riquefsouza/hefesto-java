package br.com.hfs.admin.view.admParameterCategory;

import java.util.Map;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.report.BaseViewReport;
import br.com.hfs.base.report.IBaseReport;
import br.com.hfs.base.report.IBaseViewReport;
import br.com.hfs.base.report.ReportPath;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmParameterCategoryRelMB extends BaseViewReport<AdmParameterCategory, Long, AdmParameterCategoryService>
		implements IBaseViewReport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Boolean forceDownload;

	@Inject
	@ReportPath("AdmParameterCategory")
	private IBaseReport report;

	public AdmParameterCategoryRelMB() {
		super();
		this.forceDownload = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewRelatorio#exportar()
	 */
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
