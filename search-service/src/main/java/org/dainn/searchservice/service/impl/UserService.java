package org.dainn.searchservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.searchservice.document.User;
import org.dainn.searchservice.exception.AppException;
import org.dainn.searchservice.exception.ErrorCode;
import org.dainn.searchservice.repository.IUserRepository;
import org.dainn.searchservice.service.IUserService;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void create(User user) {
        log.info("Creating user: {}", user.getId());
        userRepository.save(user);
    }

    @Override
    public void syncPermission(User request) {
        User user = findById(request.getId());
        user.setPermissions(request.getPermissions());
        userRepository.save(user);
    }

    @Override
    public void syncAgency(User request) {
        Optional<User> optional = userRepository.findById(request.getId());
        if (optional.isEmpty()) {
            userRepository.save(request);
        }
        List<User> users = userRepository.findByAgencyId(request.getAgency().getId());
        for (User user : users) {
            user.setAgency(request.getAgency());
        }
        userRepository.saveAll(users);
    }

    @Override
    public User findById(String id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public void delete(String id) {
        log.info("Deleting user: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getSubAccountTeamMembers(String subAccountId) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                // Điều kiện 1: role = SUBACCOUNT_USER
                                .must(m -> m
                                        .term(t -> t
                                                .field("role")
                                                .value("SUBACCOUNT_USER")))
                                // Điều kiện 2: Có subaccountId trong subAccounts
                                .must(m -> m
                                        .nested(n -> n
                                                .path("subAccounts")
                                                .query(nq -> nq
                                                        .term(t -> t
                                                                .field("subAccounts.id")
                                                                .value(subAccountId)))))
                                // Điều kiện 3: Có permission với subAccountId và access = true
                                .must(m -> m
                                        .nested(n -> n
                                                .path("permissions")
                                                .query(nq -> nq
                                                        .bool(nb -> nb
                                                                .must(nm -> nm
                                                                        .term(t -> t
                                                                                .field("permissions.subAccountId")
                                                                                .value(subAccountId)))
                                                                .must(nm -> nm
                                                                        .term(t -> t
                                                                                .field("permissions.access")
                                                                                .value(true)))))))))
                .build();

        SearchHits<User> searchHits = elasticsearchTemplate.search(query, User.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }


}
