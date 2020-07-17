package br.com.hfs.base.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class ReportRender implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ReportRender.class);

	public void render(final byte[] conteudo, final ReportTypeEnum tipoRelatorio, String nomeArquivo,
			boolean forcarDownload) {
		//HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpServletResponse response = attr.getResponse();		
	    
		log.info("Rendering to file " + nomeArquivo + ".");

		if (tipoRelatorio.equals(ReportTypeEnum.HTML)){
			nomeArquivo = nomeArquivo.replaceAll(".html", ".zip");
		}
		
		try {
			response.setContentType(tipoRelatorio.getContentType());
			response.setContentLength(conteudo.length);

			String forcarDownloadComando = forcarDownload ? "attachment; " : "";
			response.setHeader("Content-Disposition", forcarDownloadComando + "filename=\"" + nomeArquivo + "\"");

			log.info("Writing the file " + nomeArquivo + " in the response.");
			response.getOutputStream().write(conteudo, 0, conteudo.length);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			log.warn("Error generating report.", e);
		}
	}

	public void render(final byte[] conteudo, final ReportTypeEnum tipoRelatorio, final String nomeArquivo) {
		render(conteudo, tipoRelatorio, nomeArquivo, false);
	}

	public void render(final InputStream stream, final ReportTypeEnum tipoRelatorio, final String nomeArquivo,
			boolean forcarDownload) {
		log.info("Rendering to file " + nomeArquivo + ".");
		try {
			render(IOUtils.toByteArray(stream), tipoRelatorio, nomeArquivo, forcarDownload);
		} catch (IOException e) {
			log.warn("Error generating report.", e);
		}
	}

	public void render(final InputStream stream, final ReportTypeEnum tipoRelatorio, final String nomeArquivo) {
		render(stream, tipoRelatorio, nomeArquivo, false);
	}

	public void render(File file, ReportTypeEnum tipoRelatorio, String nomeArquivo, boolean forcarDownload) {
		log.info("Rendering to file " + nomeArquivo + ".");
		try {
			render(new FileInputStream(file), tipoRelatorio, nomeArquivo, forcarDownload);
		} catch (FileNotFoundException e) {
			log.warn("Error generating report.", e);
		}
	}

	public void render(File file, ReportTypeEnum tipoRelatorio, String nomeArquivo) {
		render(file, tipoRelatorio, nomeArquivo, false);
	}

	public void render(HttpServletResponse response, final byte[] conteudo, ReportParamsDTO reportParamsDTO) {
		ReportTypeEnum reportType = ReportTypeEnum.valueOf(reportParamsDTO.getReportType());
		String nameReport = reportParamsDTO.getReportName() + "." + reportType.name().toLowerCase();
		boolean forceDownload = Boolean.parseBoolean(reportParamsDTO.getForceDownload());
		
		log.info("Rendering to file " + nameReport + ".");

		if (reportType.equals(ReportTypeEnum.HTML)){
			nameReport = nameReport.replaceAll(".html", ".zip");
		}
		
		try {
			response.setContentType(reportType.getContentType());
			response.setContentLength(conteudo.length);

			String forcarDownloadComando = forceDownload ? "attachment; " : "";
			response.setHeader("Content-Disposition", forcarDownloadComando + "filename=\"" + nameReport + "\"");

			log.info("Writing the file " + nameReport + " in the response.");
			response.getOutputStream().write(conteudo, 0, conteudo.length);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			log.warn("Error generating report.", e);
		}
	}	
	
	
}
