package br.com.hfs.admin.controller.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;

public class AdmParameterCategoryForm {

	private Long id;
	
	private String description;

	private Long order;

	public AdmParameterCategoryForm() {
		super();
	}

	public AdmParameterCategoryForm(AdmParameterCategory obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.order = obj.getOrder();
	}
	
	public AdmParameterCategory convert() {
		return new AdmParameterCategory(description, order);
	}

	public AdmParameterCategory update(Long id, AdmParameterCategoryService service) {
		Optional<AdmParameterCategory> bean = service.findById(id);
		if (bean.isPresent()) {
			bean.get().setDescription(this.description);
			bean.get().setOrder(this.order);
			return bean.get();	
		}
		return null;
	}
	
	public static List<AdmParameterCategoryForm> convert(List<AdmParameterCategory> admParameterCategorys) {
		return admParameterCategorys.stream().map(AdmParameterCategoryForm::new).collect(Collectors.toList());
	}
	
	public static List<AdmParameterCategory> convertFromForm(List<AdmParameterCategoryForm> admParameterCategorys) {
		return admParameterCategorys.stream().map(AdmParameterCategory::new).collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}

	public Long getOrder() {
		return order;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
