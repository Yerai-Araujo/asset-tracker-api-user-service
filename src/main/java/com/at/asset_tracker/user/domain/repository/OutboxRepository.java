package com.at.asset_tracker.user.domain.repository;

import com.at.asset_tracker.user.infrastructure.persistence.entity.OutboxEvent;

public interface OutboxRepository {

    void save(OutboxEvent event);
}
