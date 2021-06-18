package br.com.hfs.admin.view.admParameterCategory;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmParameterCategoryMB
		extends BaseViewRegister<AdmParameterCategory, Long, AdmParameterCategoryService>
		implements IBaseViewRegister<AdmParameterCategory> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryMB(){
		super(AdmParameterCategory.class,
				"admin/admParameterCategory/listAdmParameterCategory", 
				"admin/admParameterCategory/editAdmParameterCategory");
	}
	
	@PostConstruct
	public void init() {
		updateDataTableList();
		beanInSession();
	}

	@Override
	public String onInsert() {
		return super.onInsert(new AdmParameterCategory());
	}
	
	@Override
	public String save() {
		return super.save(getEntity().getId(), getEntity().getDescription());
	}

	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmParameterCategory");
	}

	@Override
	public AdmParameterCategory getBean() {
		return super.getEntity();
	}

	@Override
	public void setBean(AdmParameterCategory entity) {
		super.setEntity(entity);
	}

	@Override
	public List<AdmParameterCategory> getListBean() {
		return super.getListEntity();
	}

	@Override
	public void setListBean(List<AdmParameterCategory> listEntity) {
		super.setListEntity(listEntity);
	}

}
