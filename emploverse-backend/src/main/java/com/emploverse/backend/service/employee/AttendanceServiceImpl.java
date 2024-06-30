package com.emploverse.backend.service.employee;

import com.emploverse.backend.dto.employee.AttendanceDTO;
import com.emploverse.backend.model.employee.Attendance;
import com.emploverse.backend.model.employee.AttendanceStatus;
import com.emploverse.backend.repository.employee.AttendanceRepository;
import com.emploverse.backend.util.mapper.employee.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> getAttendanceById(Long id) {
        return attendanceRepository.findById(id).map(attendanceMapper::toDto);
    }

    @Override
    @Transactional
    public AttendanceDTO saveAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance.setStatus(AttendanceStatus.valueOf(attendanceDTO.getStatus()));
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDto(savedAttendance);
    }

    @Override
    @Transactional
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        return attendanceRepository.findById(id).map(attendance -> {
            attendance.setDate(attendanceDTO.getDate());
            attendance.setCheckInTime(attendanceDTO.getCheckInTime());
            attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
            attendance.setStatus(AttendanceStatus.valueOf(attendanceDTO.getStatus()));
            attendance.setRemarks(attendanceDTO.getRemarks());
            Attendance updatedAttendance = attendanceRepository.save(attendance);
            return attendanceMapper.toDto(updatedAttendance);
        }).orElseThrow(() -> new IllegalArgumentException("Attendance not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
