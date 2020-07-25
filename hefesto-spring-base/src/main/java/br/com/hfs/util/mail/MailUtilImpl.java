package br.com.hfs.util.mail;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.properties")
public class MailUtilImpl implements IMailUtil {

	@Autowired
	private Environment env;

	public void sendSimpleMessage(String to, String subject, String text) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		getJavaMailSender().send(message);
	}

	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MailException, MessagingException {
		MimeMessage message = getJavaMailSender().createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment("Invoice", file);

		getJavaMailSender().send(message);
	}

	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getRequiredProperty("mail.host"));		
		mailSender.setPort(Integer.valueOf(env.getRequiredProperty("mail.port")));

		mailSender.setUsername(env.getRequiredProperty("mail.user"));
		mailSender.setPassword(env.getRequiredProperty("mail.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", env.getRequiredProperty("mail.tls"));
		props.put("mail.debug", "true");
		
		return mailSender;
	}

}
