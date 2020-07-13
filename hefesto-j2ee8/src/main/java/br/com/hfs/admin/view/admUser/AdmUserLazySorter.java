package br.com.hfs.admin.view.admUser;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.base.BaseLazySorter;

public class AdmUserLazySorter extends BaseLazySorter<AdmUser> {

	public AdmUserLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}

}
