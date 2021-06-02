package br.com.hfs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.BaseViewRegisterPaged;

@Controller
@RequestMapping(value = "/private/admin/admParameterCategoryView")
@SessionAttributes("listMenu")
public class AdmParameterCategoryController
		extends BaseViewRegisterPaged<AdmParameterCategory, Long, AdmParameterCategoryService> {

	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryController() {
		super("/private/admin/admParameterCategory/listAdmParameterCategory",
				"/private/admin/admParameterCategory/editAdmParameterCategory", 
				"AdmParameterCategory", AdmParameterCategory.class);
	}

}
