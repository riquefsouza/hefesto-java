package br.com.hfs.admin.view.admProfile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.base.BaseLazyModel;

public class AdmProfileLazyDataModel extends LazyDataModel<AdmProfile> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base lazy model. */
	private BaseLazyModel<AdmProfile, Long, AdmProfileService> baseLazyModel;

	public AdmProfileLazyDataModel(AdmProfileService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmProfile, Long, AdmProfileService>(service);
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public AdmProfile getRowData(String rowKey) {
		for (AdmProfile admPerfil : this.baseLazyModel.getDatasource()) {
			if (admPerfil.getId().toString().equals(rowKey))
				return admPerfil;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(AdmProfile admPerfil) {
		return admPerfil.getId();
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmProfile> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmProfile> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmProfileLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
