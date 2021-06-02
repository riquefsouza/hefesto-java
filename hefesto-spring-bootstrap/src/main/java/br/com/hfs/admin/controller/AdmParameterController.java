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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.base.BaseViewRegister;

@Controller
@RequestMapping(value = "/private/admin/admParameterView")
@SessionAttributes("listMenu")
public class AdmParameterController
		extends BaseViewRegister<AdmParameter, Long, AdmParameterService> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;

	private List<AdmParameterCategory> listAdmParameterCategory;
	
	public AdmParameterController() {
		super("/private/admin/admParameter/listAdmParameter",
				"/private/admin/admParameter/editAdmParameter", 
				"AdmParameter", AdmParameter.class);
		
		this.listAdmParameterCategory = new ArrayList<AdmParameterCategory>();
	}

	@Override
	@GetMapping("/add")	
	public ModelAndView add(AdmParameter bean) {
		Optional<ModelAndView> mv = getPage(getEditPage());
		bean = new AdmParameter();
				
		if (mv.isPresent()) {
			listAdmParameterCategory = admParameterCategoryService.findAll();
			mv.get().addObject("listAdmCategories", listAdmParameterCategory);
			mv.get().addObject("bean", bean);
		}
		
		return mv.get();
	}
	
	@Override
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		Optional<ModelAndView> mv = getPage(getEditPage());

		Optional<AdmParameter> obj = service.findById(id);
		if (obj.isPresent() && mv.isPresent()) {
			listAdmParameterCategory = admParameterCategoryService.findAll();
			mv.get().addObject("listAdmCategories", listAdmParameterCategory);
			mv.get().addObject("bean", obj.get());
		}
		
		return mv.get();
	}
	
	@Override
	@PostMapping
	public ModelAndView save(@Valid @ModelAttribute AdmParameter bean, 
			BindingResult result, RedirectAttributes attributes) {
	
		Optional<ModelAndView> mv = getPage(this.getListPage());
		
		return this.saveCallableBefore(bean, result, attributes, new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				
				if (mv.isPresent()) {

					listAdmParameterCategory.stream()
					.filter(p -> p.getId().equals(bean.getAdmParameterCategory().getId()))
					.findFirst()					
					.ifPresent(x -> { 
						bean.getAdmParameterCategory().setDescription(x.getDescription());
						bean.setIdAdmParameterCategory(x.getId());
					});
					
					mv.get().addObject("bean", bean);
				}
				
				return "";
			}
		});
	}
	
}
