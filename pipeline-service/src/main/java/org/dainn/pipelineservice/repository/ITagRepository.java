package org.dainn.pipelineservice.repository;

import org.dainn.pipelineservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITagRepository extends JpaRepository<Tag, String> {
    List<Tag> findAllBySubAccountId(String subAccountId);
}
