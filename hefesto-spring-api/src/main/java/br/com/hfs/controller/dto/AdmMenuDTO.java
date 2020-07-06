package br.com.hfs.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.model.AdmMenu;

public class AdmMenuDTO {

	private Long id;
	
	private String description;

	private Integer order;
	
	private Long idPage;
	
	private Long idMenuParent;
		
	public AdmMenuDTO() {
		super();
	}

	public AdmMenuDTO(AdmMenu obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.order = obj.getOrder();
		/*
		if (obj.getAdmPage()!=null)
			this.admPage = new AdmPageDTO(obj.getAdmPage());
		else
			this.admPage = new AdmPageDTO();
		*/
		this.idPage = obj.getIdPage();
		this.idMenuParent = obj.getIdMenuParent();
	}

	public static List<AdmMenuDTO> convert(List<AdmMenu> admMenus) {
		return admMenus.stream().map(AdmMenuDTO::new).collect(Collectors.toList());
	}
	
	public static Page<AdmMenuDTO> convert(Page<AdmMenu> pg) {
		return pg.map(AdmMenuDTO::new);
	}

	public String getDescription() {
		return description;
	}

	public Integer getOrder() {
		return order;
	}
	
	public Long getIdMenuParent() {
		return idMenuParent;
	}

	public Long getId() {
		return id;
	}

	public Long getIdPage() {
		return idPage;
	}
}
