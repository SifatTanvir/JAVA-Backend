package com.dbl.nsl.erp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public void sendEmail(String sendTo, String subject, String message) throws MessagingException, IOException {
        
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
       
        Context context = new Context();
        context.setVariable("message", message);
    
        String html = templateEngine.process("email-body", context);

        helper.setTo(sendTo);
        helper.setText(html, true);
        helper.setSubject(subject);
        // helper.addAttachment("template-cover.png", new ClassPathResource("your_image_file_name.extension")); 
        emailSender.send(mimeMessage);
    }


	public void sendEmailWithAttachment(String sendTo, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
       
        Context context = new Context();
        context.setVariable("message", message);
    
        String html = templateEngine.process("email-body", context);

        helper.setTo(sendTo);
        helper.setText(html, true);
        helper.setSubject(subject);
       
        String filePath = "src/main/resources/static/file/" + attachment;
        FileSystemResource file = new FileSystemResource( ResourceUtils.getFile(filePath) );
        String fileExtension = attachment.substring(attachment.lastIndexOf("."));
        String fileName = "attachment" + fileExtension;
        helper.addAttachment(fileName, file);

        emailSender.send(mimeMessage);
	}
	
	

}
