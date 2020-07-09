package br.com.hfs.admin.controller.admParametroCategoria;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.BaseLazyModel;

public class AdmParameterCategoryLazyDataModel extends LazyDataModel<AdmParameterCategory> {

	private static final long serialVersionUID = 1L;
	
	private BaseLazyModel<AdmParameterCategory, Long, AdmParameterCategoryService> baseLazyModel;
	
	public AdmParameterCategoryLazyDataModel(AdmParameterCategoryService service) {
		super();
		this.baseLazyModel = new BaseLazyModel<AdmParameterCategory, Long, AdmParameterCategoryService>(service);
	}
	
	@Override
	public AdmParameterCategory getRowData(String rowKey) {
		for (AdmParameterCategory obj : this.baseLazyModel.getDatasource()) {
			if (obj.getId().toString().equals(rowKey))
				return obj;
		}
		return null;
	}
	
	@Override
	public Object getRowKey(AdmParameterCategory obj) {
		return obj.getId();
	}
	
	@Override
	public List<AdmParameterCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, FilterMeta> filters) {
		List<AdmParameterCategory> data = this.baseLazyModel.load(first, pageSize, sortField, sortOrder, filters, false);

		// sort
		if (sortField != null) {
			Collections.sort(data, new AdmParameterCategoryLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && this.baseLazyModel.isFound()) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(this.baseLazyModel.getDataSize());
		}

		return data;
	}
}
