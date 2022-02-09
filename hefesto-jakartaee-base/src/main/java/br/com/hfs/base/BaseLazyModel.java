package br.com.hfs.base;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

import jakarta.faces.context.FacesContext;

public class BaseLazyModel<T, I extends Serializable, B extends BaseService<T, I, ? extends BaseRepository<T, I>>>
		implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The datasource. */
	private List<T> datasource;

	/** The data size. */
	private int dataSize;

	private boolean found;

	private B service;

	public BaseLazyModel(B service) {
		super();
		this.datasource = new ArrayList<T>();
		this.service = service;
		this.dataSize = service.count().intValue();
		this.found = false;
	}

	public BaseLazyModel(B service, int dataSize) {
		super();
		this.datasource = new ArrayList<T>();
		this.service = service;
		this.dataSize = dataSize;
		this.found = false;
	}

	public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy,
			boolean usarQueryNativa) {
		List<T> ds;

		if (!usarQueryNativa)
			ds = service.findAll(first, pageSize);
		else {
			try {
				ds = service.listByRange(first + 1, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				ds = service.listByRange(first + 1, first + (dataSize % pageSize));
			}
		}
		return load(first, pageSize, sortBy, filterBy, ds);
	}

	public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy,
			List<T> ds) {
		List<T> data = new ArrayList<T>();
		found = false;

		// paginate
		if (dataSize > pageSize) {
			try {
				datasource = ds;
			} catch (Exception e) {
				return new ArrayList<T>();
			}
		}

		// filter
		for (T entidade : datasource) {
			boolean match = true;

			if (filterBy != null && filterBy.size() > 0) {
								
				for (FilterMeta fm : filterBy.values()) {
				//for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						//FilterMeta fm = filters.get(it.next()); 
						
						/*
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						
						Method metodo1 = entidade.getClass().getMethod("get" + StringUtils.capitalize(filterProperty),
								new Class[] {});
						String fieldValue = String.valueOf(metodo1.invoke(entidade, new Object[] {}));
						*/

						String filterValue = fm.getFilterValue().toString();
						String fieldValue = fm.getField();
						
						if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
							match = true;
							found = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(entidade);
			}
		}

		return data;
	}

	public List<T> getDatasource() {
		return datasource;
	}

	public int getDataSize() {
		return dataSize;
	}

	public boolean isFound() {
		return found;
	}

	public B getService() {
		return service;
	}

	public boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
		boolean matching = true;

		for (FilterMeta filter : filterBy) {
			FilterConstraint constraint = filter.getConstraint();
			Object filterValue = filter.getFilterValue();

			try {
				Object columnValue = String.valueOf(this.getPropertyValueViaReflection(o, filter.getField()));
				matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
			} catch (ReflectiveOperationException | IntrospectionException e) {
				matching = false;
			}

			if (!matching) {
				break;
			}
		}

		return matching;
	}

	private Object getPropertyValueViaReflection(Object o, String field)
			throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
		return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
	}
}
