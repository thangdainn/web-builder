package org.dainn.userservice.service;


import org.dainn.userservice.dto.mail.MailData;

public interface IMailService {
    void sendEmail(MailData mailData);
}
