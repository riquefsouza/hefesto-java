package br.com.hfs.admin.controller.form;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;

public class AdmMenuForm {
	
	private Long id;
	
	private String description;

	private Integer order;
	
	private Long idPage;
	
	private Long idMenuParent;
		
	public AdmMenuForm() {
		super();
	}
	
	public AdmMenuForm(String description, Integer order, Long idPage, Long idMenuParent) {
		super();
		this.description = description;
		this.order = order;
		this.idPage = idPage;
		this.idMenuParent = idMenuParent;
	}

	public AdmMenuForm(AdmMenu obj) {
		this.id = obj.getId();
		this.description = obj.getDescription();
		this.order = obj.getOrder();
		//this.admPage = new AdmPageForm(obj.getAdmPage());
		this.idPage = obj.getIdPage();
		this.idMenuParent = obj.getIdMenuParent();
	}
	
	public AdmMenu convert() {
		return new AdmMenu(description, idMenuParent, idPage, order);
	}

	public AdmMenu update(Long id, AdmMenuService service) {
		Optional<AdmMenu> bean = service.findById(id);
		if (bean.isPresent()) {
			bean.get().setDescription(this.description);
			bean.get().setIdMenuParent(this.idMenuParent);
			bean.get().setIdPage(this.idPage);
			bean.get().setOrder(this.order);
			return bean.get();	
		}
		return null;
	}

	public static List<AdmMenuForm> convert(List<AdmMenu> admMenus) {
		return admMenus.stream().map(AdmMenuForm::new).collect(Collectors.toList());
	}

	public static List<AdmMenu> convertFromForm(List<AdmMenuForm> admMenus) {
		return admMenus.stream().map(AdmMenu::new).collect(Collectors.toList());
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getIdMenuParent() {
		return idMenuParent;
	}

	public void setIdMenuParent(Long idMenuParent) {
		this.idMenuParent = idMenuParent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPage() {
		return idPage;
	}

	public void setIdPage(Long idPage) {
		this.idPage = idPage;
	}

}
