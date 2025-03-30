package org.dainn.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String id;

    @NotNull(message = "Content is required")
    @NotBlank(message = "Agency ID is required")
    private String notification;

    @NotNull(message = "Agency Id is required")
    @NotBlank(message = "Agency ID is required")
    private String agencyId;

    @NotNull(message = "SubAccount Id is required")
    @NotBlank(message = "SubAccount ID is required")
    private String subAccountId;

    @NotNull(message = "User Id is required")
    @NotBlank(message = "User ID is required")
    private String userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
