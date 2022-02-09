package br.com.hfs.admin.view.admParameterCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

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
	public String getRowKey(AdmParameterCategory obj) {
		return String.valueOf(obj.getId());
	}
	
	@Override
	public List<AdmParameterCategory> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmParameterCategory> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
        if (!sortBy.isEmpty()) {
            List<Comparator<AdmParameterCategory>> comparators = sortBy.values().stream()
                    .map(o -> new AdmParameterCategoryLazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<AdmParameterCategory> cp = ComparatorUtils.chainedComparator(comparators);
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
