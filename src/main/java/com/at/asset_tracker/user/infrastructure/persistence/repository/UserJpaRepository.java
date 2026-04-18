package com.at.asset_tracker.user.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.at.asset_tracker.user.infrastructure.persistence.entity.UserEntity;

public interface UserJpaRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    void deleteById(Long id);

}
