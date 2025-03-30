package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.ClassName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassNameRepository extends JpaRepository<ClassName, String> {
}
