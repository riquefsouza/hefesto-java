package br.com.hfs.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.admin.vo.UserVO;
import br.com.hfs.base.BaseViewController;
import br.com.hfs.security.HfsUserDetails;

@Controller
@RequestMapping("/private/admin/changePasswordEditView")
@SessionAttributes("listMenu")
public class ChangePasswordController extends BaseViewController {

	private String listPage;
	
	private UserVO userLogged;
	
	@Autowired
	private AdmUserService admUserService;
	
	public ChangePasswordController() {
		this.listPage = "private/admin/changePassword";
	}

	@GetMapping
	public ModelAndView list() {
		Optional<ModelAndView> mv = getPage(this.listPage);
		
		if (mv.isPresent()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			HfsUserDetails userDetails = (HfsUserDetails)authentication.getPrincipal();

			userLogged = userDetails.getAuthenticatedUser().getUser();
			mv.get().addObject("userVO", userLogged);
		}
		
		return mv.get();
	}

	public boolean prepararParaSalvar(UserVO user, ModelAndView mv) {
		if ((user.getNewPassword() == null && user.getConfirmNewPassword() == null && user.getCurrentPassword() == null)
				|| (user.getNewPassword().equals("") && user.getConfirmNewPassword().equals("") && user.getCurrentPassword().equals(""))) {

			this.showWarningMessage(mv, "changePasswordView.validation");
			
		} else if ((user.getNewPassword() == null && user.getConfirmNewPassword() == null)
				|| (user.getNewPassword().equals("") && user.getConfirmNewPassword().equals(""))) {

			this.showWarningMessage(mv, "changePasswordView.validation");
			
		} else {

			if (BCrypt.checkpw(user.getCurrentPassword(), user.getPassword())) {

				if (user.getNewPassword().equals(user.getConfirmNewPassword())) {
					return true;
				} else {
					this.showWarningMessage(mv, "changePasswordView.notEqual");					
				}
			} else {
				this.showWarningMessage(mv, "changePasswordView.currentPwdNotEqual");
			}
		}
		return false;
	}
	
	@PostMapping
	public ModelAndView save(@Valid UserVO user, 
			BindingResult result, RedirectAttributes attributes) {
		Optional<ModelAndView> mv = getPage(this.listPage);
		mv.get().addObject("userVO", user);

		if (result.hasErrors()){
			//this.showWarningMessage(mv.get(), "changePasswordView.checkFields");
			//logBindingResultErrors(result, log);
			return mv.get();
		}

		if (!prepararParaSalvar(user, mv.get())){
			return mv.get();
		}
		
		try {
			String pwdCrypt = BCrypt.hashpw(user.getConfirmNewPassword(), BCrypt.gensalt());
			
			userLogged.setPassword(pwdCrypt);
			
			AdmUser admUser = new AdmUser(userLogged);
			
			admUserService.update(admUser);
			
			this.showWarningMessage(mv.get(), "changePasswordView.passwordChanged");
		} catch (RestClientException e) {
			this.showDangerMessage(mv.get(), e);
			return mv.get();
		}
		
		return mv.get();
	}
	
	
}
