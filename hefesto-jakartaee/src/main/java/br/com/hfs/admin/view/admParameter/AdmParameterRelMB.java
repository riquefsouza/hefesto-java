/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admParameter;

import java.util.Map;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.base.report.BaseViewReport;
import br.com.hfs.base.report.IBaseReport;
import br.com.hfs.base.report.IBaseViewReport;
import br.com.hfs.base.report.ReportPath;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmParameterRelMB 
	extends BaseViewReport<AdmParameter, Long, AdmParameterService>
		implements IBaseViewReport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The forcar download. */
	private Boolean forceDownload;
	
	/** The relatorio. */
	@Inject
	@ReportPath("AdmParameter")
	private IBaseReport report;

	public AdmParameterRelMB() {
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
