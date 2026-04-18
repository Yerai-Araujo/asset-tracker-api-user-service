package com.at.asset_tracker.user.domain.events;

public record UserCreatedEvent(Long id, String email, String name) {
}