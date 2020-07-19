package br.com.hfs.util;

// TODO: Auto-generated Javadoc
/**
 * The Interface IBaseProducer.
 *
 * @param <R>
 *            the generic type
 * @param <T>
 *            the generic type
 */
public abstract interface IBaseProducer<R, T> {
	
	/**
	 * Apply.
	 *
	 * @param obj
	 *            the obj
	 * @return the r
	 */
	public abstract R apply(T obj);
	
}
