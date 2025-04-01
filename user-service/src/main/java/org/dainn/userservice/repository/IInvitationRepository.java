package org.dainn.userservice.repository;

import org.dainn.userservice.model.Invitation;
import org.dainn.userservice.util.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInvitationRepository extends JpaRepository<Invitation, String> {
    @Modifying
    @Query("""
                UPDATE Invitation i
                    SET i.status = :status
                    WHERE i.id = :id
            """)
    void updateStatus(String id, InvitationStatus status);

    Optional<Invitation> findByEmail(String email);

    Optional<Invitation> findByEmailAndStatus(String email, InvitationStatus status);

    List<Invitation> findAllByAgencyId(String id);

    @Modifying
    @Query("""
                DELETE FROM Invitation i
                    WHERE i.email = :email
            """)
    void deleteByEmail(String email);
}
