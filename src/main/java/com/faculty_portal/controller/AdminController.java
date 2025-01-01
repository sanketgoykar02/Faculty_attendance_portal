package com.faculty_portal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculty_portal.entity.Admin;
import com.faculty_portal.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping
	public Admin createAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}

	@GetMapping("/{id}")
	public Optional<Admin> getAdminById(@PathVariable Long id) {
		return Optional.ofNullable(adminService.getAdminById(id));
	}

	@PutMapping("/{id}")
	public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
		return adminService.updateAdmin(id, updatedAdmin);
	}

	@DeleteMapping("/{id}")
	public String deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return "Admin deleted successfully.";
	}
}
