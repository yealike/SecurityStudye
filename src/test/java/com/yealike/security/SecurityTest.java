package com.yealike.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {
    @Test
    public void passwordEncoder() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encode = pe.encode("123");
        System.out.println("加密后的密码===>" + encode);

        boolean matches = pe.matches("123", encode);
        System.out.println("原始密码与加密后是否相同===>"+matches);
    }
}
