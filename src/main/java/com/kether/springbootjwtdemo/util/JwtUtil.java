package com.kether.springbootjwtdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secretkey}")
    private String secretKey ;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    private Algorithm algorithm ;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }


    /**
     * 根據使用者名稱生成 JWT token 部分
     */
    public String generateToken(String username) {

        return JWT.create()
                //sub - JWT 所面向的用戶
                .withSubject(username)
                //JWT簽發時間
                .withIssuedAt(new Date())
                //JWT的過期時間
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    /**
     * 從 token 中驗證並解析出 DecodedJWT，若驗證失敗則會拋出異常
     */
    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier.verify(token);
    }

    /**
     * 檢查token是否有效外並檢查token是否有過期
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return verifyToken(token).getSubject();
    }
}
