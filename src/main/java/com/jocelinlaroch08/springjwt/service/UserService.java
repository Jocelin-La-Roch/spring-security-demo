package com.jocelinlaroch08.springjwt.service;

import com.jocelinlaroch08.springjwt.domain.AppUser;
import com.jocelinlaroch08.springjwt.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    AppUser getUser(String email);
    List<AppUser> getUsers();
}

