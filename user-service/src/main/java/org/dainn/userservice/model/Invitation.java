package org.dainn.userservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.dainn.userservice.util.enums.Role;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invitations")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String agencyId;
}
