package com.at.asset_tracker.user.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.at.asset_tracker.user.domain.events.UserCreatedEvent;
import com.at.asset_tracker.user.domain.model.User;
import com.at.asset_tracker.user.domain.repository.OutboxRepository;
import com.at.asset_tracker.user.domain.repository.UserRepository;
import com.at.asset_tracker.user.infrastructure.persistence.entity.OutboxEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public User create(String email, String name) throws JsonProcessingException {

        if (existsByEmail(email)) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(null, email, name, null);

        User savedUser = userRepository.save(user);

        UserCreatedEvent event = new UserCreatedEvent(savedUser.id(), savedUser.email());

        JsonNode payload;
        try {
            payload = objectMapper.valueToTree(event);
        } catch (Exception e) {
            log.error("Jackson no pudo serializar 'event' ({}): {}",
                    event != null ? event.getClass().getName() : "null",
                    e.getMessage(), e);
            throw new IllegalStateException("No se pudo serializar el evento", e);
        }

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setId(UUID.randomUUID());
        outboxEvent.setAggregateType("User");
        outboxEvent.setAggregateId(savedUser.id().toString());
        outboxEvent.setType("UserCreated");
        outboxEvent.setPayload(payload);

        outboxRepository.save(outboxEvent);

        return savedUser;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
