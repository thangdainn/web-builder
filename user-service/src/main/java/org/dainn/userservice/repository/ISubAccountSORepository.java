package org.dainn.userservice.repository;

import org.dainn.userservice.model.SubAccountSidebarOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubAccountSORepository extends JpaRepository<SubAccountSidebarOption, String> {
    List<SubAccountSidebarOption> findAllBySubAccountId(String subAccountId);
}
