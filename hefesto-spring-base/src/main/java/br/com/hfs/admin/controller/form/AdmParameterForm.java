package br.com.hfs.admin.controller.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;

public class AdmParameterForm {

	private Long id;
	
	private String code;

	private String description;

	private String value;

	private AdmParameterCategoryForm admParameterCategory;

	public AdmParameterForm() {
		super();
	}

	public AdmParameterForm(AdmParameter obj) {
		this.id = obj.getId();
		this.code = obj.getCode();
		this.description = obj.getDescription();
		this.value = obj.getValue();
		this.admParameterCategory = new AdmParameterCategoryForm(obj.getAdmParameterCategory());
	}

	public AdmParameterForm(String code, String description, String value,
			Long idAdmParameterCategory) {
		super();
		this.code = code;
		this.description = description;
		this.value = value;
		this.admParameterCategory = new AdmParameterCategoryForm(idAdmParameterCategory);
	}

	public AdmParameter convert() {
		return new AdmParameter(value, description, code, admParameterCategory.getId());
	}

	public AdmParameter update(Long id, AdmParameterService service) {
		Optional<AdmParameter> bean = service.findById(id);
		if (bean.isPresent()) {
			bean.get().setValue(this.value);
			bean.get().setDescription(this.description);
			bean.get().setCode(this.code);
			bean.get().setIdAdmParameterCategory(admParameterCategory.getId());
			return bean.get();	
		}
		return null;
	}
	
	public static List<AdmParameterForm> convert(List<AdmParameter> admParameters) {
		return admParameters.stream().map(AdmParameterForm::new).collect(Collectors.toList());
	}
	
	public static List<AdmParameter> convertFromForm(List<AdmParameterForm> admParameters) {
		return admParameters.stream().map(AdmParameter::new).collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public AdmParameterCategoryForm getAdmParameterCategory() {
		return admParameterCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setAdmParameterCategory(AdmParameterCategoryForm admParameterCategory) {
		this.admParameterCategory = admParameterCategory;
	}
}
