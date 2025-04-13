package org.dainn.searchservice.repository;

import org.dainn.searchservice.document.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends ElasticsearchRepository<User, String> {
}
