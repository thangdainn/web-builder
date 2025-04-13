package org.dainn.searchservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.searchservice.document.User;
import org.dainn.searchservice.dto.user.UserAccessConsumer;
import org.dainn.searchservice.dto.user.UserInfoConsumer;
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
    public void update(User user) {
        log.info("Updating user: {}", user.getId());
        userRepository.save(user);
    }

    @Override
    public void updateInfo(UserInfoConsumer dto) {
        User user = findById(dto.getId());
        user.setAgencyId(dto.getAgencyId());
        List<User.Permission> permissions = dto.getPermissions().stream()
                .map(permission -> User.Permission.builder()
                        .id(permission.getId())
                        .subAccountId(permission.getSubAccountId())
                        .access(permission.getAccess())
                        .build())
                .toList();
        user.getPermissions().addAll(permissions);

        List<User.SubAccount> subAccounts = dto.getSubAccounts().stream()
                .map(subAccount -> User.SubAccount.builder()
                        .id(subAccount.getId())
                        .agencyId(subAccount.getAgencyId())
                        .build())
                .toList();
        user.getSubAccounts().addAll(subAccounts);

        userRepository.save(user);
    }

    @Override
    public void updateAccess(UserAccessConsumer dto) {
        User user = findById(dto.getUserId());

        User.Permission permissionToUpdate = user.getPermissions().stream()
                .filter(p -> p.getId().equals(dto.getPermissionId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionToUpdate.setAccess(dto.getAccess());

        userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id)
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
