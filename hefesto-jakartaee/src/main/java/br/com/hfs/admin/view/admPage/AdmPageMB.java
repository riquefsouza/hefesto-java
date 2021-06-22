/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.admin.view.admPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.model.DualListModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmPageMB extends BaseViewRegister<AdmPage, Long, AdmPageService> implements IBaseViewRegister<AdmPage> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Inject
	private AdmProfileService admProfileService;

	/** The dual list adm perfil. */
	private DualListModel<AdmProfile> dualListAdmProfile;

	/** The lista adm perfil. */
	private List<AdmProfile> listaAdmProfile;
	
	public AdmPageMB() {
		super(AdmPage.class, 
				"admin/admPage/listAdmPage", 
				"admin/admPage/editAdmPage");
	}

	@PostConstruct
	public void init() {
		updateDataTableList();
		
		if (beanInSession()) {
			onEditMode(getBean());
		}
		
		onInserMode();
	}

	private void loadAdmPerfis(AdmPage bean) {
		List<AdmProfile> listaAdmProfileSelecionado = bean.getId() == null ? new ArrayList<AdmProfile>()
				: this.getService().findProfilesByPage(bean);
		this.listaAdmProfile = admProfileService.findAll();
		this.listaAdmProfile.removeAll(listaAdmProfileSelecionado);
		this.dualListAdmProfile = new DualListModel<AdmProfile>(this.listaAdmProfile, listaAdmProfileSelecionado);
	}

	@Override
	public String onInsert() {
		return super.onInsert(new AdmPage());
	}
	
	private void onInserMode() {
		if (getState().isInsertMode()) {
			this.listaAdmProfile = admProfileService.findAll();
		    this.dualListAdmProfile = new DualListModel<AdmProfile>(this.listaAdmProfile, new ArrayList<AdmProfile>());
		}
	}
	
	private void onEditMode(AdmPage bean) {
		if (getState().isEditMode()) {
			loadAdmPerfis(bean);
		}
	}
	
	@Override
	public String onEdit(AdmPage entity) {
		return super.onEdit(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#salvar()
	 */
	@Override
	public String save() {
		getBean().setAdmProfiles(new HashSet<AdmProfile>(this.dualListAdmProfile.getTarget()));
		return super.save(getBean().getId());
	}

	@Override
	public void delete(AdmPage entity) {
		super.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.jus.trt1.hfsframework.base.IBaseViewCadastro#preProcessPDF(java.lang
	 * .Object)
	 */
	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmPage");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getBean()
	 */
	@Override
	public AdmPage getBean() {
		return super.getEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#setBean(java.lang.
	 * Object)
	 */
	@Override
	public void setBean(AdmPage entity) {
		super.setEntity(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#getListaBean()
	 */
	@Override
	public List<AdmPage> getListBean() {
		return super.getListEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.jus.trt1.hfsframework.base.IBaseViewCadastro#setListaBean(java.util.
	 * List)
	 */
	@Override
	public void setListBean(List<AdmPage> listEntity) {
		super.setListEntity(listEntity);
	}

	/**
	 * Gets the dual list adm perfil.
	 *
	 * @return the dual list adm perfil
	 */
	public DualListModel<AdmProfile> getDualListAdmProfile() {
		return dualListAdmProfile;
	}
		
	/**
	 * Sets the dual list adm perfil.
	 *
	 * @param dualListAdmProfile the new dual list adm perfil
	 */
	public void setDualListAdmProfile(DualListModel<AdmProfile> dualListAdmProfile) {
		this.dualListAdmProfile = dualListAdmProfile;
	}
}
