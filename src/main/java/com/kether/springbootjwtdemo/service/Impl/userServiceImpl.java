package com.kether.springbootjwtdemo.service.Impl;

import com.kether.springbootjwtdemo.dao.userDao;
import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.user;
import com.kether.springbootjwtdemo.service.userService;
import com.kether.springbootjwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userDao userDao;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer adduser(userRequest userRequest) {
        user checkExistingUser = userDao.getUserByUsername(userRequest.getUser());
        if (checkExistingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        String hashPassword = PasswordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(hashPassword);

        return userDao.adduser(userRequest);
    }

    @Override
    public user getUserById(Integer userId) {
        user user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }


    @Override
    public List<user> getAllUser() {
        List<user> userList = userDao.getAllUser();
        return userList;
    }

    @Override
    public void deleteUserById(Integer userId) {
        userDao.deleteUserById(userId);
    }

    @Override
    public String login(String username, String password) {
        user user = userDao.getUserByUsername(username);
        if (user != null && PasswordEncoder.matches(password, user.getPassword())) {
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUsername());
            return token;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}
