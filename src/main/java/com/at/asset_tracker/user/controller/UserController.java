package com.at.asset_tracker.user.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.at.asset_tracker.user.application.dto.request.CreateUserRequest;
import com.at.asset_tracker.user.application.dto.response.UserResponse;
import com.at.asset_tracker.user.application.service.UserApplicationService;
import com.at.asset_tracker.user.domain.exception.ResourceNotFoundException;
import com.at.asset_tracker.user.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userService;

    public UserController(UserApplicationService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) throws JsonProcessingException {

        User user = userService.create(
                request.email(),
                request.name());

        return ResponseEntity
                .created(URI.create("/api/users/" + user.id()))
                .body(toResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        User user = userService.findById(id);

        return ResponseEntity.ok(toResponse(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {

        User user = userService.findByEmail(email);

        return ResponseEntity.ok(toResponse(user));
    }

    @GetMapping("/validate/user/{id}")
    public ResponseEntity<UserResponse> validateUser(@PathVariable Long id) {

        return ResponseEntity.ok(toResponse(userService.findById(id)));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.id(),
                user.name(),
                user.email(),
                user.portfolioId());
    }
}
