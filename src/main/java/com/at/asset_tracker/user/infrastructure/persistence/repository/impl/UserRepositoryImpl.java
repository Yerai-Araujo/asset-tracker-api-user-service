package com.at.asset_tracker.user.infrastructure.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.at.asset_tracker.user.domain.model.User;
import com.at.asset_tracker.user.domain.repository.UserRepository;
import com.at.asset_tracker.user.infrastructure.persistence.entity.UserEntity;
import com.at.asset_tracker.user.infrastructure.persistence.repository.UserJpaRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.id());
        entity.setEmail(user.email());
        entity.setName(user.name());

        if (user.portfolioId() != null) {
            entity.setPortfolioId(user.portfolioId());
        }

        return entity;
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getPortfolioId() != null ? entity.getPortfolioId() : null
        );
    }
}
