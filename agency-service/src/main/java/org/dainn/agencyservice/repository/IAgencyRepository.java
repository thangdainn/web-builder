package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgencyRepository extends JpaRepository<Agency, String> {
}
