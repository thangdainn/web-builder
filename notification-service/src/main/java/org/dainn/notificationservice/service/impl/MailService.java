package org.dainn.notificationservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.dainn.notificationservice.dto.mail.MailData;
import org.dainn.notificationservice.service.IMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(MailData mailData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mailData.getTo());
            helper.setSubject(mailData.getSubject());

            Context context = new Context();
            context.setVariable("inviterName", mailData.getInviterName());
            context.setVariable("invitationLink", mailData.getInvitationLink());

            String htmlContent = templateEngine.process("invitation-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new  RuntimeException("Failed to send email", e);
        }

    }
}
