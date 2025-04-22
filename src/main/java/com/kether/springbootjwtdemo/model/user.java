package com.kether.springbootjwtdemo.model;

import lombok.Data;

import java.util.List;

@Data
public class user {
    private Integer id;
    private String username;
    private String password;
    private List<role> roles;
}
