package br.com.hfs.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admUserView")
public class AdmUserController extends BaseViewRegister<AdmUser, Long, AdmUserService> {

	private static final long serialVersionUID = 1L;

	public AdmUserController() {
		super("/private/admUser/listAdmUser", 
				"/private/admUser/editAdmUser", 
				"AdmUser", AdmUser.class);
	}

}
