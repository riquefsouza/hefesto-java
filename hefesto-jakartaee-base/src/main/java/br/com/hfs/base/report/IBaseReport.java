package br.com.hfs.base.report;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

public interface IBaseReport extends Serializable {

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

	public abstract void prepare(Collection<?> paramCollection, Map<String, Object> paramMap);

	public abstract byte[] export(Collection<?> paramCollection, Map<String, Object> paramMap,
			ReportTypeEnum paramType);

	public abstract byte[] export(ReportTypeEnum paramType);

	public abstract byte[] exportJoinAlternate(JasperPrint jp1, JasperPrint jp2, ReportTypeEnum type);

}
