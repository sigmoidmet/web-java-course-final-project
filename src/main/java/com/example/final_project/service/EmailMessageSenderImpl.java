package com.example.final_project.service;

import com.example.final_project.pojo.EmailMessage;
import com.example.final_project.exception.EmailSenderException;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailMessageSenderImpl implements EmailMessageSender {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        Context context = buildContext(emailMessage);
        String html = templateEngine.process(emailMessage.getTemplate().getTemplateName(), context);
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailMessage.getRecipient());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(html, true);

            emailSender.send(message);
        } catch (Exception e) {
            throw new EmailSenderException(e.getMessage(), e);
        }
    }

    private Context buildContext(EmailMessage emailMessage) {
        Context context = new Context();

        for (var param : emailMessage.getParams().entrySet()) {
            context.setVariable(param.getKey(), param.getValue());
        }
        return context;
    }
}
