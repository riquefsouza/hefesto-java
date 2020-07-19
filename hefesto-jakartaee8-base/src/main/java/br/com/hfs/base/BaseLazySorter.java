package br.com.hfs.base;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

public class BaseLazySorter <T> implements Comparator<T> {

	/** The sort field. */
	private String sortField;

	/** The sort order. */
	private SortOrder sortOrder;

	/**
	 * Instantiates a new base lazy sorter.
	 *
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 */
	public BaseLazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public int compare(T entidade1, T entidade2) {
		try {
			Method metodo1 = entidade1.getClass().getMethod("get" + StringUtils.capitalize(this.sortField),
					new Class[] {});
			Object value1 = metodo1.invoke(entidade1, new Object[] {});

			Method metodo2 = entidade2.getClass().getMethod("get" + StringUtils.capitalize(this.sortField),
					new Class[] {});
			Object value2 = metodo2.invoke(entidade2, new Object[] {});

			int value = ((Comparable<Object>) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
