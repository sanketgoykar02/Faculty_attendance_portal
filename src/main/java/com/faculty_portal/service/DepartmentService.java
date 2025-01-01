package com.faculty_portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculty_portal.entity.Department;
import com.faculty_portal.exception.ResourceNotFoundException;
import com.faculty_portal.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Save a department
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Get department by ID
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found."));
    }

    // Update a department
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(id);
        existingDepartment.setName(updatedDepartment.getName());
        return departmentRepository.save(existingDepartment);
    }

    // Delete a department
    public void deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Department with ID " + id + " not found.");
        }
    }
}
