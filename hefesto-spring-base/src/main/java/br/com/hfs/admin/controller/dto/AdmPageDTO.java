package br.com.hfs.admin.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.admin.model.AdmPage;

public class AdmPageDTO {

	private Long id;
	
	private String description;

	private String url;
		
	public AdmPageDTO() {
		super();
	}

	public AdmPageDTO(AdmPage obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.url = obj.getUrl();
	}

	public static List<AdmPageDTO> convert(List<AdmPage> admPages) {
		return admPages.stream().map(AdmPageDTO::new).collect(Collectors.toList());
	}

	public static Page<AdmPageDTO> convert(Page<AdmPage> pg) {
		return pg.map(AdmPageDTO::new);
	}
	
	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public Long getId() {
		return id;
	}
}
