package br.com.hfs.admin.controller.form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmProfileService;

public class AdmProfileForm {

	private Long id;
	
	private Boolean administrator;

	private String description;

	private Boolean general;
	
	private List<AdmPageForm> admPages;
	
	private List<AdmUserForm> admUsers;

	public AdmProfileForm() {
		super();
	}

	public AdmProfileForm(String description, Boolean administrator, Boolean general,
			List<Long> idAdmPages, List<Long> idAdmUsers) {
		super();
		this.id = null;
		this.administrator = administrator;
		this.description = description;
		this.general = general;
		this.admPages = AdmPageForm.convertFromListIds(idAdmPages);
		this.admUsers = AdmUserForm.convertFromListIds(idAdmUsers);
	}

	public AdmProfileForm(AdmProfile obj) {
		super();
		this.id = obj.getId();
		this.administrator = obj.getAdministrator();
		this.description = obj.getDescription();
		this.general = obj.getGeneral();
		
		List<AdmPage> listPages = new ArrayList<AdmPage>(obj.getAdmPages()); 
		this.admPages = AdmPageForm.convert(listPages);
		List<AdmUser> listUsers = new ArrayList<AdmUser>(obj.getAdmUsers()); 
		this.admUsers = AdmUserForm.convert(listUsers);
	}

	public AdmProfile convert() {
		AdmProfile obj = new AdmProfile(description, administrator, general);
		
		List<AdmPage> listPages = new ArrayList<AdmPage>(obj.getAdmPages()); 
		this.admPages = AdmPageForm.convert(listPages);
		List<AdmUser> listUsers = new ArrayList<AdmUser>(obj.getAdmUsers()); 
		this.admUsers = AdmUserForm.convert(listUsers);
		
		return obj; 
	}

	public AdmProfile update(Long id, AdmProfileService service) {
		Optional<AdmProfile> bean = service.findById(id);
		if (bean.isPresent()) {
			bean.get().setAdministrator(this.administrator);
			bean.get().setDescription(this.description);
			bean.get().setGeneral(this.general);
			bean.get().setAdmPages(new HashSet<AdmPage>(AdmPageForm.convertFromForm(this.admPages)));
			bean.get().setAdmUsers(new HashSet<AdmUser>(AdmUserForm.convertFromForm(this.admUsers)));
			return bean.get();
		}
		return null;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getGeneral() {
		return general;
	}

	public void setGeneral(Boolean general) {
		this.general = general;
	}

	public List<AdmPageForm> getAdmPages() {
		return admPages;
	}

	public void setAdmPages(List<AdmPageForm> admPages) {
		this.admPages = admPages;
	}

	public List<AdmUserForm> getAdmUsers() {
		return admUsers;
	}

	public void setAdmUsers(List<AdmUserForm> admUsers) {
		this.admUsers = admUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
