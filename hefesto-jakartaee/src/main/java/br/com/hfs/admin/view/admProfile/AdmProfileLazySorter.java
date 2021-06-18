package br.com.hfs.admin.view.admProfile;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.base.BaseLazySorter;

public class AdmProfileLazySorter extends BaseLazySorter<AdmProfile> {

	public AdmProfileLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}

}
