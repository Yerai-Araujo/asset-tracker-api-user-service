package com.at.asset_tracker.user.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.at.asset_tracker.user.infrastructure.persistence.entity.OutboxEvent;

public interface OutboxJpaRepository
        extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop100ByPublishedFalse();
}