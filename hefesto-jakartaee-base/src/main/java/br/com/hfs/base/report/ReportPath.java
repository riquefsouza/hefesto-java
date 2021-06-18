package br.com.hfs.base.report;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.Nonbinding;

// TODO: Auto-generated Javadoc
/**
 * The Interface RelatorioPath.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface ReportPath {
	
	/**
	 * Value.
	 *
	 * @return the string
	 */
	@Nonbinding
	String value();
}
