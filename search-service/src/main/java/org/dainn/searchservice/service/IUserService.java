package org.dainn.searchservice.service;

import org.dainn.searchservice.document.User;

import java.util.List;

public interface IUserService {
    void create(User user);
    void syncPermission(User user);
    void syncAgency(User user);
    void sync(List<User> users);

    User findById(String id);
    User findByEmail(String email);
    void delete(String id);
    List<User> getSubAccountTeamMembers(String subAccountId);
    List<User> getTeamMembers(String agencyId);

}
