package com.faculty_portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculty_portal.entity.Attendance;
import com.faculty_portal.entity.Employee;
import com.faculty_portal.exception.ResourceNotFoundException;
import com.faculty_portal.repository.AttendanceRepository;
import com.faculty_portal.repository.EmployeeRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Fetch attendance by employee ID
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + employeeId + " not found."));
        return attendanceRepository.findByEmployee(employee);
    }

    // Mark attendance
    public Attendance markAttendance(Attendance attendance) {
        Long employeeId = attendance.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + employeeId + " not found."));

        attendance.setEmployee(employee);
        return attendanceRepository.save(attendance);
    }

    // Optional: Update attendance
    // public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
    //     Attendance existingAttendance = attendanceRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Attendance record with ID " + id + " not found."));

    //     existingAttendance.setDate(updatedAttendance.getDate());
    //     existingAttendance.setStatus(updatedAttendance.getStatus());
    //     existingAttendance.setCheckIn(updatedAttendance.getCheckIn());
    //     existingAttendance.setCheckOut(updatedAttendance.getCheckOut());

    //     return attendanceRepository.save(existingAttendance);
    // }

    // Optional: Delete attendance
    // public void deleteAttendance(Long id) {
    //     if (attendanceRepository.existsById(id)) {
    //         attendanceRepository.deleteById(id);
    //     } else {
    //         throw new ResourceNotFoundException("Attendance record with ID " + id + " not found.");
    //     }
    // }
}
