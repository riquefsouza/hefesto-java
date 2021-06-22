/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.admin.service.AdmParameterService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmParameterMB extends
BaseViewRegister<AdmParameter, Long, AdmParameterService>
		implements IBaseViewRegister<AdmParameter> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Inject
	private AdmParameterCategoryService admParameterCategoryService;

	private List<AdmParameterCategory> listAdmParameterCategory;

	/**
	 * Instantiates a new adm parametro MB.
	 */
	public AdmParameterMB() {
		super(AdmParameter.class, 
				"admin/admParameter/listAdmParameter", 
				"admin/admParameter/editAdmParameter");

		listAdmParameterCategory = new ArrayList<AdmParameterCategory>();
	}

	@PostConstruct
	public void init() {
		listAdmParameterCategory = admParameterCategoryService.findAll();
		updateDataTableList();
		beanInSession();
	}

	/**
	 * Select adm parametro categoria.
	 */
	public void selectAdmParameterCategory(AdmParameter bean) {
		AdmParameterCategory admParametroCategoria = admParameterCategoryService
				.findById(bean.getAdmParameterCategory().getId()).get();
		bean.setAdmParameterCategory(admParametroCategoria);
	}
	
	private void setAdmParameterCategory(AdmParameter bean) {
		if (bean.getAdmParameterCategory() != null && listAdmParameterCategory.size() > 0) {
			bean.getAdmParameterCategory().setId(listAdmParameterCategory.get(0).getId());
			selectAdmParameterCategory(bean);
		}		
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#onIncluir()
	 */
	@Override
	public String onInsert() {
		AdmParameter obj = new AdmParameter();
		setAdmParameterCategory(obj);
		return super.onInsert(obj);
	}
	
	@Override
	public String onEdit(AdmParameter entity) {
		return super.onEdit(entity);
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#salvar()
	 */
	@Override
	public String save() {
		getBean().setIdAdmParameterCategory(getBean().getAdmParameterCategory().getId());
		return super.save(getBean().getId(), getBean().getDescription());
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.BaseViewCadastro#excluir(java.lang.Object)
	 */
	@Override
	public void delete(AdmParameter entity) {
		super.delete(entity);
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#preProcessPDF(java.lang.Object)
	 */
	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmParameter");
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getBean()
	 */
	@Override
	public AdmParameter getBean() {
		return super.getEntity();
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#setBean(java.lang.Object)
	 */
	@Override
	public void setBean(AdmParameter entity) {
		super.setEntity(entity);
	}

	@Override
	public List<AdmParameter> getListBean() {
		return super.getListEntity();
	}

	@Override
	public void setListBean(List<AdmParameter> listEntity) {
		super.setListEntity(listEntity);
	}

	public List<AdmParameterCategory> getListAdmParameterCategory() {
		return listAdmParameterCategory;
	}

	public void setListaAdmParameterCategory(List<AdmParameterCategory> listAdmParameterCategory) {
		this.listAdmParameterCategory = listAdmParameterCategory;
	}

}
