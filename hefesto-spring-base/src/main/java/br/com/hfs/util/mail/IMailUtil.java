package br.com.hfs.util.mail;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;


public interface IMailUtil {

	public void sendSimpleMessage(String to, String subject, String text) throws MailException;

	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MailException, MessagingException;

}
