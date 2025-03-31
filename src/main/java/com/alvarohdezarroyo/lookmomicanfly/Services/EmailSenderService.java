package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailSenderService {

    @Autowired
    private final EmailTemplateService emailTemplateService;
    private final JavaMailSender javaMailSender;

    public EmailSenderService(EmailTemplateService emailTemplateService, JavaMailSender javaMailSender) {
        this.emailTemplateService = emailTemplateService;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithNoAttachment(EmailDetailsDTO emailDetailsDTO) {
        try {
            final MimeMessage mimeMessage= generateEmail(emailDetailsDTO,null);
            sendEmail(mimeMessage);
        }
        catch (MessagingException | MailException e){
            System.out.println("There was an issue when sending the email.");
        }
        catch (RuntimeException ex){
            System.out.println("Server error.");
        }
    }

    public void sendEmailWithAttachment(EmailDetailsDTO emailDetailsDTO, String filePath){
        try{
            final File file=new File(filePath);
            final MimeMessage mimeMessage= generateEmail(emailDetailsDTO,file);
            sendEmail(mimeMessage);
        }
        catch(NullPointerException | MessagingException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void sendEmail(MimeMessage mimeMessage){
        try {
            javaMailSender.send(mimeMessage);
        }
        catch (MailException e){
            System.out.println("There was an issue when sending the email.");
        }
        catch (RuntimeException ex){
            System.out.println("Server error.");
        }
    }

    private MimeMessage generateEmail(EmailDetailsDTO emailDetailsDTO, File file) throws MessagingException {
        final MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        String content= emailTemplateService.addParamsToHTMLTemplate(emailDetailsDTO.getTemplate(),emailDetailsDTO.getContent());
        mimeMessageHelper.setTo(emailDetailsDTO.getRecipient());
        mimeMessageHelper.setFrom(emailDetailsDTO.getSender());
        mimeMessageHelper.setSubject(emailDetailsDTO.getSubject());
        mimeMessageHelper.setText(content,true);
        if(file!=null)
            mimeMessageHelper.addAttachment(file.getName(),file);
        return mimeMessage;
    }

}
