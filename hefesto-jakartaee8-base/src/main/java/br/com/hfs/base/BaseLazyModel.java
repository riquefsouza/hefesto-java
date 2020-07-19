package br.com.hfs.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;

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

	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters,
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
		return load(first, pageSize, sortField, sortOrder, filters, ds);
	}

	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters,
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

			if (filters != null && filters.size() > 0) {
								
				for (FilterMeta fm : filters.values()) {
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
						String fieldValue = fm.getFilterField();
						
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

}
