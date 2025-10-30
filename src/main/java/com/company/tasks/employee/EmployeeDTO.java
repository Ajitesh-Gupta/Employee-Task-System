package com.company.tasks.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {

	private Long id;

	@NotBlank
	@Size(min = 2, max = 100)
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String department;

	private String position;

	private LocalDate joindate;

	// Getters / Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public LocalDate getJoindate() {
		return joindate;
	}
	public void setJoindate(LocalDate joindate) {
		this.joindate = joindate;
	}

	// Mapping helpers
    //Used when fetching data from the database
	public static EmployeeDTO fromEntity(Employee e) {
		if (e == null) return null;
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(e.getId());
		dto.setName(e.getName());
		dto.setEmail(e.getEmail());
		dto.setDepartment(e.getDepartment() != null ? e.getDepartment().getName() : null);
		dto.setPosition(e.getPosition());
		dto.setJoindate(e.getJoindate());
		return dto;
	}

    //Used when creating or updating data into the database
	public Employee toEntity() {
		Employee e = new Employee();
		e.setName(this.name);
		e.setEmail(this.email);
		// Department will be set by the service layer
		e.setPosition(this.position);
		e.setJoindate(this.joindate);
		return e;
	}
}
