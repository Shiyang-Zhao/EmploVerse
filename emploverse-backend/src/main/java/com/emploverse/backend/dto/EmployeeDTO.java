package com.emploverse.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String firstName;
    private String lastName;
    private String profileImagePath;

    @Setter(lombok.AccessLevel.NONE)
    private Long userId;
}