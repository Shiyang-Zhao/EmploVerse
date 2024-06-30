package com.emploverse.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.emploverse.backend.model.user.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String username;
    private String email;
    private Set<RoleName> roles;
    private boolean enabled;
    private Long employeeId;
}
