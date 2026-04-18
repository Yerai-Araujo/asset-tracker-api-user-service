package com.at.asset_tracker.user.domain.model;

import lombok.Setter;

@Setter
public class User {
    
    private Long id;

    private String email;

    private String name;

    private Long portfolioId;


    public User(Long id, String email, String name, Long portfolioId) {

        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        this.id = id;
        this.email = email;
        this.name = name;
        this.portfolioId = portfolioId;
    }

    public Long id() { return id; }
    public String email() { return email; }
    public String name() { return name; }
    public Long portfolioId() { return portfolioId; }

    public static User create(String email, String name) {
        return new User(null, email, name, null);   
    }

}
