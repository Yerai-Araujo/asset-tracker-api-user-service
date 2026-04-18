package com.at.asset_tracker.user.domain.events;

public record UserUpdatedEvent (Long id, String email, String name) {
    
}
