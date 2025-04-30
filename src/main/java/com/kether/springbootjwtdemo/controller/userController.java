package com.kether.springbootjwtdemo.controller;

import com.kether.springbootjwtdemo.dto.LoginRequest;
import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.user;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.kether.springbootjwtdemo.service.userService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class userController {
     @Autowired
     private userService userService;

     @GetMapping("/test")
     public ResponseEntity<Integer> test() {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(1);
     }


     @PostMapping("/register")
     public ResponseEntity<Integer> registerUser(@Valid @RequestBody userRequest userRequest) {
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(userService.adduser(userRequest));
     }

     @GetMapping("/search/{userId}")
     public ResponseEntity<user> getUserById(@PathVariable Integer userId) {
        user user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<user>> getAllUser() {
        List<user> userList = userService.getAllUser();
        if (userList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userList);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getUser(), loginRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


}
