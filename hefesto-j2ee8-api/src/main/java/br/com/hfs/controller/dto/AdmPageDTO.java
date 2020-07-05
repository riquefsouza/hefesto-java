package br.com.hfs.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.model.AdmPage;

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
