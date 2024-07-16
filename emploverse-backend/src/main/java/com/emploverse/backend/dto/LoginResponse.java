package com.emploverse.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.Set;

import com.emploverse.backend.model.RoleName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String jwt;
    private String username;
    private String email;
    private boolean enabled;
    private Date lastLogin;
    private Long employeeId;
    private Set<RoleName> roles;
}
