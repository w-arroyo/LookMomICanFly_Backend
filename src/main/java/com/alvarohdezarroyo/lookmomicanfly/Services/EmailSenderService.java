package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private final EmailTemplateService emailTemplateService;
    private final JavaMailSender javaMailSender;

    public EmailSenderService(EmailTemplateService emailTemplateService, JavaMailSender javaMailSender) {
        this.emailTemplateService = emailTemplateService;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(EmailDetailsDTO emailDetailsDTO) throws MessagingException {
        try {
            final MimeMessage mimeMessage= javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            String content= emailTemplateService.addParamsToHTMLTemplate(emailDetailsDTO.getTemplate(),emailDetailsDTO.getContent());
            mimeMessageHelper.setTo(emailDetailsDTO.getRecipient());
            mimeMessageHelper.setFrom(emailDetailsDTO.getSender());
            mimeMessageHelper.setSubject(emailDetailsDTO.getSubject());
            mimeMessageHelper.setText(content,true);
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException | MailException e){
            System.out.println("There was an issue when sending the email.");
        }
    }

}
