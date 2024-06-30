package com.emploverse.backend.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
