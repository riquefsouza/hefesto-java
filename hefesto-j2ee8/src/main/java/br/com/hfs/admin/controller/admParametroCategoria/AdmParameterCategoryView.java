package br.com.hfs.admin.controller.admParametroCategoria;

import java.io.IOException;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

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
public class AdmParameterCategoryView
		extends BaseViewRegister<AdmParameterCategory, Long, AdmParameterCategoryService>
		implements IBaseViewRegister<AdmParameterCategory> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public AdmParameterCategoryView(String pageList, String pageEdit){
		super("ListAdmParameterCategory", "EditAdmParameterCategory");	
	}
	
	@Override
	public void init() {
		updateDataTableList();
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

}
