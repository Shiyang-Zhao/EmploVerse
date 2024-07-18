package com.emploverse.backend.controller;

import com.emploverse.backend.dto.EmployeeDTO;
import com.emploverse.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getEmployeesBySortPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<EmployeeDTO> employees = employeeService.findEmployeesBySortPage(page, size, sortBy, sortDir);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/all/get")
    public ResponseEntity<List<EmployeeDTO>> getAllUsers() {
        List<EmployeeDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findEmployeeById(id);
        return employeeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(
            @PathVariable Long id,
            @RequestBody EmployeeDTO updatedEmployeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(id, updatedEmployeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
}
