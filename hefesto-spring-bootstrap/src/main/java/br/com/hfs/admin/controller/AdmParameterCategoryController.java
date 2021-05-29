package br.com.hfs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admParameterCategoryView")
public class AdmParameterCategoryController
		extends BaseViewRegister<AdmParameterCategory, Long, AdmParameterCategoryService> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryController() {
		super("/private/admParameterCategory/listAdmParameterCategory",
				"/private/admParameterCategory/editAdmParameterCategory", 
				"AdmParameterCategory", AdmParameterCategory.class);
	}

}
