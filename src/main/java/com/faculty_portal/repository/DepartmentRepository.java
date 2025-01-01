package com.faculty_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculty_portal.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
