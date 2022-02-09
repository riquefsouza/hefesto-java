package br.com.hfs.admin.view.admProfile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

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
	public String getRowKey(AdmProfile admPerfil) {
		return String.valueOf(admPerfil.getId());
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmProfile> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmProfile> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
        if (!sortBy.isEmpty()) {
            List<Comparator<AdmProfile>> comparators = sortBy.values().stream()
                    .map(o -> new AdmProfileLazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<AdmProfile> cp = ComparatorUtils.chainedComparator(comparators);
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
