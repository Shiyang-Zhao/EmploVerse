package com.emploverse.backend.controller.employee;

import com.emploverse.backend.dto.employee.AttendanceDTO;
import com.emploverse.backend.service.employee.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        Optional<AttendanceDTO> attendanceDTO = attendanceService.getAttendanceById(id);
        return attendanceDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @PostMapping("/create")
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO savedAttendance = attendanceService.saveAttendance(attendanceDTO);
        return ResponseEntity.ok(savedAttendance);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id,
            @RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceDTO updatedAttendance = attendanceService.updateAttendance(id, attendanceDTO);
            return ResponseEntity.ok(updatedAttendance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
