package org.dainn.notificationservice.service;


import org.dainn.notificationservice.dto.mail.MailData;

public interface IMailService {
    void sendEmail(MailData mailData);
}
