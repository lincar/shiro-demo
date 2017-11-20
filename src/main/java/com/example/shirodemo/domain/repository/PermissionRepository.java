package com.example.shirodemo.domain.repository;

import com.example.shirodemo.domain.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "t_permission")
@Qualifier("permissionRepository")
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}