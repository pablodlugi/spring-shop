package com.pablito.shop.service.impl;

import com.pablito.shop.service.MailService;
import com.pablito.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateService templateService;
    private final ITemplateEngine iTemplateEngine;

    @Override
    @Async
    public void send(String email, String templateName, Map<String, Object> variables, String fileName, byte[] file) {
        var template = templateService.getTemplateByName(templateName);
        var context = new Context(Locale.getDefault(), variables);
        var mailBody = iTemplateEngine.process(template.getBody(), context);
        javaMailSender.send(mimeMessage -> {
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(mailBody, true);
            if (fileName != null && file != null) {
                mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(file));
            }
        });
    }

}
