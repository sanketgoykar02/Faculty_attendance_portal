package com.faculty_portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculty_portal.entity.Employee;
import com.faculty_portal.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
      return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employee));
  }
  
  @GetMapping("/me")
  public ResponseEntity<Employee> authenticatedUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      Employee currentUser = (Employee) authentication.getPrincipal();
//      return ResponseEntity.ok(currentUser);
      Object principal = authentication.getPrincipal();
      if (!(principal instanceof Employee)) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }
      Employee currentUser = (Employee) principal;
      return ResponseEntity.ok(currentUser);

  }

  @GetMapping("/")
  public ResponseEntity<List<Employee>> allEmployee() {
      List <Employee> employee = employeeService.allEmployee();
      return ResponseEntity.ok(employee);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
	  return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
      employeeService.deleteEmployee(id);
      return ResponseEntity.ok("Employee deleted successfully");
  }


} 
