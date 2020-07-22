package br.com.hfs.admin.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.admin.model.AdmParameter;

public class AdmParameterDTO {

	private Long id;
	
	private String code;

	private String description;

	private String value;

	private AdmParameterCategoryDTO admParameterCategory;

	public AdmParameterDTO() {
		super();
	}

	public AdmParameterDTO(AdmParameter obj) {
		this.id = obj.getId();
		this.code = obj.getCode();
		this.description = obj.getDescription();
		this.value = obj.getValue();
		this.admParameterCategory = new AdmParameterCategoryDTO(obj.getAdmParameterCategory());
	}

	public static List<AdmParameterDTO> convert(List<AdmParameter> admParameters) {
		return admParameters.stream().map(AdmParameterDTO::new).collect(Collectors.toList());
	}
	
	public static Page<AdmParameterDTO> convert(Page<AdmParameter> pg) {
		return pg.map(AdmParameterDTO::new);
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

	public AdmParameterCategoryDTO getAdmParameterCategory() {
		return admParameterCategory;
	}

	public Long getId() {
		return id;
	}
}
