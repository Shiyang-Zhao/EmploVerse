package com.emploverse.backend.dto.department;

import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
public class DepartmentDTO {
    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private String name;
    private Set<Long> employeeIds;
}
