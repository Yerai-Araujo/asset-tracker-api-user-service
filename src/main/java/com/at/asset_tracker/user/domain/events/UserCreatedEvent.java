package com.at.asset_tracker.user.domain.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedEvent {

    private Long id;
    private String email;

}