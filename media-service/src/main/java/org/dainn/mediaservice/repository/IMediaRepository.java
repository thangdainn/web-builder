package org.dainn.mediaservice.repository;

import org.dainn.mediaservice.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaRepository extends JpaRepository<Media, String> {
    Page<Media> findBySubAccountId(String subAccountId, Pageable pageable);
    Page<Media> findByNameContainingIgnoreCaseAndSubAccountId(String name, String subAccountId, Pageable pageable);
}
