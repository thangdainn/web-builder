package org.dainn.agencyservice.repository;

import org.dainn.agencyservice.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateRepository extends JpaRepository<Template, String> {
}
