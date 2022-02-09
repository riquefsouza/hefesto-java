package br.com.hfs.admin.view.admMenu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

public class AdmMenuLazyDataModel extends LazyDataModel<AdmMenu> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base lazy model. */
	private BaseLazyModel<AdmMenu, Long, AdmMenuService> baseLazyModel;

	public AdmMenuLazyDataModel(AdmMenuService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmMenu, Long, AdmMenuService>(service);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public AdmMenu getRowData(String rowKey) {
		for (AdmMenu admMenu : this.baseLazyModel.getDatasource()) {
			if (admMenu.getId().toString().equals(rowKey))
				return admMenu;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public String getRowKey(AdmMenu admMenu) {
		return String.valueOf(admMenu.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String,
	 * org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmMenu> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmMenu> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
		/*
		 * if (sortBy != null) { Collections.sort(data, new AdmMenuLazySorter(sortField,
		 * sortOrder)); }
		 */
		if (!sortBy.isEmpty()) {
			List<Comparator<AdmMenu>> comparators = sortBy.values().stream()
					.map(o -> new AdmMenuLazySorter(o.getField(), o.getOrder())).collect(Collectors.toList());
			Comparator<AdmMenu> cp = ComparatorUtils.chainedComparator(comparators);
			data.sort(cp);
		}

		if (filterBy.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return (int) this.baseLazyModel.getDatasource().stream()
				.filter(o -> this.baseLazyModel.filter(FacesContext.getCurrentInstance(), filterBy.values(), o)).count();
	}

}
