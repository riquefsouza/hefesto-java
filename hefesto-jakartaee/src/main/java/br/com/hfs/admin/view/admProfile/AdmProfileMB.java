package br.com.hfs.admin.view.admProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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
		super(AdmProfile.class, 
				"admin/admProfile/listAdmProfile", 
				"admin/admProfile/editAdmProfile");
	}

	@PostConstruct
	public void init() {
		updateDataTableList();		
		
		if (beanInSession()) {
			onEditMode(getBean());
		}
		
		onInserMode();
	}
	
	private void loadAdmPages(AdmProfile bean) {
		List<AdmPage> listAdmPageSelected = bean.getId() == null ? new ArrayList<AdmPage>()
				: this.getService().findPagesByProfile(bean);
		this.listAdmPage = admPageService.findAll();
		this.listAdmPage.removeAll(listAdmPageSelected);
		this.dualListAdmPage = new DualListModel<AdmPage>(this.listAdmPage, listAdmPageSelected);
	}

	private void loadAdmUsers(AdmProfile bean) {
		List<AdmUser> listAdmUserSelected = bean.getId() == null ? new ArrayList<AdmUser>()
				: this.getService().findUsersByProfile(bean);
		this.listAdmUser = admUserService.findAll();
		this.listAdmUser.removeAll(listAdmUserSelected);
		this.dualListAdmUser = new DualListModel<AdmUser>(this.listAdmUser, listAdmUserSelected);
	}

	@Override
	public String onInsert() {
		return super.onInsert(new AdmProfile());
	}
	
	private void onInserMode() {
		if (getState().isInsertMode()) {
			this.listAdmPage = admPageService.findAll();
		    this.dualListAdmPage = new DualListModel<AdmPage>(this.listAdmPage, new ArrayList<AdmPage>());
	
			this.listAdmUser = admUserService.findAll();
		    this.dualListAdmUser = new DualListModel<AdmUser>(this.listAdmUser, new ArrayList<AdmUser>());
		}
	}
	
	private void onEditMode(AdmProfile bean) {
		if (getState().isEditMode()) {
			loadAdmPages(bean);
			loadAdmUsers(bean);
		}
	}

	@Override
	public String onEdit(AdmProfile entity) {
		return super.onEdit(entity);
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
