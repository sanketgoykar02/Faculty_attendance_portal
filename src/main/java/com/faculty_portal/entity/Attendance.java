package com.faculty_portal.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private String status;
	private LocalTime checkIn;
	private LocalTime checkOut;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	@JsonManagedReference
	private Employee employee;

	public Attendance() {
		
	}

	public Attendance(Long id, LocalDate date, String status, LocalTime checkIn, LocalTime checkOut,
			Employee employee) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = checkOut;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}