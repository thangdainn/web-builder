package org.dainn.searchservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.searchservice.document.User;
import org.dainn.searchservice.exception.AppException;
import org.dainn.searchservice.exception.ErrorCode;
import org.dainn.searchservice.repository.IUserRepository;
import org.dainn.searchservice.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Transactional
    @Override
    public void create(User user) {
        log.info("Creating user: {}", user.getId());
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void syncPermission(User request) {
        User user = findById(request.getId());
        user.setPermissions(request.getPermissions());
        userRepository.save(user);
        log.info("Sync permissions for user: {}", user.getId());
    }

    @Transactional
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
        log.info("Sync agency for user: {}", request.getId());
    }

    @Transactional
    @Override
    public void sync(List<User> users) {
        userRepository.deleteAll();
        userRepository.saveAll(users);
        log.info("Sync users successfully");
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

    @Transactional
    @Override
    public void delete(String id) {
        log.info("Deleting user: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getSubAccountTeamMembers(String subAccountId) {
        return userRepository.findByAgencySubAccountsIdAndRoleAndPermissionsSubAccountIdAndPermissionsAccess(
                subAccountId, "SUBACCOUNT_USER", subAccountId, true);
    }

    @Override
    public List<User> getTeamMembers(String agencyId) {
        return userRepository.findByAgencyId(agencyId);
    }
}
