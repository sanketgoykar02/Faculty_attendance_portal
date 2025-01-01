package com.faculty_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculty_portal.entity.Attendance;
import com.faculty_portal.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
  @Autowired
  private AttendanceService attendanceService;

  @GetMapping("/{employeeId}")
  public List<Attendance> getAttendanceByEmployeeId(@PathVariable Long employeeId) {
      return attendanceService.getAttendanceByEmployee(employeeId);
  }

  @PostMapping
  public Attendance saveAttendance(@RequestBody Attendance attendance) {
      return attendanceService.markAttendance(attendance);
  }

//  @PutMapping("/{id}")
//  public Attendance updateAttendance(@PathVariable Long id, @RequestBody Attendance updatedAttendance) {
//      return attendanceService.updateAttendance(id, updatedAttendance);
//  }

//  @DeleteMapping("/{id}")
//  public String deleteAttendance(@PathVariable Long id) {
//      attendanceService.deleteAttendance(id);
//      return "Attendance record deleted successfully.";
//  }
} 