package com.example.jobportal.dto;

import com.example.jobportal.entity.Role;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String name;

    private String email;

    private Role role;

}