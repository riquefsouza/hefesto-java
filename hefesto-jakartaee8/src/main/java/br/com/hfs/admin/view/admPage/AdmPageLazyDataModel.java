/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admPage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.base.BaseLazyModel;

public class AdmPageLazyDataModel extends LazyDataModel<AdmPage> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private BaseLazyModel<AdmPage, Long, AdmPageService> baseLazyModel;

	public AdmPageLazyDataModel(AdmPageService bc) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmPage, Long, AdmPageService>(bc);
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public AdmPage getRowData(String rowKey) {
		for (AdmPage admPagina : this.baseLazyModel.getDatasource()) {
			if (admPagina.getId().toString().equals(rowKey))
				return admPagina;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(AdmPage admPagina) {
		return admPagina.getId();
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmPage> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmPage> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmPageLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
