package com.kether.springbootjwtdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kether.springbootjwtdemo.dto.LoginRequest;
import com.kether.springbootjwtdemo.dto.userRequest;
import com.kether.springbootjwtdemo.model.user;
import com.kether.springbootjwtdemo.service.userService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class userControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private userService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("registerUser should return 201 when user is successfully registered")
    void registerUserReturns201() throws Exception {
        userRequest request = new userRequest();
        request.setUser("newUser");
        request.setPassword("newPassword");

        Mockito.when(userService.adduser(any(userRequest.class))).thenReturn(1);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("register User should return 400 when request body is invalid")
    void registerUserReturns400ForInvalidRequest() throws Exception {
        userRequest request = new userRequest(); // Empty request

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getUserById should return 200 with user details when user exists")
    void getUserByIdReturns200() throws Exception {
        user mockUser = new user();
        mockUser.setId(1);
        mockUser.setUsername("existingUser");
        mockUser.setPassword("password");

        Mockito.when(userService.getUserById(eq(1))).thenReturn(mockUser);

        mockMvc.perform(get("/api/user/search/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("existingUser"));
    }


    @Test
    @DisplayName("getAllUser should return 200 with user list when users exist")
    void getAllUserReturns200() throws Exception {
        user mockUser = new user();
        mockUser.setId(1);
        mockUser.setUsername("existingUser");
        mockUser.setPassword("password");

        Mockito.when(userService.getAllUser()).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/api/user/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("existingUser"));
    }

    @Test
    @DisplayName("deleteUserById should return 204 when user is successfully deleted")
    void deleteUserByIdReturns204() throws Exception {
        Mockito.doNothing().when(userService).deleteUserById(eq(1));

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("login should return 200 with user details when credentials are valid")
    void loginReturns200() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser("validUser");
        loginRequest.setPassword("validPassword");

        user mockUser = new user();
        mockUser.setId(1);
        mockUser.setUsername("validUser");
        mockUser.setPassword("validPassword");

        Mockito.when(userService.login(eq("validUser"), eq("validPassword"))).thenReturn(mockUser);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("validUser"));
    }

}