package com.emploverse.backend.dto.employee;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long departmentId;
    private String position;
    private LocalDate dateOfJoining;
    private LocalDate dateOfBirth;
    private String address;
    private Long managerId;

    @Setter(lombok.AccessLevel.NONE)
    private Long userId;

    private Long salaryId;
    private Set<Long> attendanceIds;
    private Set<Long> projectIds;
    private Set<Long> taskIds;
    private Set<Long> subordinateIds;
}
