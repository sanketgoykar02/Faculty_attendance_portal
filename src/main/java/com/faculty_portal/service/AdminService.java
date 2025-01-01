package com.faculty_portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faculty_portal.entity.Admin;
import com.faculty_portal.exception.ResourceNotFoundException;
import com.faculty_portal.repository.AdminRepository;



@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + id + " not found."));
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Admin existingAdmin = getAdminById(id);

        existingAdmin.setUsername(updatedAdmin.getUsername());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        existingAdmin.setRole(updatedAdmin.getRole());

        if (updatedAdmin.getPassword() != null && !updatedAdmin.getPassword().isEmpty()) {
            existingAdmin.setPassword(passwordEncoder.encode(updatedAdmin.getPassword()));
        }

        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found.");
        }
    }
}
