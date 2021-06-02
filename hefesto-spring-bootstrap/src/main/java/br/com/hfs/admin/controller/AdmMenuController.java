package br.com.hfs.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmMenuService;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.admin.vo.MenuVO;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.security.HfsUserDetails;

@Controller
@RequestMapping(value = "/private/admin/admMenuView")
@SessionAttributes("listMenu")
public class AdmMenuController extends BaseViewRegister<AdmMenu, Long, AdmMenuService> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdmPageService admPageService;

	private List<AdmPage> listAdmPage;
	
	private List<AdmMenu> listAdmMenuParent;
	
	public AdmMenuController() {
		super("/private/admin/admMenu/listAdmMenu", 
				"/private/admin/admMenu/editAdmMenu", 
				"AdmMenu", AdmMenu.class);
		
		this.listAdmPage = new ArrayList<AdmPage>();
		this.listAdmMenuParent = new ArrayList<AdmMenu>();
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
	
	private void fillLists(Optional<ModelAndView> mv) {
		listAdmPage = admPageService.findAll();
		mv.get().addObject("listAdmPages", listAdmPage);
		
		this.listAdmMenuParent.clear();
		
		List<AdmMenu> listAdmMenus = this.service.findAll();		
		for (AdmMenu menu : listAdmMenus) {
			if ((menu.getAdmSubMenus() != null) && (menu.getAdmPage() == null)) {
				this.listAdmMenuParent.add(menu);
			}
		}
		
		mv.get().addObject("listAdmMenuParents", listAdmMenuParent);
	}
	
	private void filterLists(AdmMenu bean) {
		listAdmPage.stream()
		.filter(p -> p.getId().equals(bean.getAdmPage().getId()))
		.findFirst()					
		.ifPresent(x -> {
			bean.setAdmPage(x);
			bean.setIdPage(x.getId());
		});
		
		listAdmMenuParent.stream()
		.filter(p -> p.getId().equals(bean.getAdmMenuParent().getId()))
		.findFirst()					
		.ifPresent(x -> {
			bean.setAdmMenuParent(x);
			bean.setIdMenuParent(x.getId());
		});
	}
	
	@Override
	@GetMapping("/add")	
	public ModelAndView add(AdmMenu bean) {
		Optional<ModelAndView> mv = getPage(getEditPage());
		bean = new AdmMenu();
				
		if (mv.isPresent()) {			
			fillLists(mv);
			mv.get().addObject("bean", bean);
		}
		
		return mv.get();
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		Optional<ModelAndView> mv = getPage(getEditPage());

		Optional<AdmMenu> obj = service.findById(id);
		if (obj.isPresent() && mv.isPresent()) {
			fillLists(mv);
			mv.get().addObject("bean", obj.get());
		}
		
		return mv.get();
	}
	
	@Override
	@PostMapping
	public ModelAndView save(@Valid @ModelAttribute AdmMenu bean, 
			BindingResult result, RedirectAttributes attributes) {
	
		Optional<ModelAndView> mv = getPage(this.getListPage());
		
		return this.saveCallableBefore(bean, result, attributes, new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				
				if (mv.isPresent()) {

					filterLists(bean);
					
					mv.get().addObject("bean", bean);
				}
				
				return "";
			}
		});
	}

}
