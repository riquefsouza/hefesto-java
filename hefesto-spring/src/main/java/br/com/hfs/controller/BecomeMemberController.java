package br.com.hfs.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.util.exceptions.TransactionException;

@Controller
public class BecomeMemberController { 
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AdmUserService admUserService;
	
	@Autowired
	private AdmProfileService admProfileService;
	
	@GetMapping("/public/becomeMember")
	@ResponseBody
	public String signUp(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "new", required = true) String newPassword,
			@RequestParam(name = "confirm", required = true) String confirmNewPassword) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pwd = passwordEncoder.encode(confirmNewPassword);
		
		AdmUser user = new AdmUser(username, pwd);
		user.setEmail(email);
		user.setCurrentPassword(pwd);
		user.setNewPassword(newPassword);
		user.setConfirmNewPassword(confirmNewPassword);

		try {
			AdmUser user1 = admUserService.insert(user);
			
			Set<AdmUser> users = new HashSet<AdmUser>();
			users.add(user1);

			Optional<AdmProfile> profile = admProfileService.findById(2L);
			profile.get().setAdmUsers(users);
			admProfileService.update(profile.get());
			
			return messageSource.getMessage("dlgBecomeMember.okSave", null, LocaleContextHolder.getLocale());
			
		} catch (TransactionException e) {
			return messageSource.getMessage("dlgBecomeMember.noSave", null, LocaleContextHolder.getLocale());
		}

	}
}
