package br.com.hfs.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.model.AdmMenu;

public class AdmMenuDTO {

	private Long id;
	
	private String description;

	private Integer order;
	
	private AdmPageDTO admPage;
	
	private Long idMenuParent;
		
	public AdmMenuDTO() {
		super();
	}

	public AdmMenuDTO(AdmMenu obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.order = obj.getOrder();
		this.admPage = new AdmPageDTO(obj.getAdmPage());
		this.idMenuParent = obj.getIdMenuParent();
	}

	public static List<AdmMenuDTO> convert(List<AdmMenu> admMenus) {
		return admMenus.stream().map(AdmMenuDTO::new).collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}

	public Integer getOrder() {
		return order;
	}

	public AdmPageDTO getAdmPage() {
		return admPage;
	}

	public Long getIdMenuParent() {
		return idMenuParent;
	}

	public Long getId() {
		return id;
	}
}
