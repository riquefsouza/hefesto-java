/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.base.report;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseRelatorioImpl.
 */
public class BaseReportImpl implements IBaseReport {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private Logger log;
	
	/** The jasper. */
	private JasperReport jasper;
	
	/** The print. */
	private JasperPrint print;
	
	/** The path. */
	private String path;
		
	/**
	 * Instantiates a new base relatorio impl.
	 *
	 * @param path
	 *            the path
	 */
	public BaseReportImpl(String path) {
		this.log = LogManager.getLogger(BaseReportImpl.class);
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#getSource()
	 */
	public Object getSource() {
		try {
			loadReport();
		} catch (Exception e) {
			throw new ReportException(log, ReportBundle.getString("excecao-carregar-relatorio", this.path), e);
		}

		return this.jasper;
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#getReportObject()
	 */
	public JasperPrint getReportObject() {
		return this.print;
	}

	/**
	 * Gets the class loader for resource.
	 *
	 * @param resource
	 *            the resource
	 * @return the class loader for resource
	 */
	private ClassLoader getClassLoaderForResource(final String resource) {
		final String stripped = resource.charAt(0) == '/' ? resource.substring(1) : resource;

		URL url = null;
		ClassLoader result = Thread.currentThread().getContextClassLoader();

		if (result != null) {
			url = result.getResource(stripped);
		}

		if (url == null) {
			result = BaseReportImpl.class.getClassLoader();
			url = BaseReportImpl.class.getClassLoader().getResource(stripped);
		}

		if (url == null) {
			result = null;
		}

		return result;
	}

	/**
	 * Gets the report stream.
	 *
	 * @param relativePath
	 *            the relative path
	 * @return the report stream
	 */
	private InputStream getReportStream(String relativePath) {
		InputStream reportStream;
		try {
			ClassLoader classLoader = getClassLoaderForResource(relativePath);
			reportStream = classLoader.getResourceAsStream(relativePath);
		} catch (Exception cause) {
			reportStream = null;
		}

		if (reportStream == null) {
			throw new ReportException(ReportBundle.getString("arquivo-nao-encontrado"));
		}

		return reportStream;
	}

	/**
	 * Load JRXML.
	 *
	 * @param relativePath
	 *            the relative path
	 */
	private void loadJRXML(String relativePath) {
		try {
			this.jasper = JasperCompileManager.compileReport(getReportStream(relativePath));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ReportException(
					ReportBundle.getString("excecao-compilando-arquivo-jrxml", this.path));
		}
	}

	/**
	 * Load jasper.
	 *
	 * @param relativePath
	 *            the relative path
	 */
	private void loadJasper(String relativePath) {
		try {
			this.jasper = ((JasperReport) JRLoader.loadObject(getReportStream(relativePath)));
		} catch (JRException e) {
			throw new ReportException(log,
					ReportBundle.getString("excecao-carregando-arquivo-jasper", this.path), e);
		}
	}

	/**
	 * Load report.
	 */
	private final void loadReport() {
		if (this.path == null) {
			throw new ReportException(ReportBundle.getString("arquivo-nao-encontrado"));
		}

		if (this.jasper == null) {
			if (this.path.endsWith(".jasper")) {
				loadJasper(this.path);
			} else if (this.path.endsWith(".jrxml")) {
				this.log.info(ReportBundle.getString("recomenda-usar-jasper"));
				try {
					String jasperPath = this.path.replaceAll(".jrxml", ".jasper");

					loadJasper(jasperPath);
					this.log.info(ReportBundle.getString("encontrada-versao-compilada", jasperPath));
				} catch (Exception e) {
					this.log.error(ReportBundle.getString("nao-encontrada-versao-compilada"));
					loadJRXML(this.path);
				}
			} else {
				throw new ReportException(
						ReportBundle.getString("excecao-extensao-nao-valida", this.path ));
			}
		}
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#prepare(java.util.Collection, java.util.Map)
	 */
	public void prepare(Collection<?> dataSource, Map<String, Object> param) {
		this.log.info(ReportBundle.getString("preenchendo-relatorio"));
		loadReport();
		try {
			this.print = JasperFillManager.fillReport(this.jasper, param, new JRBeanCollectionDataSource(dataSource));
		} catch (JRException e) {
			throw new ReportException(log, ReportBundle.getString("problema-preenchendo-relatorio"), e);
		}
	}
	
	private JasperPrint joinAlternate(JasperPrint jp1, JasperPrint jp2){
		JasperPrint jp = new JasperPrint();
		
		jp.setBookmarks(jp1.getBookmarks());
		jp.setBottomMargin(jp1.getBottomMargin());
		jp.setDefaultStyle(jp1.getDefaultStyle());
		jp.setFormatFactoryClass(jp1.getFormatFactoryClass());
		jp.setLeftMargin(jp1.getLeftMargin());
		jp.setLocaleCode(jp1.getLocaleCode());
		jp.setName(jp1.getName());
		jp.setOrientation(jp1.getOrientationValue());
		jp.setPageHeight(jp1.getPageHeight());
		jp.setPageWidth(jp1.getPageWidth());
		jp.setRightMargin(jp1.getRightMargin());
		jp.setTimeZoneId(jp1.getTimeZoneId());
		jp.setTopMargin(jp1.getTopMargin());
		
		for (int i = 0; i < jp1.getPages().size(); i++) {
			jp.addPage(jp1.getPages().get(i));
			jp.addPage(jp2.getPages().get(i));
		}
		
		return jp;
	}
	
	/**
	 * Checa se é filled.
	 *
	 * @return the boolean
	 */
	public Boolean isFilled() {
		if (this.print != null)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#export(br.jus.trt1.hfsframework.base.relatorio.ReportTypeEnum)
	 */
	public byte[] export(ReportTypeEnum type) {
		if (!isFilled().booleanValue()) {
			throw new ReportException(ReportBundle.getString("excecao-relatorio-nao-preenchendo"));
		}

		return BaseReportExporter.export(type, this.print).toByteArray();
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#export(java.util.Collection, java.util.Map, br.jus.trt1.hfsframework.base.relatorio.ReportTypeEnum)
	 */
	public byte[] export(Collection<?> dataSource, Map<String, Object> param, ReportTypeEnum type) {
		prepare(dataSource, param);
		return export(type);
	}
		
	/**
	 * Export junto alternado.
	 *
	 * @param jp1
	 *            the jp 1
	 * @param jp2
	 *            the jp 2
	 * @param type
	 *            the type
	 * @return the byte[]
	 */
	public byte[] exportJoinAlternate(JasperPrint jp1, JasperPrint jp2, ReportTypeEnum type) {
		this.print = joinAlternate(jp1, jp2);		
		return export(type);
	}

	/**
	 * Atribui o the path.
	 *
	 * @param path
	 *            o novo the path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.relatorio.IBaseRelatorio#getPath()
	 */
	public String getPath() {
		return this.path;
	}
}
