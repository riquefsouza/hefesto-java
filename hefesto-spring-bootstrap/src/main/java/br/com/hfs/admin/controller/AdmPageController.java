package br.com.hfs.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
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
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.base.BaseDualList;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admPageView")
public class AdmPageController extends BaseViewRegister<AdmPage, Long, AdmPageService> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdmProfileService admProfileService;
	
	private BaseDualList<AdmProfile> dualListProfile;
	
	private List<AdmProfile> listAllAdmProfiles;
	
	public AdmPageController() {
		super("/private/admPage/listAdmPage", 
				"/private/admPage/editAdmPage", 
				"AdmPage", AdmPage.class);
		this.listAllAdmProfiles = new ArrayList<AdmProfile>();
	}
	
	@PostConstruct
	public void init() {
		//
	}
	
	private void loadProfiles(AdmPage bean, boolean bEdit) {
		List<AdmProfile> listProfilesSelected;		 
		List<AdmProfile> listProfiles = admProfileService.findAll();
		
		listAllAdmProfiles.clear();
		listAllAdmProfiles.addAll(listProfiles);
		
		if (bEdit) { 
			listProfilesSelected = new ArrayList<AdmProfile>();
			
			/*
			bean.getAdmProfiles().forEach(id -> {
				listProfilesSelected.add(listAllAdmProfiles.stream()
						.filter(p -> p.getId().equals(id))
						.findFirst().get());	
			});
			*/
			
			for (AdmProfile profile : listAllAdmProfiles) {
				for (AdmPage page : profile.getAdmPages()) {
					if (page.equals(bean)) {
						listProfilesSelected.add(profile);
						break;
					}
				}
			}
			
			
			listProfiles.removeAll(listProfilesSelected);
		} else {
			listProfilesSelected = new ArrayList<AdmProfile>();
		}
		
		this.dualListProfile = new BaseDualList<AdmProfile>(listProfiles, listProfilesSelected);		
	}
	
	@Override
	@GetMapping("/add")	
	public ModelAndView add(AdmPage bean) {
		Optional<ModelAndView> mv = getPage(getEditPage());
		bean = new AdmPage();
					
		if (mv.isPresent()) {
			loadProfiles(bean, false);
			mv.get().addObject("listSourceProfiles", this.dualListProfile.getSource());
			mv.get().addObject("bean", bean);
		}
		
		return mv.get();
	}

	@Override
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		Optional<ModelAndView> mv = getPage(getEditPage());

		Optional<AdmPage> obj = service.findById(id);
		if (obj.isPresent() && mv.isPresent()) {
			loadProfiles(obj.get(), true);
			mv.get().addObject("listSourceProfiles", this.dualListProfile.getSource());
			mv.get().addObject("listTargetProfiles", this.dualListProfile.getTarget());
			mv.get().addObject("bean", obj.get());
		}
		
		return mv.get();
	}

	@Override
	@PostMapping
	public ModelAndView save(@Valid @ModelAttribute AdmPage bean, 
			BindingResult result, RedirectAttributes attributes) {
	
		Optional<ModelAndView> mv = getPage(this.getListPage());
		
		return this.saveCallableBefore(bean, result, attributes, new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				
				if (mv.isPresent()) {
					/*
					bean.getAdmProfiles().forEach(item -> {					
						listAllAdmProfiles.stream()
							.filter(p -> p.getId().equals(item))
							.findFirst();	
					});
					*/
					mv.get().addObject("bean", bean);
				}
				
				return "";
			}
		});
	}
		
}
