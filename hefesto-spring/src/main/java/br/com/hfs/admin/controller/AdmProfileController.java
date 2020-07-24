package br.com.hfs.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseDualList;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admProfileView")
public class AdmProfileController extends BaseViewRegister<AdmProfile, Long, AdmProfileService> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdmPageService admPageService;
	
	private BaseDualList<AdmPage> dualListAdmPage;
	
	private List<AdmPage> listAllAdmPages;
	
	@Autowired
	private AdmUserService admUserService;
	
	private BaseDualList<AdmUser> dualListAdmUser;
	
	private List<AdmUser> listAllAdmUsers;	
	
	public AdmProfileController() {
		super("/private/admProfile/listAdmProfile", 
				"/private/admProfile/editAdmProfile", 
				"AdmProfile", AdmProfile.class);
		this.listAllAdmPages = new ArrayList<AdmPage>();
		this.listAllAdmUsers = new ArrayList<AdmUser>();
	}
	
	private void loadAdmPages(AdmProfile bean, boolean bEdit) {
		List<AdmPage> listAdmPagesSelected;		 
		List<AdmPage> listAdmPages = admPageService.findAll();
		listAllAdmPages.addAll(listAdmPages);
		
		if (bEdit) { 
			listAdmPagesSelected = new ArrayList<AdmPage>(bean.getAdmPages());
			listAdmPages.removeAll(listAdmPagesSelected);
		} else {
			listAdmPagesSelected = new ArrayList<AdmPage>();
		}
		
		this.dualListAdmPage = new BaseDualList<AdmPage>(listAdmPages, listAdmPagesSelected);		
	}
	
	private void loadAdmUsers(AdmProfile bean, boolean bEdit) {
		List<AdmUser> listAdmUsersSelected;		 
		List<AdmUser> listAdmUsers = admUserService.findAll();
		listAllAdmUsers.addAll(listAdmUsers);
		
		if (bEdit) { 
			listAdmUsersSelected = new ArrayList<AdmUser>(bean.getAdmUsers());
			listAdmUsers.removeAll(listAdmUsersSelected);
		} else {
			listAdmUsersSelected = new ArrayList<AdmUser>();
		}
		
		this.dualListAdmUser = new BaseDualList<AdmUser>(listAdmUsers, listAdmUsersSelected);		
	}	
	
	@Override
	@GetMapping("/add")	
	public ModelAndView add(AdmProfile bean) {
		Optional<ModelAndView> mv = getPage(getEditPage());
		bean = new AdmProfile();
				
		if (mv.isPresent()) {
			loadAdmPages(bean, false);
			mv.get().addObject("listSourceAdmPages", this.dualListAdmPage.getSource());

			loadAdmUsers(bean, false);
			mv.get().addObject("listSourceAdmUsers", this.dualListAdmUser.getSource());
			
			mv.get().addObject("bean", bean);
		}
		
		return mv.get();
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		Optional<ModelAndView> mv = getPage(getEditPage());

		Optional<AdmProfile> obj = service.findById(id);
		if (obj.isPresent() && mv.isPresent()) {
			loadAdmPages(obj.get(), true);
			mv.get().addObject("listSourceAdmPages", this.dualListAdmPage.getSource());
			mv.get().addObject("listTargetAdmPages", this.dualListAdmPage.getTarget());

			loadAdmUsers(obj.get(), true);
			mv.get().addObject("listSourceAdmUsers", this.dualListAdmUser.getSource());
			mv.get().addObject("listTargetAdmUsers", this.dualListAdmUser.getTarget());
			
			mv.get().addObject("bean", obj.get());
		}
		
		return mv.get();
	}
	
	@Override
	@PostMapping
	public ModelAndView save(@Valid @ModelAttribute AdmProfile bean, 
			BindingResult result, RedirectAttributes attributes) {
	
		Optional<ModelAndView> mv = getPage(this.getListPage());
		
		return this.saveCallableBefore(bean, result, attributes, new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				
				if (mv.isPresent()) {
					
					bean.getAdmPages().forEach(item -> {
						listAllAdmPages.stream()
							.filter(p -> p.getId().equals(item.getId()))
							.findFirst()
							.ifPresent(x -> item.setDescription(x.getDescription()));	
					});
					bean.getAdmUsers().forEach(item -> {
						listAllAdmUsers.stream()
							.filter(p -> p.getId().equals(item.getId()))
							.findFirst()
							.ifPresent(x -> item.setName(x.getName()));	
					});
					
					mv.get().addObject("bean", bean);
				}
				
				return "";
			}
		});
	}	
}
