package br.com.hfs.admin.view.admUser;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseLazyModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AdmUserLazyDataModel.
 */
public class AdmUserLazyDataModel extends LazyDataModel<AdmUser> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base lazy model. */
	private BaseLazyModel<AdmUser, Long, AdmUserService> baseLazyModel;

	public AdmUserLazyDataModel(AdmUserService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmUser, Long, AdmUserService>(service);
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public AdmUser getRowData(String rowKey) {
		for (AdmUser admUser : this.baseLazyModel.getDatasource()) {
			if (admUser.getId().toString().equals(rowKey))
				return admUser;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(AdmUser admUser) {
		return admUser.getId();
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmUser> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmUser> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmUserLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
