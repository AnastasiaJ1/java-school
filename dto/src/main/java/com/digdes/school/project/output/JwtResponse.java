package com.digdes.school.project.output;

import java.util.List;
import java.util.UUID;

public class JwtResponse {
    private String token;
    private UUID id;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken,  String username, List<String> roles, UUID id) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        this.id = id;
    }

    public UUID getID() {
        return id;
    }

    public void setID(UUID ID) {
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
