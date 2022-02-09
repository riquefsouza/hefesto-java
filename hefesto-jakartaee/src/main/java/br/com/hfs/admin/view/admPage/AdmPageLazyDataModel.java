/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admPage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

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
	public String getRowKey(AdmPage admPagina) {
		return String.valueOf(admPagina.getId());
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmPage> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmPage> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
        if (!sortBy.isEmpty()) {
            List<Comparator<AdmPage>> comparators = sortBy.values().stream()
                    .map(o -> new AdmPageLazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<AdmPage> cp = ComparatorUtils.chainedComparator(comparators);
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
