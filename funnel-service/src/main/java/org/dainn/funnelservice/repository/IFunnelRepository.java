package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFunnelRepository extends JpaRepository<Funnel, String> {
}
