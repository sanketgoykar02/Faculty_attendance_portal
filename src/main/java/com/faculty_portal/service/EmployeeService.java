package com.faculty_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faculty_portal.entity.Employee;
import com.faculty_portal.exception.ResourceNotFoundException;
import com.faculty_portal.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save an employee
    public Employee saveEmployee(Employee employee) {
        // Encode the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    // Retrieve all employees
    public List<Employee> allEmployee() {
        return employeeRepository.findAll();
    }

    // Update an employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setUsername(updatedEmployee.getUsername());
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setPhone(updatedEmployee.getPhone());
            employee.setVerificationCode(updatedEmployee.getVerificationCode());
            employee.setVerificationCodeExpiresAt(updatedEmployee.getVerificationCodeExpiresAt());
            employee.setEnable(updatedEmployee.isEnable());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setAdmin(updatedEmployee.getAdmin());

            // Update password if provided
            if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                employee.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
            }

            return employeeRepository.save(employee);
        } else {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found.");
        }
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found.");
        }
    }
//    
//    public Employee saveEmployee(Employee employee) {
//		return employeeRepository.save(employee);
//		
//	}
//
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
    
//    public EmployeeService(EmployeeRepository employeeRepository, EmailService emailService) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    public List<Employee> allEmployee() {
//        List<Employee> employees = new ArrayList<>();
//        employeeRepository.findAll().forEach(employees::add);
//        return employees;
//    }
//    public Optional<Employee> getEmployeeById(Long id) {
//        return employeeRepository.findById(id);
//    }
//
//    public Employee updateEmployee(Long id,Employee employee) {
//        return employeeRepository.save(employee);
//    }
//
//    public void deleteEmployee(Long id) {
//        employeeRepository.deleteById(id);
//    }
	
	
	
}
