package com.faculty_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculty_portal.entity.Department;
import com.faculty_portal.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public List<Department> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@PostMapping("/save")
	public Department createDepartment(@RequestBody Department department) {
		return departmentService.saveDepartment(department);
	}

//	@GetMapping("/{id}")
//	public Department getDepartmentById(@PathVariable Long id) {
//		return departmentService.getDepartmentById(id);
//	}

	@PutMapping("/{id}")
	public Department updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
		return departmentService.updateDepartment(id, updatedDepartment);
	}

	@DeleteMapping("/{id}")
	public String deleteDepartment(@PathVariable Long id) {
		departmentService.deleteDepartment(id);
		return "Department deleted successfully.";
	}
}