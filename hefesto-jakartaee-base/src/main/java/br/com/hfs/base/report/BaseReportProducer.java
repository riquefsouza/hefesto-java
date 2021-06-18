package br.com.hfs.base.report;

import java.lang.reflect.Field;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

public class BaseReportProducer {
	
	/** The Constant DEFAULT_PATH. */
	private static final String DEFAULT_PATH = "reports/";
	
	/** The Constant DEFAULT_EXTENSION. */
	private static final String DEFAULT_EXTENSION = ".jasper";

	public BaseReportProducer() {
	}

	@Produces
	@Default
	public static IBaseReport create(InjectionPoint ip) {
		Field field = (Field) ip.getMember();
		String path = DEFAULT_PATH + field.getName() + DEFAULT_EXTENSION;
		if (field.isAnnotationPresent(ReportPath.class)) {
			path = DEFAULT_PATH + ((ReportPath) field.getAnnotation(ReportPath.class)).value() + DEFAULT_EXTENSION;
		}

		return create(path);
	}

	public static IBaseReport create(String path) {
		return new BaseReportImpl(path);
	}
}
