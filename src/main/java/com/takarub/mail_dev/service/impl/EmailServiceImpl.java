package com.takarub.mail_dev.service.impl;

import com.takarub.mail_dev.Utils.EmailUtils;
import com.takarub.mail_dev.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

import static com.takarub.mail_dev.Utils.EmailUtils.getEmailMessage;
import static com.takarub.mail_dev.Utils.EmailUtils.getVericationUrl;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "new User Account Verification";
    public static final String UTF_8 = "UTF-8";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;



    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
                message.setTo(to);
                message.setText(getEmailMessage(name,host,token));
                javaMailSender.send(message);

            }catch (Exception exception){
                System.out.println(exception.getMessage());
                throw new RuntimeException(exception.getMessage());
            }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message =getMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8);

            helper.setPriority(1);
            helper.setFrom(fromEmail);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setTo(to);
            helper.setText(getEmailMessage(name,host,token));

            javaMailSender.send(message);

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }



    @Override
    @Async

    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {
        try {
            Context context = new Context();
//            context.setVariable("name",name);
//            context.setVariable("url",getVerificationUrl(host,token));

            context.setVariables(Map.of("name",name,"url",getVericationUrl(host,token)));

           String text = templateEngine.process("emailtemplate",context);
            MimeMessage message =getMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8);

            helper.setPriority(1);
            helper.setFrom(fromEmail);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setTo(to);
            helper.setText(text ,true);

            javaMailSender.send(message);

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }


    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }
    private MimeMessage getMimeMessage() {
        return javaMailSender.createMimeMessage();
    }
}
