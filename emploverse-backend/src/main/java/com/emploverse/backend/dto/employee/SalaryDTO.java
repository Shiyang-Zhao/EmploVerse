package com.emploverse.backend.dto.employee;

import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class SalaryDTO {
    @Setter(lombok.AccessLevel.NONE)
    private Long id;
    private Long employeeId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String remarks;
    private Map<String, BigDecimal> customAttributes;
}
