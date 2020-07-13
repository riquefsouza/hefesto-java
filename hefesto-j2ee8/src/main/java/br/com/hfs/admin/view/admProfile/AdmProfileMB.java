package br.com.hfs.admin.view.admProfile;

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
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmPageService;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class AdmProfileMB extends
		BaseViewRegister<AdmProfile, Long, AdmProfileService>
		implements IBaseViewRegister<AdmProfile> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Inject
	private AdmUserService admUserService;

	private DualListModel<AdmUser> dualListAdmUser;

	private List<AdmUser> listAdmUser;
	
	@Inject
	private AdmPageService admPageService;

	private DualListModel<AdmPage> dualListAdmPage;

	private List<AdmPage> listAdmPage;

	public AdmProfileMB() {
		super("ListAdmProfile", "EditAdmProfile");
	}

	@PostConstruct
	public void init() {
		updateDataTableList();
	}
	
	private void loadAdmPages() {
		List<AdmPage> listAdmPageSelected = this.getBean().getId() == null ? new ArrayList<AdmPage>()
				: this.getService().findPagesByProfile(this.getBean());
		this.listAdmPage = admPageService.findAll();
		this.listAdmPage.removeAll(listAdmPageSelected);
		this.dualListAdmPage = new DualListModel<AdmPage>(this.listAdmPage, listAdmPageSelected);
	}

	private void loadAdmUsers() {
		List<AdmUser> listAdmUserSelected = this.getBean().getId() == null ? new ArrayList<AdmUser>()
				: this.getService().findUsersByProfile(this.getBean());
		this.listAdmUser = admUserService.findAll();
		this.listAdmUser.removeAll(listAdmUserSelected);
		this.dualListAdmUser = new DualListModel<AdmUser>(this.listAdmUser, listAdmUserSelected);
	}

	@Override
	public String onInsert() {
		this.listAdmPage = admPageService.findAll();
	    this.dualListAdmPage = new DualListModel<AdmPage>(this.listAdmPage, new ArrayList<AdmPage>());

		this.listAdmUser = admUserService.findAll();
	    this.dualListAdmUser = new DualListModel<AdmUser>(this.listAdmUser, new ArrayList<AdmUser>());
	    
		return super.onInsert(new AdmProfile());
	}

	@Override
	public String onEdit(AdmProfile entity) {
		String retorno = super.onEdit(entity);
		if (entity != null) {
			loadAdmPages();
			loadAdmUsers();
		}
		return retorno;
	}
	
	@Override
	public String save() {	
		getBean().setAdmPages(new HashSet<AdmPage>(this.dualListAdmPage.getTarget()));
		getBean().setAdmUsers(new HashSet<AdmUser>(this.dualListAdmUser.getTarget()));		
		return super.save(getBean().getId(), getBean().getDescription());
	}

	@Override
	public void delete(AdmProfile entity) {
		super.delete(entity);
	}

	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		super.preProcessPDF(document, "AdmProfile");
	}

	@Override
	public AdmProfile getBean() {
		return super.getEntity();
	}

	@Override
	public void setBean(AdmProfile entity) {
		super.setEntity(entity);
	}

	@Override
	public List<AdmProfile> getListBean() {
		return super.getListEntity();
	}

	@Override
	public void setListBean(List<AdmProfile> listEntity) {
		super.setListEntity(listEntity);		
	}

	public DualListModel<AdmUser> getDualListAdmUser() {
		return dualListAdmUser;
	}
		
	public void setDualListAdmUser(DualListModel<AdmUser> dualListAdmUser) {
		this.dualListAdmUser = dualListAdmUser;
	}
		
	public DualListModel<AdmPage> getDualListAdmPage() {
		return dualListAdmPage;
	}
		
	public void setDualListAdmPage(DualListModel<AdmPage> dualListAdmPage) {
		this.dualListAdmPage = dualListAdmPage;
	}

}
