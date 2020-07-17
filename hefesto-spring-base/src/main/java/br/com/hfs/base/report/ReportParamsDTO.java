package br.com.hfs.base.report;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class ReportParamsDTO {

	@NotBlank
	private String reportType;

	@NotBlank
	private String forceDownload;

	@NotBlank
	private String reportName;
	
	private List<String> params;

	public ReportParamsDTO() {
		super();
		this.params = new ArrayList<String>();
	}
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getForceDownload() {
		return forceDownload;
	}

	public void setForceDownload(String forceDownload) {
		this.forceDownload = forceDownload;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ReportParamsDTO [reportType=" + reportType + ", forceDownload=" + forceDownload + ", reportName="
				+ reportName + ", params=" + params + "]";
	}
	
}
