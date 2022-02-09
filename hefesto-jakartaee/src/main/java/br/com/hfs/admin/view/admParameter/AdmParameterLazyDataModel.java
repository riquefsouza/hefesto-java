/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admParameter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

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
	public String getRowKey(AdmParameter admParametro) {
		return String.valueOf(admParametro.getId());
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmParameter> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmParameter> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
        if (!sortBy.isEmpty()) {
            List<Comparator<AdmParameter>> comparators = sortBy.values().stream()
                    .map(o -> new AdmParameterLazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<AdmParameter> cp = ComparatorUtils.chainedComparator(comparators);
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
