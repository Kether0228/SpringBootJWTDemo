package com.kether.springbootjwtdemo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class user implements UserDetails  {
    private Integer id;
    private String username;
    private String password;
    private List<role> roles;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // 檢查 roles 是否為 null，避免 NullPointerException
    if (roles == null) {
        return List.of();
    }
    // 將 userRole 轉換為 GrantedAuthority
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole()))
            .collect(Collectors.toList());
}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
