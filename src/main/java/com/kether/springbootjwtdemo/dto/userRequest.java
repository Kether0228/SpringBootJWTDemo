package com.kether.springbootjwtdemo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class userRequest {
    @NotBlank
    private String user;
    @NotBlank
    private String password;
}
