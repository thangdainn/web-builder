package org.dainn.subaccountservice.repository;

import org.dainn.subaccountservice.model.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubAccountRepository extends JpaRepository<SubAccount, String> {
    Page<SubAccount> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
