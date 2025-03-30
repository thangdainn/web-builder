package org.dainn.subaccountservice.repository;

import org.dainn.subaccountservice.model.SubAccountSidebarOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubAccountSORepository extends JpaRepository<SubAccountSidebarOption, String> {
}
