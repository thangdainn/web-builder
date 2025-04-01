package org.dainn.subaccountservice.repository;

import org.dainn.subaccountservice.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IContactRepository extends JpaRepository<Contact, String> {
    Optional<Contact> findByEmailAndSubAccountId(String email, String subAccountId);

    Page<Contact> findBySubAccountIdAndEmailContainingIgnoreCase(String subAccountId, String email, Pageable pageable);

    Page<Contact> findBySubAccountId(String subAccountId, Pageable pageable);
}
