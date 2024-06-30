package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.AttendanceDTO;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    List<AttendanceDTO> getAllAttendances();

    Optional<AttendanceDTO> getAttendanceById(Long id);

    AttendanceDTO saveAttendance(AttendanceDTO attendanceDTO);

    AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO);

    void deleteAttendance(Long id);
}
