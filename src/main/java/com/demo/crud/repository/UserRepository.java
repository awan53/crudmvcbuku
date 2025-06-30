// src/main/java/com/demo/crud/repository/UserRepository.java
package com.demo.crud.repository;

import com.demo.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}