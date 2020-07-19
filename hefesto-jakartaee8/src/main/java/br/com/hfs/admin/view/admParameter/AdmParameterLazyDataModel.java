/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admParameter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.base.BaseLazyModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AdmParameterLazyDataModel.
 */
public class AdmParameterLazyDataModel extends LazyDataModel<AdmParameter> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The base lazy model. */
	private BaseLazyModel<AdmParameter, Long, AdmParameterService> baseLazyModel;

	public AdmParameterLazyDataModel(AdmParameterService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmParameter, Long, AdmParameterService>(service);
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public AdmParameter getRowData(String rowKey) {
		for (AdmParameter admParametro : this.baseLazyModel.getDatasource()) {
			if (admParametro.getId().toString().equals(rowKey))
				return admParametro;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(AdmParameter admParametro) {
		return admParametro.getId();
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmParameter> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmParameter> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmParameterLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
