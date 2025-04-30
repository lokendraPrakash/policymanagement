package com.policymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.policymanagement.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}