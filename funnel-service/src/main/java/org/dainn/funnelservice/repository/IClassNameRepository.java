package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.ClassName;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassNameRepository extends R2dbcRepository<ClassName, String> {
}
