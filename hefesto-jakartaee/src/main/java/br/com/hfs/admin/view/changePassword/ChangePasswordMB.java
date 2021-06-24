package br.com.hfs.admin.view.changePassword;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.base.BaseViewRegister;
import br.com.hfs.base.IBaseViewRegister;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
@HandlingExpectedErrors
public class ChangePasswordMB extends
		BaseViewRegister<AdmUser, Long, AdmUserService>
		implements IBaseViewRegister<AdmUser> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public ChangePasswordMB() {
		super(AdmUser.class,
				"admin/changePassword/listChangePassword", 
				"admin/changePassword/editChangePassword");
	}

	@PostConstruct
	public void init() {
		AdmUser user = new AdmUser(getAuthenticatedUser().getUser());
		setBean(user);
	}
	
	@Override
	public String onInsert() {
		return "";
	}

	public boolean validateFields(AdmUser bean) {
		if ((bean.getNewPassword() == null && bean.getConfirmNewPassword() == null && bean.getCurrentPassword() == null)
				|| (bean.getNewPassword().equals("") && bean.getConfirmNewPassword().equals("") && bean.getCurrentPassword().equals(""))) {

			generateErrorMessage("Please fill in all fields!");
			
		} else if ((bean.getNewPassword() == null && bean.getConfirmNewPassword() == null)
				|| (bean.getNewPassword().equals("") && bean.getConfirmNewPassword().equals(""))) {

			generateErrorMessage("Please fill in all fields!");
			
		} else {
			if (bean.getNewPassword().equals(bean.getConfirmNewPassword())) {
				return true;
			} else {
				generateErrorMessage("New password and confirm password do not match!");					
			}
		}
		return false;
	}
	
	@Override
	public String save() {
		if (validateFields(getBean())) {
			//UserVO autenticado = getAuthenticatedUser().getUser();

			if (!getService().validarSenha(getBean().getEmail(), getBean().getCurrentPassword())) {
				generateErrorMessage("Current password does not meet security criteria.");
				return null;
			}
			
			if (!getService().validarSenha(getBean().getEmail(), getBean().getNewPassword())) {
				generateErrorMessage("The new password does not meet the security criteria.");
				return null;
			}
			
			//String psenha = SHAHashUtil.get_SHA_512_SecurePassword(getBean().getNewPassword(), SHAHashUtil.SALT);
						
		
			if (getService().updatePassword(getBean())){
				generateInformativeMessage("Password changed successfully!");
				
				//if (getAuthenticatedUser().getPrimeiroAcesso()) {
					//logOut();
				//}
			}
		}
		
		//return super.save(getBean().getId(), getBean().getLogin());
		return "";
	}

	@Override
	public void delete(AdmUser entity) {
		//
	}

	@Override
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		//
	}

	@Override
	public AdmUser getBean() {
		return super.getEntity();
	}

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
