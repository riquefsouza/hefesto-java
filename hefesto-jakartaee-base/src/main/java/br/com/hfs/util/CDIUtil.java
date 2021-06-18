package br.com.hfs.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CDIUtil {

	/**
	 * Gets the bean manager.
	 *
	 * @return the bean manager
	 */
	private static BeanManager getBeanManager() {
		try {
			return (BeanManager) InitialContext.doLookup("java:comp/BeanManager");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Lookup.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T lookup(Class<T> clazz) {
		BeanManager bm = getBeanManager();
		Iterator<Bean<?>> iter = bm.getBeans(clazz, new Annotation[0]).iterator();
		if (!iter.hasNext()) {
			throw new IllegalStateException("CDI BeanManager could not find a bean of the class: " + clazz.getName());
		}
		Bean<T> bean = (Bean<T>) iter.next();
		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, clazz, ctx);
	}
	
}
