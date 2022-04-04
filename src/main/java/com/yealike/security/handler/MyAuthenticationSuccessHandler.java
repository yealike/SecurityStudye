package com.yealike.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("本机ip地址===>"+request.getRemoteAddr());
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String password = user.getPassword();
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        System.out.println("用户名===>"+username);
        System.out.println("密码===>"+password);
        System.out.println("权限===>"+authorities);

        response.sendRedirect(url);
    }
}
