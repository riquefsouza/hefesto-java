package br.com.hfs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import br.com.hfs.util.mail.IMailUtil;

@Controller
public class ForgotPasswordController { 
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IMailUtil mailUtil;
	
	@Autowired
	private AdmUserService admUserService;
	
	@GetMapping("/public/forgotPassword")
	@ResponseBody
	public String send(@RequestParam(name = "username", required = true) String username) {

		String subject = messageSource.getMessage("login.forgotPassword", null, LocaleContextHolder.getLocale());
		String text = messageSource.getMessage("forgotPassword.textMail", null, LocaleContextHolder.getLocale());

		Optional<AdmUser> user = admUserService.findByName(username);
		
		try {
			if (user.isPresent()) {
				mailUtil.sendSimpleMessage(user.get().getEmail(), subject, text);
			} else {
				user = admUserService.findByEmail(username);

				if (user.isPresent()) {
					mailUtil.sendSimpleMessage(user.get().getEmail(), subject, text);				
				}
			}
			
			return messageSource.getMessage("forgotPassword.sendMail", null, LocaleContextHolder.getLocale());
			
		} catch (MailException e) {
			return messageSource.getMessage("forgotPassword.noSendMail", null, LocaleContextHolder.getLocale());
		}

	}
}
