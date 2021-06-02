package br.com.hfs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admin/admUserView")
@SessionAttributes("listMenu")
public class AdmUserController extends BaseViewRegister<AdmUser, Long, AdmUserService> {

	private static final long serialVersionUID = 1L;

	public AdmUserController() {
		super("/private/admin/admUser/listAdmUser", 
				"/private/admin/admUser/editAdmUser", 
				"AdmUser", AdmUser.class);
	}

}
