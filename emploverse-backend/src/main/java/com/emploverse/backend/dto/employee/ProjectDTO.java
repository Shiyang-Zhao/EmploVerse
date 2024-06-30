package com.emploverse.backend.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private Long managerId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<TaskDTO> tasks;
}
