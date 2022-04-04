package com.yealike.security.config;

import com.yealike.security.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义登录参数
//                .usernameParameter("hello")
//                .passwordParameter("world")
                // 当发现/login时认为是登录，必须和表单提交的地址一样，去执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                // 自定义登录页面
//                .loginPage("/login.html")
                .loginPage("/showLogin")
                // 登录之后跳转的页面，必须是post请求
                .successForwardUrl("/toMain")
                // 登录成功后处理器，不能和successForwardUrl共存
//                .successHandler(new MyAuthenticationSuccessHandler("main.html"))
                .failureForwardUrl("/toError");
                // 登录失败的跳转页面
//                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        // 授权认证
        http.authorizeRequests()
                // error.html不需要被认证
                .antMatchers("/error.html").access("permitAll()")
//                .antMatchers("/error.html").permitAll()
                // login.html不需要被认证
//                .antMatchers("/login.html").permitAll()
                .antMatchers("/showLogin").access("permitAll()")
//                .antMatchers("/main1.html").hasAuthority("Admin")
//                .antMatchers("/main1.html").hasAnyAuthority("Admin","normal")
//                .antMatchers("/main1.html").hasRole("abc")
//                .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                // 所有请求都必须被认证，必须登录之后才能被访问
//                    .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");
                .anyRequest().authenticated();

        // 关闭csrf防火墙
//        http.csrf().disable();

        // 异常处理
//        http.exceptionHandling()
//                .accessDeniedHandler(myAccessDeniedHandler);

        //记住我
        http.rememberMe()
                // 默认失效时间
                .tokenValiditySeconds(60)
                //自定义登录逻辑
                .userDetailsService(userDetailsService)
                // 持久层对象
                .tokenRepository(persistentTokenRepository);

        // 退出登录
        http.logout()
                // 退出登录跳转路径
                .logoutSuccessUrl("/login.html");
    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动建表，第一次启动的时候需要建表，第二次启动的时候需要注释掉
        // jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;

    }
}
