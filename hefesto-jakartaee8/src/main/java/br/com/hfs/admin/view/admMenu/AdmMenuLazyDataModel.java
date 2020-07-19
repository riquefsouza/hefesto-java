package br.com.hfs.admin.view.admMenu;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.base.BaseLazyModel;

public class AdmMenuLazyDataModel extends LazyDataModel<AdmMenu> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base lazy model. */
	private BaseLazyModel<AdmMenu, Long, AdmMenuService> baseLazyModel;

	public AdmMenuLazyDataModel(AdmMenuService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmMenu, Long, AdmMenuService>(service);
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(AdmMenu admMenu) {
		return admMenu.getId();
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmMenu> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmMenu> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmMenuLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
