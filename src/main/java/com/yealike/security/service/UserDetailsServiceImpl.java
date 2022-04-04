package com.yealike.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("用户已经登录。。。");
        // 1.查询数据库判断用户名是否存在，如果不存在就会抛出UsernameNotFoundException
        // 我们模拟查询数据库的过程，假设用户名就为  admin
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 2.把查询出来的密码(注册时就已经加密后存入数据库的)进行解析，或者直接把密码放入构造方法
        String password = pw.encode("123");


        // 赋予当前用户admin,normal权限
        return new User(username,password,
        AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin," +
                        "normal," +
                        "ROLE_abc,/main.html,/insert,/delete"));
    }
}
