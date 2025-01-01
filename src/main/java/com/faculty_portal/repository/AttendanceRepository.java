package com.faculty_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculty_portal.entity.Attendance;
import com.faculty_portal.entity.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

	List<Attendance> findByEmployee(Employee employee);

}
