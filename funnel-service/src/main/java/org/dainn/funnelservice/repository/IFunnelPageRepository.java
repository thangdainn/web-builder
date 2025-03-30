package org.dainn.funnelservice.repository;

import org.dainn.funnelservice.model.FunnelPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFunnelPageRepository extends JpaRepository<FunnelPage, String> {
}
