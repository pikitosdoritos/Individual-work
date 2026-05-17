package com.testing_system.dto;

import com.testing_system.model.Role;

public class AuthRequest {
    private String username;
    private String password;
    private Role role; // only used for registration

    public AuthRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
