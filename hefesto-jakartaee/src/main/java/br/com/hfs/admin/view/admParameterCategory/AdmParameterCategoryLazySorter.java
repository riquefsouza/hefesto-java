package br.com.hfs.admin.view.admParameterCategory;

import org.primefaces.model.SortOrder;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.base.BaseLazySorter;

public class AdmParameterCategoryLazySorter extends BaseLazySorter<AdmParameterCategory> {

	public AdmParameterCategoryLazySorter(String sortField, SortOrder sortOrder) {
		super(sortField, sortOrder);
	}
}
