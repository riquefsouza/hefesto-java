package br.com.hfs.admin.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.admin.model.AdmPage;

public class AdmPageDTO {

	private Long id;
	
	private String description;

	private String url;
	
	private List<Long> admIdProfiles;
	
	private String pageProfiles;
		
	public AdmPageDTO() {
		super();
		this.admIdProfiles = new ArrayList<Long>();
	}

	public AdmPageDTO(AdmPage obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.url = obj.getUrl();
		
		this.admIdProfiles = new ArrayList<Long>();
		obj.getAdmProfiles().forEach(profile -> this.admIdProfiles.add(profile.getId()));
		
		List<String> listPageProfiles = new ArrayList<String>();
		obj.getAdmProfiles().forEach(profile -> listPageProfiles.add(profile.getDescription()));
		this.pageProfiles = listPageProfiles.stream().collect(Collectors.joining(","));		
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

	public List<Long> getAdmIdProfiles() {
		return admIdProfiles;
	}

	public String getPageProfiles() {
		return pageProfiles;
	}
}
