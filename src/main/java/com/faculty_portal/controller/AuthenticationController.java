package com.faculty_portal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faculty_portal.dto.LoginEmployeeDto;
import com.faculty_portal.dto.RegisterEmployeeDto;
import com.faculty_portal.dto.VerifyEmployeeDto;
import com.faculty_portal.entity.Employee;
import com.faculty_portal.exception.EmployeeNotFoundException;
import com.faculty_portal.exception.InvalidVerificationCodeException;
import com.faculty_portal.response.LoginResponse;
import com.faculty_portal.service.AuthenticationService;
import com.faculty_portal.service.JwtService;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
  private final JwtService jwtService;

  private final AuthenticationService authenticationService;

  public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
      this.jwtService = jwtService;
      this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<Employee> register(@RequestBody RegisterEmployeeDto registerEmployeeDto) {
      Employee registeredEmployee = authenticationService.signup(registerEmployeeDto);
      return ResponseEntity.ok(registeredEmployee);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginEmployeeDto loginUserDto){
      Employee authenticatedEmployee = authenticationService.authenticate(loginUserDto);
      String jwtToken = jwtService.generateToken(authenticatedEmployee);
      LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
      return ResponseEntity.ok(loginResponse);
  }

//  @PostMapping("/verify")
//  public ResponseEntity<?> verifyEmployee(@RequestBody VerifyEmployeeDto verifyEmployeeDto) {
//      try {
//          authenticationService.verifyEmployee(verifyEmployeeDto);
//          return ResponseEntity.ok("Account verified successfully");
//      } catch (RuntimeException e) {
//          return ResponseEntity.badRequest().body(e.getMessage());
//      }
//  }
  @PostMapping("/verify")
  public ResponseEntity<?> verifyUser(@RequestBody VerifyEmployeeDto verifyEmployeeDto) {
      try {
          authenticationService.verifyEmployee(verifyEmployeeDto);
          return ResponseEntity.ok("Account verified successfully");
      } catch (EmployeeNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } catch (InvalidVerificationCodeException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
  }


  @PostMapping("/resend")
  public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
      try {
          authenticationService.resendVerificationCode(email);
          return ResponseEntity.ok("Verification code sent");
      } catch (RuntimeException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }
}