package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.AgencySidebarOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAgencySORepository extends JpaRepository<AgencySidebarOption, String> {
    List<AgencySidebarOption> findAllByAgencyId(String agencyId);
    void deleteAllByAgencyId(String agencyId);
}
