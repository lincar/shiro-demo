package com.example.shirodemo.domain.repository;

import com.example.shirodemo.domain.Role;
import com.example.shirodemo.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "t_role")
@Qualifier("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
}