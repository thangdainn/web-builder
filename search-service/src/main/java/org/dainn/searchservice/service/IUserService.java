package org.dainn.searchservice.service;

import org.dainn.searchservice.document.User;
import org.dainn.searchservice.dto.user.UserAccessConsumer;
import org.dainn.searchservice.dto.user.UserInfoConsumer;

import java.util.List;

public interface IUserService {
    void create(User user);
    void update(User user);
    void updateInfo(UserInfoConsumer dto);
    void updateAccess(UserAccessConsumer dto);
    User findById(String id);
    void delete(String id);

    List<User> getSubAccountTeamMembers(String subAccountId);

}
