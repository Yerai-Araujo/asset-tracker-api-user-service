package com.at.asset_tracker.user.domain.repository;

import java.util.Optional;

import com.at.asset_tracker.user.domain.model.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    void deleteById(Long id);
}
