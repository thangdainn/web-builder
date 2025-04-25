package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.userservice.dto.event.UserProducer;
import org.dainn.userservice.dto.permission.PermissionDto;
import org.dainn.userservice.dto.user.UserDetailDto;
import org.dainn.userservice.dto.user.UserDto;
import org.dainn.userservice.dto.user.UserReq;
import org.dainn.userservice.event.EventProducer;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.feignclient.IAgencyClient;
import org.dainn.userservice.feignclient.ISAClient;
import org.dainn.userservice.mapper.IPermissionMapper;
import org.dainn.userservice.mapper.IUserMapper;
import org.dainn.userservice.model.User;
import org.dainn.userservice.repository.IPermissionRepository;
import org.dainn.userservice.repository.IUserRepository;
import org.dainn.userservice.service.IUserService;
import org.dainn.userservice.util.Paging;
import org.dainn.userservice.util.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IPermissionRepository permissionRepository;
    private final IUserMapper userMapper;
    private final IPermissionMapper permissionMapper;
    private final EventProducer eventProducer;
    private final ISAClient saClient;
    private final IAgencyClient agencyClient;

    @Transactional
    @Override
    public UserDto create(UserDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USER_EXISTED);
                });
        User user = userMapper.toEntity(dto);
        User newUser = userRepository.save(user);
        log.info("Created new user: {}", newUser);

        return userMapper.toDto(newUser);
    }

    @Transactional
    @Override
    public void delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (user.getRole() == Role.AGENCY_OWNER) {
            userRepository.deleteById(id);
            eventProducer.sendDeleteUserOwner(user.getAgencyId());
            log.info("Deleted user with ID: {}", id);
            return;
        }
        userRepository.deleteById(id);
        eventProducer.sendDeleteUser(id);
        log.info("Deleted user with ID: {}", id);
    }

    @Override
    public void deleteByAgency(String agencyId) {
        List<User> users = userRepository.findAllByAgencyId(agencyId);
        users.forEach(user -> eventProducer.sendDeleteUser(user.getId()));
        userRepository.deleteAll(users);
        log.info("Deleted all users by agency ID: {}", agencyId);
    }

    @Transactional
    @Override
    public UserDto update(UserDto dto) {
        User old = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        old = userMapper.toUpdate(old, dto);
        old = userRepository.save(old);
        log.info("Updated user: {}", old);
        eventProducer.changePerEvent(old.getId());
        return userMapper.toDto(old);
    }

    @Override
    public UserDetailDto findDetailById(String id) {
        UserDetailDto detail = userMapper.toDetail(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        List<PermissionDto> permissions = permissionRepository.findAllByUserId(id)
                .stream().map(permissionMapper::toDto).toList();
        detail.setPermissions(permissions);
        return detail;
    }

    @Override
    public UserDto findById(String id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public Page<UserDto> findAll(UserReq req) {
        return userRepository.findByFilters(req.getEmail(), req.getRole(), req.getAgencyId(), Paging.getPageable(req))
                .map(userMapper::toDto);
    }

    @Override
    public boolean isOwner(String id) {
        return userRepository.findByIdAndRole(id, Role.AGENCY_OWNER).isPresent();
    }

    @Override
    public UserDto findOwnerByAgency(String id) {
        return userMapper.toDto(userRepository.findByAgencyIdAndRole(id, Role.AGENCY_OWNER)
                .orElseThrow(() -> new AppException(ErrorCode.AGENCY_NOT_EXIST)));
    }

    @Transactional
    @Override
    public void setOwner(UserDto dto) {
        Optional<User> optional = userRepository.findByEmail(dto.getEmail());
        User user;
        if (optional.isPresent()) {
            user = optional.get();
            user = userMapper.toUpdate(user, dto);
        } else {
            user = userMapper.toEntity(dto);
        }
        user.setRole(Role.AGENCY_OWNER);
        user.setAgencyId(dto.getAgencyId());
        log.info("Set owner for user: {}", user);
        userRepository.save(user);
    }

    @Override
    public void syncPermission(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<PermissionDto> permissions = permissionRepository.findAllByUserId(user.getId())
                .stream().map(permissionMapper::toDto).toList();
        List<UserProducer.Permission> permissionList = permissions.stream()
                .map(permission -> {
                    try {
                        ResponseEntity<UserProducer.SubAccount> response = saClient.getById(permission.getSubAccountId());
                        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                            log.warn("SubAccount not found for permission ID: {}", permission.getId());
                            return null;
                        }
                        return UserProducer.Permission.builder()
                                .id(permission.getId())
                                .subAccount(response.getBody())
                                .access(permission.getAccess())
                                .build();
                    } catch (Exception e) {
                        log.error("Error fetching SubAccount for permission ID: {}", permission.getId(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        UserProducer userProducer = UserProducer.toProducerPer(user, permissionList);
        eventProducer.syncPerEvent(userProducer);
        log.info("Sync permission for user successfully: {}", userProducer);
    }

    @Override
    public void syncAgency(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserProducer.Agency agency = agencyClient.getById(user.getAgencyId()).getBody();
        assert agency != null;
        agency.setSubAccounts(saClient.getAllByAgency(agency.getId()).getBody());
        UserProducer userProducer = UserProducer.toProducerAgency(user, agency);
        eventProducer.syncAgencyEvent(userProducer);
        log.info("Sync agency for user successfully: {}", userProducer);
    }

    @Override
    public void syncUser() {
        List<UserProducer> userProducers = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserProducer.Agency agency = agencyClient.getById(user.getAgencyId()).getBody();
            assert agency != null;
            agency.setSubAccounts(saClient.getAllByAgency(agency.getId()).getBody());
            UserProducer userProducer = UserProducer.toProducerAgency(user, agency);
            List<PermissionDto> permissions = permissionRepository.findAllByUserId(user.getId())
                    .stream().map(permissionMapper::toDto).toList();
            List<UserProducer.Permission> permissionList = permissions.stream()
                    .map(permission -> {
                        try {
                            ResponseEntity<UserProducer.SubAccount> response = saClient.getById(permission.getSubAccountId());
                            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                                log.warn("SubAccount not found for permission ID: {}", permission.getId());
                                return null;
                            }
                            return UserProducer.Permission.builder()
                                    .id(permission.getId())
                                    .subAccount(response.getBody())
                                    .access(permission.getAccess())
                                    .build();
                        } catch (Exception e) {
                            log.error("Error fetching SubAccount for permission ID: {}", permission.getId(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            userProducer.setPermissions(permissionList);
            userProducers.add(userProducer);
        }
        eventProducer.syncUserEvent(userProducers);
        log.info("Sync user successfully");
    }

    @Override
    public Boolean isOwnerAgency(UserDto dto) {
        Optional<User> optional = userRepository.findByIdAndAgencyId(dto.getId(), dto.getAgencyId());
        if (optional.isPresent()) {
            return optional.get().getRole() == Role.AGENCY_OWNER;
        } else {
            return false;
        }
    }
}
