package com.at.asset_tracker.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "Name cannot be empty") String name,

        @Email(message = "Email cannot be empty") String email) {
}
