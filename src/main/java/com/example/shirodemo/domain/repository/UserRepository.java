package com.example.shirodemo.domain.repository;

import com.example.shirodemo.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "t_user")
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}