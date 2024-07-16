package com.emploverse.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.emploverse.backend.model.RoleName;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {
    private String username;
    private String email;
    private boolean enabled;
    private Set<RoleName> roles;
}
