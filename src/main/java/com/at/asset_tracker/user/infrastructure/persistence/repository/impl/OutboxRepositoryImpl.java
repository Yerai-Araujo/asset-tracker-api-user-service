package com.at.asset_tracker.user.infrastructure.persistence.repository.impl;

import org.springframework.stereotype.Repository;

import com.at.asset_tracker.user.domain.repository.OutboxRepository;
import com.at.asset_tracker.user.infrastructure.persistence.entity.OutboxEvent;
import com.at.asset_tracker.user.infrastructure.persistence.repository.OutboxJpaRepository;

@Repository
public class OutboxRepositoryImpl implements OutboxRepository{
    
    private final OutboxJpaRepository jpaRepository;
    
    public OutboxRepositoryImpl(OutboxJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public void save(OutboxEvent event) {
        if (event != null) {
            jpaRepository.save(event);
        }
    }
}
