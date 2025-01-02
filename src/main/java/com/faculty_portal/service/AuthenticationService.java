package com.faculty_portal.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faculty_portal.dto.LoginEmployeeDto;
import com.faculty_portal.dto.RegisterEmployeeDto;
import com.faculty_portal.dto.VerifyEmployeeDto;
import com.faculty_portal.entity.Admin;
import com.faculty_portal.entity.Department;
import com.faculty_portal.entity.Employee;
import com.faculty_portal.repository.AdminRepository;
import com.faculty_portal.repository.DepartmentRepository;
import com.faculty_portal.repository.EmployeeRepository;

import jakarta.mail.MessagingException;

@Service
public class AuthenticationService {
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final EmailService emailService;

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	public AuthenticationService(EmployeeRepository employeeRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	public Employee signup(RegisterEmployeeDto input) {
		Employee employee = new Employee(input.getUsername(), input.getEmail(),
				passwordEncoder.encode(input.getPassword()));
		employee.setVerificationCode(generateVerificationCode());
		employee.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
		employee.setEnabled(false);

		Admin admin = adminRepository.findById(1L) // Replace 1L with an existing Admin ID
				.orElseThrow(() -> new RuntimeException("Admin not found"));
		employee.setAdmin(admin);
		sendVerificationEmail(employee);
		
		Department department = departmentRepository.findById(1L) // Replace 1L with an actual Department ID
		        .orElseThrow(() -> new RuntimeException("Department not found"));
		    employee.setDepartment(department);
		return employeeRepository.save(employee);
	}

	public Employee authenticate(LoginEmployeeDto input) {
		Employee employee = employeeRepository.findByEmail(input.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!employee.isEnabled()) {
			throw new RuntimeException("Account not verified. Please verify your account.");
		}
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return employee;
	}

	public void verifyEmployee(VerifyEmployeeDto input) {
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail(input.getEmail());
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			if (employee.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
				throw new RuntimeException("Verification code has expired");
			}
			if (employee.getVerificationCode().equals(input.getVerificationCode())) {
				employee.setEnabled(true);
				employee.setVerificationCode(null);
				employee.setVerificationCodeExpiresAt(null);
				employeeRepository.save(employee);
			} else {
				throw new RuntimeException("Invalid verification code");
			}
		} else {
			throw new RuntimeException("User not found");
		}
	}

	public void resendVerificationCode(String email) {
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			if (employee.isEnabled()) {
				throw new RuntimeException("Account is already verified");
			}
			employee.setVerificationCode(generateVerificationCode());
			employee.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
			sendVerificationEmail(employee);
			employeeRepository.save(employee);
		} else {
			throw new RuntimeException("Employee not found");
		}
	}

	private void sendVerificationEmail(Employee employee) { // TODO: Update with company logo
		String subject = "Account Verification";
		String verificationCode = "VERIFICATION CODE " + employee.getVerificationCode();
		String htmlMessage = "<html>" + "<body style=\"font-family: Arial, sans-serif;\">"
				+ "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
				+ "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
				+ "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
				+ "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
				+ "<h3 style=\"color: #333;\">Verification Code:</h3>"
				+ "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
				+ "</div>" + "</div>" + "</body>" + "</html>";

		try {
			emailService.sendVerificationEmail(employee.getEmail(), subject, htmlMessage);
		} catch (MessagingException e) {
			// Handle email sending exception
			e.printStackTrace();
		}
	}

	private String generateVerificationCode() {
		Random random = new Random();
		int code = random.nextInt(900000) + 100000;
		return String.valueOf(code);
	}
}