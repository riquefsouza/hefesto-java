package br.com.hfs.base.report;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface IBaseViewReport extends Serializable {

	List<ReportGroupVO> getListReportType();

	String export(HttpServletResponse response, String reportType, String forceDownload, List<String> params);

	String cancel();
}
