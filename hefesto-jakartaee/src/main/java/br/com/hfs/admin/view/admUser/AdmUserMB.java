package br.com.hfs.admin.view.admUser;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmUserMB extends
		BaseViewRegister<AdmUser, Long, AdmUserService>
		implements IBaseViewRegister<AdmUser> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public AdmUserMB() {
		super(AdmUser.class,
				"admin/admUser/listAdmUser", 
				"admin/admUser/editAdmUser");
	}

	@PostConstruct
	public void init() {
		updateDataTableList();
		beanInSession();
	}
	
	@Override
	public String onInsert() {
		return super.onInsert(new AdmUser());
	}

	@Override
	public String save() {
		return super.save(getBean().getId(), getBean().getLogin());
	}

	@Override
	public void delete(AdmUser entity) {
		super.delete(entity);
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#preProcessPDF(java.lang.Object)
	 */
	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmUser");
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getBean()
	 */
	@Override
	public AdmUser getBean() {
		return super.getEntity();
	}

	/* (non-Javadoc)
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#setBean(java.lang.Object)
	 */
	@Override
	public void setBean(AdmUser entity) {
		super.setEntity(entity);
	}

	@Override
	public List<AdmUser> getListBean() {
		return super.getListEntity();
	}

	@Override
	public void setListBean(List<AdmUser> listEntity) {
		super.setListEntity(listEntity);		
	}

}
