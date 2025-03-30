package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.AgencySidebarOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgencySORepository extends JpaRepository<AgencySidebarOption, String> {
}
