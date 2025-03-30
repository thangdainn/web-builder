package org.dainn.subaccountservice.repository;

import org.dainn.subaccountservice.model.SubAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubAccountRepository extends JpaRepository<SubAccount, String> {
}
