package org.dainn.searchservice.repository;

import org.dainn.searchservice.document.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends ElasticsearchRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByAgencyId(String agencyId);
}
