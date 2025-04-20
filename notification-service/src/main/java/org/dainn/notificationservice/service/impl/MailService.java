package org.dainn.notificationservice.service.impl;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.notificationservice.dto.mail.MailData;
import org.dainn.notificationservice.event.EventProducer;
import org.dainn.notificationservice.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final SpringTemplateEngine templateEngine;
    private final EventProducer eventProducer;

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${resend.api.mail}")
    private String resendMail;

    @Override
    public void sendEmail(MailData mailData) {
        try {
            Context context = new Context();
            context.setVariable("inviterName", mailData.getInviterName());
            context.setVariable("invitationLink", mailData.getInvitationLink());
            String htmlContent = templateEngine.process("invitation-email", context);

            Resend resend = new Resend(resendApiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Taskium <" + resendMail +">")
                    .to(mailData.getTo())
                    .subject(mailData.getSubject())
                    .html(htmlContent)
                    .build();
            resend.emails().send(params);
            log.info("Email sent from {} to {} successfully", mailData.getFrom(), mailData.getTo());
        }catch (ResendException e) {
            log.error("Failed to send email from {} to {}: {}", mailData.getFrom(), mailData.getTo(), e.getMessage());
            eventProducer.sendEmailFailed(mailData.getInviteId());
        } catch (Exception e) {
            log.error("Failed to send email from {} to {}", mailData.getFrom(), mailData.getTo());
            eventProducer.sendEmailFailed(mailData.getInviteId());
        }
    }
}
