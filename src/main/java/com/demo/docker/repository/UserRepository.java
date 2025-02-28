package com.demo.docker.repository;

import com.demo.docker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    /**
     * Find user by email
     *
     * @param email user email
     * @return user entity
     */
    Optional<UserEntity> findByEmail(String email);
}
