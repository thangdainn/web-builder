package org.dainn.userservice.dto.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailData {
    private String inviteId;
    private String from;
    private String to;
    private String subject;
    private String inviterName;
    private String invitationLink;
}
