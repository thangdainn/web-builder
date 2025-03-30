package org.dainn.notificationservice.repository;

import org.dainn.notificationservice.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface INotificationRepository extends R2dbcRepository<Notification, String> {
    Flux<Notification> findByUserIdAndAgencyId(String userId, String agencyId, Pageable pageable);
    Flux<Notification> findBySubAccountIdAndAgencyId(String subAccountId, String agencyId, Pageable pageable);
}
