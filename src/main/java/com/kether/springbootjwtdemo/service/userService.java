package com.kether.springbootjwtdemo.service;

import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.user;

import java.util.List;

public interface userService {
    Integer adduser(userRequest userRequest);
    user getUserById(Integer id);
    List<user> getAllUser();
    void deleteUserById(Integer userId);
    user login(String username, String password);

//    void addRole(Integer userId, Integer roleId);
//    void deleteUser(Integer id);
//    void updateUser(Integer id, String username, String password, String role);
//    void updateRole(Integer id, Integer roleId);
//    void deleteRole(Integer id, Integer roleId);
}
