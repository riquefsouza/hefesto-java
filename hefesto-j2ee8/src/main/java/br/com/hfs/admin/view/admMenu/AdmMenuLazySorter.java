package br.com.hfs.admin.view.admMenu;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.base.BaseLazySorter;

// TODO: Auto-generated Javadoc
/**
 * The Class AdmMenuLazySorter.
 */
public class AdmMenuLazySorter extends BaseLazySorter<AdmMenu> {

	/**
	 * Instantiates a new adm menu lazy sorter.
	 *
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 */
	public AdmMenuLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}

}
