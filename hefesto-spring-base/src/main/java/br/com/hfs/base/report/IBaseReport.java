/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base.report;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

// TODO: Auto-generated Javadoc
/**
 * The Interface IBaseRelatorio.
 */
public abstract interface IBaseReport extends Serializable {

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public abstract Object getSource();

	/**
	 * Gets the report object.
	 *
	 * @return the report object
	 */
	public abstract JasperPrint getReportObject();

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public abstract String getPath();

	/**
	 * Prepare.
	 *
	 * @param paramCollection
	 *            the param collection
	 * @param paramMap
	 *            the param map
	 */
	public abstract void prepare(Collection<?> paramCollection, Map<String, Object> paramMap);

	public abstract byte[] export(Collection<?> paramCollection, Map<String, Object> paramMap,
			ReportTypeEnum paramType);

	public abstract byte[] export(ReportTypeEnum paramType);

	public abstract byte[] exportJuntoAlternado(JasperPrint jp1, JasperPrint jp2, ReportTypeEnum type);
}
