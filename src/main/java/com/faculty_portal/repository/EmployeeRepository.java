package com.faculty_portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculty_portal.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByVerificationCode(String verificationCode);

	boolean existsByEmail(String email);

}
