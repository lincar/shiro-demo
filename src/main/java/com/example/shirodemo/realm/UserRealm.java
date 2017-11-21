package com.example.shirodemo.realm;

import com.example.shirodemo.application.UserService;
import com.example.shirodemo.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = userService.getUser(username);
        authorizationInfo.setRoles(userService.getRoles(user));
        authorizationInfo.setStringPermissions(userService.getPermissions(user));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException();
        }

        if (user.getLocked()) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                getName()
        );

        return authenticationInfo;
    }
}
