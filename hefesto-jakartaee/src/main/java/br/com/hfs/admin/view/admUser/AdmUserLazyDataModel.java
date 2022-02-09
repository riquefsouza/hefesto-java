package br.com.hfs.admin.view.admUser;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseLazyModel;
import jakarta.faces.context.FacesContext;

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
	public String getRowKey(AdmUser admUser) {
		return String.valueOf(admUser.getId());
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<AdmUser> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		List<AdmUser> data = this.baseLazyModel.load(first, pageSize, sortBy, filterBy, false);

		// sort
        if (!sortBy.isEmpty()) {
            List<Comparator<AdmUser>> comparators = sortBy.values().stream()
                    .map(o -> new AdmUserLazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<AdmUser> cp = ComparatorUtils.chainedComparator(comparators);
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
