package br.com.hfs.admin.view.admPage;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.base.BaseLazySorter;

public class AdmPageLazySorter extends BaseLazySorter<AdmPage> {

	public AdmPageLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}

}
