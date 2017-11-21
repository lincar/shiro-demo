package com.example.shirodemo.controller;

import com.example.shirodemo.application.UserService;
import com.example.shirodemo.domain.User;
import com.example.shirodemo.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "/init")
    public String initData() {
        userService.initData();
        return "init data";
    }

    @RequestMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping
    public User getUser(@RequestParam String name) {
        return userService.getUser(name);
    }

    @RequestMapping("/login")
    public Result testLogin(@RequestParam String user, @RequestParam String password) {
        login(user, password);
        return Result.success(SecurityUtils.getSubject().getPrincipal());
    }

    protected void login(String username, String password) {

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        subject.login(token);
    }

    @RequestMapping("/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            System.out.println(subject.getPrincipal() + " logout");
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            System.out.println(subject.getPrincipal() + " logout");
        }
    }


    @RequestMapping(value = "/role")
    @RequiresRoles("admin")
    public Result testRole() {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return Result.success(SecurityUtils.getSubject().getPrincipal());
    }

    private Result isAdmin() {
        return Result.success();
    }
}
