package com.kether.springbootjwtdemo.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String user;
    private String password;
}
