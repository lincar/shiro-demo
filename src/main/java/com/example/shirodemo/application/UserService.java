package com.example.shirodemo.application;

import com.alibaba.druid.util.StringUtils;
import com.example.shirodemo.domain.Permission;
import com.example.shirodemo.domain.Role;
import com.example.shirodemo.domain.User;
import com.example.shirodemo.domain.repository.PermissionRepository;
import com.example.shirodemo.domain.repository.RoleRepository;
import com.example.shirodemo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public void initData() {
        Permission p1 = new Permission();
        p1.setPermission("file:read");
        permissionRepository.save(p1);
        Permission p2 = new Permission();
        p2.setPermission("file:write");
        permissionRepository.save(p2);
        Permission p3 = new Permission();
        p3.setPermission("file:exe");
        permissionRepository.save(p3);

        Role admin = new Role();
        admin.setRole("admin");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(p1);
        permissions.add(p2);
        permissions.add(p3);
        admin.setPermissions(permissions);
        roleRepository.save(admin);

        Role guest = new Role();
        guest.setRole("guest");
        permissions = new ArrayList<>();
        permissions.add(p1);
        guest.setPermissions(permissions);
        roleRepository.save(guest);

        User lin = new User("lin", "123");
        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        lin.setRoles(roles);
        userRepository.save(lin);

        User chen = new User("chen", "123");
        roles = new ArrayList<>();
        roles.add(guest);
        chen.setRoles(roles);
        userRepository.save(chen);
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User getUser(String name) {
        return userRepository.findByUsername(name);
    }

    public Set<String> getRoles(User user) {
        if (user.getRoles() == null) {
            return null;
        }
        Set<String> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRole());
        }
        return roles;
    }

    public Set<String> getPermissions(User user) {
        if (user.getRoles() == null) {
            return null;
        }
        Set<String> permissions = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getPermissions() == null) {
                continue;
            }
            for (Permission permission : role.getPermissions()) {
                if (StringUtils.isEmpty(permission.getPermission())) {
                    continue;
                }
                if (permissions.contains(permission.getPermission())) {
                    continue;
                }
                permissions.add(permission.getPermission());
            }
        }
        return permissions;
    }

}
