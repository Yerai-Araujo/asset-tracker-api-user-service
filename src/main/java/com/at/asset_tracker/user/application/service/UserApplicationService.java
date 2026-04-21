package com.at.asset_tracker.user.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.at.asset_tracker.user.domain.events.UserCreatedEvent;
import com.at.asset_tracker.user.domain.events.UserDeletedEvent;
import com.at.asset_tracker.user.domain.events.UserUpdatedEvent;
import com.at.asset_tracker.user.domain.exception.ConflictException;
import com.at.asset_tracker.user.domain.exception.ResourceNotFoundException;
import com.at.asset_tracker.user.domain.model.User;
import com.at.asset_tracker.user.domain.repository.OutboxRepository;
import com.at.asset_tracker.user.domain.repository.UserRepository;
import com.at.asset_tracker.user.infrastructure.persistence.entity.OutboxEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public User create(String email, String name) {

        if (existsByEmail(email)) {
            throw new ConflictException("User with email " + email + " already exists");
        }

        User user = User.create(email, name);

        User savedUser = userRepository.save(user);

        UserCreatedEvent event = new UserCreatedEvent(savedUser.id(), savedUser.email(), savedUser.name());
        JsonNode payload = objectMapper.valueToTree(event);

        OutboxEvent outboxEvent = OutboxEvent.userCreated(savedUser.id(), payload);
        outboxRepository.save(outboxEvent);
        
        return savedUser;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public User update(Long id, String email, String name) {

        User user = findById(id);

        user.setName(name);
        user.setEmail(email);

        userRepository.save(user);

        UserUpdatedEvent event = new UserUpdatedEvent(user.id(), user.email(), user.name());
        JsonNode payload = objectMapper.valueToTree(event);

        OutboxEvent outboxEvent = OutboxEvent.userUpdated(user.id(), payload);
        outboxRepository.save(outboxEvent);

        return user;
    }

    public void delete(Long id) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        userRepository.deleteById(id);

        UserDeletedEvent event = new UserDeletedEvent(id);
        JsonNode payload = objectMapper.valueToTree(event);

        OutboxEvent outboxEvent = OutboxEvent.userDeleted(id, payload);
        outboxRepository.save(outboxEvent);
    }

}
