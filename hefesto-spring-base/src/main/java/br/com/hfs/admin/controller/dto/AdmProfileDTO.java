package br.com.hfs.admin.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;

public class AdmProfileDTO {

	private Long id;
	
	private Boolean administrator;

	private String description;
	
	private Boolean general;
	
	private List<AdmPageDTO> admPages;
	
	private List<AdmUserDTO> admUsers;

	private String profilePages;

	private String profileUsers;

	public AdmProfileDTO() {
		super();
		this.admPages = new ArrayList<AdmPageDTO>();
		this.admUsers = new ArrayList<AdmUserDTO>();
	}
	
	public AdmProfileDTO(AdmProfile obj) {
		this.id = obj.getId();
		this.administrator = obj.getAdministrator();
		this.description = obj.getDescription();
		this.general = obj.getGeneral();
		List<AdmPage> listPages = new ArrayList<AdmPage>(obj.getAdmPages()); 
		this.admPages = AdmPageDTO.convert(listPages);
		List<AdmUser> listUsers = new ArrayList<AdmUser>(obj.getAdmUsers()); 
		this.admUsers = AdmUserDTO.convert(listUsers);
		
		List<String> listProfilePages = new ArrayList<String>();
		listPages.forEach(page -> listProfilePages.add(page.getDescription()));
		this.profilePages = listProfilePages.stream().collect(Collectors.joining(","));
		
		List<String> listProfileUsers = new ArrayList<String>();
		listUsers.forEach(user -> listProfileUsers.add(user.getName()));
		this.profileUsers = listProfileUsers.stream().collect(Collectors.joining(","));		
	}

	public static List<AdmProfileDTO> convert(List<AdmProfile> admProfiles) {
		return admProfiles.stream().map(AdmProfileDTO::new).collect(Collectors.toList());
	}
	
	public static Page<AdmProfileDTO> convert(Page<AdmProfile> pg) {
		return pg.map(AdmProfileDTO::new);
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getGeneral() {
		return general;
	}

	public List<AdmPageDTO> getAdmPages() {
		return admPages;
	}

	public List<AdmUserDTO> getAdmUsers() {
		return admUsers;
	}

	public Long getId() {
		return id;
	}

	public String getProfilePages() {
		return profilePages;
	}

	public String getProfileUsers() {
		return profileUsers;
	}

}
