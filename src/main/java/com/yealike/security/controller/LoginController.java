package com.yealike.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        System.out.println("执行了登录方法！");
        return "redirect:main.html";
    }

//    @Secured("ROLE_abc")
    @PreAuthorize("hasRole('abc')")
    @RequestMapping("/toMain")
    public String toMain(){
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String toError(){
        return "redirect:error.html";
    }

    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }

    @RequestMapping("/showLogin")
    public String showLogin(){
        return "login";
    }

}
