package com.example.foodOrder.dto;

import com.example.foodOrder.enums.Role;

public class AuthResponse{
    private String jwt;

    private Long id;
    private Role role;

    public AuthResponse(){

    }

    public AuthResponse(String jwt, Long id, Role role) {
        this.jwt = jwt;
        this.id = id;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
