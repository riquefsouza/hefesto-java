package br.com.hfs.admin.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.admin.model.AdmParameterCategory;

public class AdmParameterCategoryDTO {

	private Long id;
	
	private String description;

	private Long order;

	public AdmParameterCategoryDTO() {
		super();
	}

	public AdmParameterCategoryDTO(AdmParameterCategory obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.order = obj.getOrder();
	}

	public static List<AdmParameterCategoryDTO> convert(List<AdmParameterCategory> admParameterCategorys) {
		return admParameterCategorys.stream().map(AdmParameterCategoryDTO::new).collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}

	public Long getOrder() {
		return order;
	}

	public Long getId() {
		return id;
	}
}
