package br.com.hfs.admin.view.admParameter;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.base.BaseLazySorter;

public class AdmParameterLazySorter extends BaseLazySorter<AdmParameter> {

	public AdmParameterLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}

}
