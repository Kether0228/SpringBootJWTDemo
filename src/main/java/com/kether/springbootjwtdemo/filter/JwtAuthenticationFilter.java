package com.kether.springbootjwtdemo.filter;

import com.kether.springbootjwtdemo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("進入 JwtAuthenticationFilter 的 doFilterInternal 方法");
        //從request header 中獲取Authorization
        final String authorizationHeader = request.getHeader("Authorization");

        // 檢查Authorization頭是否存在並且是以"Bearer "開頭
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 提取JWT令牌（去掉"Bearer "前綴）
        String token = authorizationHeader.substring(7);

        // 驗證JWT令牌
        if (jwtUtil.validateToken(token)) {
            // 從JWT中獲取使用者
            String username = jwtUtil.getUsername(token);

            // 如果上下文中尚未設置認證信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 創建認證令牌
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 設置認證詳情
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 將認證設置到SecurityContext中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        // 繼續過濾器鏈的處理
        filterChain.doFilter(request, response);
    }
}