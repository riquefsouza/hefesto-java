package br.com.hfs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admMenuView")
public class AdmMenuController extends BaseViewRegister<AdmMenu, Long, AdmMenuService> {

	private static final long serialVersionUID = 1L;

	public AdmMenuController() {
		super("/private/admMenu/listAdmMenu", 
				"/private/admMenu/editAdmMenu", 
				"AdmMenu", AdmMenu.class);
	}
}
