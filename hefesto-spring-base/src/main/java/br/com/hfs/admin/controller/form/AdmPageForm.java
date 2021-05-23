package br.com.hfs.admin.controller.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;

public class AdmPageForm {
	
	private Long id;
	
	private String description;

	private String url;
		
	public AdmPageForm() {
		super();
	}
	
	public AdmPageForm(Long id) {
		super();
		this.id = id;
	}

	public AdmPageForm(String description, String url) {
		super();
		this.id = null;
		this.description = description;
		this.url = url;
	}

	public AdmPageForm(AdmPage obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.url = obj.getUrl();
	}
	
	public AdmPage convert() {
		return new AdmPage(url, description);
	}

	public AdmPage update(Long id, AdmPageService service) {
		Optional<AdmPage> bean = service.findById(id);
		if (bean.isPresent()) {
			bean.get().setDescription(this.description);
			bean.get().setUrl(this.url);
			return bean.get();	
		}
		return null;
	}
	
	public static List<AdmPageForm> convert(List<AdmPage> admPages) {
		return admPages.stream().map(AdmPageForm::new).collect(Collectors.toList());
	}

	public static List<AdmPage> convertFromForm(List<AdmPageForm> admPages) {
		return admPages.stream().map(AdmPage::new).collect(Collectors.toList());
	}

	public static List<AdmPageForm> convertFromListIds(List<Long> admPages) {
		return admPages.stream().map(id -> new AdmPageForm(id)).collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
