package com.kether.springbootjwtdemo.dao;


import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.user;

import java.util.List;


public interface userDao {
    Integer adduser(userRequest userRequest);
    user getUserById(Integer id);
    List<user> getAllUser();
    void deleteUserById(Integer userId);
    void deleteUserRolesByUserId(Integer userId);
    user getUserByUsername(String username);

//    void setRole(Integer userId, Integer roleId);
}
