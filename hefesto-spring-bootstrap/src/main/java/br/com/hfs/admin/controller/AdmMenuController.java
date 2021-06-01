package br.com.hfs.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.security.HfsUserDetails;

@Controller
@RequestMapping(value = "/private/admMenuView")
public class AdmMenuController extends BaseViewRegister<AdmMenu, Long, AdmMenuService> {

	private static final long serialVersionUID = 1L;

	public AdmMenuController() {
		super("/private/admMenu/listAdmMenu", 
				"/private/admMenu/editAdmMenu", 
				"AdmMenu", AdmMenu.class);
	}
	
	@Override
	@GetMapping
	public ModelAndView list(AdmMenu bean) {
		Optional<ModelAndView> mv = getPage(getListPage());
		if (mv.isPresent()) {
			mv.get().addObject("bean", bean);
						
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			HfsUserDetails userDetails = (HfsUserDetails)authentication.getPrincipal();
			
			List<MenuVO> lista = userDetails.getAuthenticatedUser().getListAdminMenus();
			mv.get().addObject("listBean", lista);
			
		}
		return mv.get();
	}
	
}
