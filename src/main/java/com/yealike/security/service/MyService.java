package com.yealike.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface MyService {
    /**
     * 判断当前用户是否有访问某些资源的权限
     *
     * @param request        获得主体和对应的权限
     * @param authentication 权限
     * @return 是否可以访问资源
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
