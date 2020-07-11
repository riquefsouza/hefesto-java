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

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
public class AdmPageMB extends
BaseViewRegister<AdmPage, Long, AdmPageService>
		implements IBaseViewRegister<AdmPage> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Inject
	private AdmProfileService admProfileService;

	/** The dual list adm perfil. */
	private DualListModel<AdmProfile> dualListAdmProfile;

	/** The lista adm perfil. */
	private List<AdmProfile> listaAdmProfile;
	
	/**
	 * Instantiates a new adm pagina MB.
	 */
	public AdmPageMB() {
		super("ListAdmPage", "EditAdmPage");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jus.trt1.hfsframework.base.IBaseViewCadastro#init()
	 */
	@PostConstruct
	public void init() {
		updateDataTableList();
	}

	private void carregarAdmPerfis() {
		List<AdmProfile> listaAdmProfileSelecionado = this.getBean().getId() == null ? new ArrayList<AdmProfile>()
				: this.getService().findProfilesByPage(this.getBean());
		this.listaAdmProfile = admProfileService.findAll();
		this.listaAdmProfile.removeAll(listaAdmProfileSelecionado);
		this.dualListAdmProfile = new DualListModel<AdmProfile>(this.listaAdmProfile, listaAdmProfileSelecionado);
	}

	@Override
	public String onInsert() {
		this.listaAdmProfile = admProfileService.findAll();
	    this.dualListAdmProfile = new DualListModel<AdmProfile>(this.listaAdmProfile, new ArrayList<AdmProfile>());
		return super.onInsert(new AdmPage());
	}

	@Override
	public String onEdit(AdmPage entity) {
		String retorno = super.onEdit(entity);
		if (entity != null) {
			carregarAdmPerfis();
		}
		return retorno;
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
