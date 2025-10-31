package com.company.tasks.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.company.tasks.department.Department;
import com.company.tasks.task.Task;
import com.company.tasks.task.TaskDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class EmployeeService {

	public List<EmployeeDTO> listAll() {
		return Employee.<Employee>listAll().stream()
				.map(EmployeeDTO::fromEntity)
				.collect(Collectors.toList());
	}

	public Optional<EmployeeDTO> findById(Long id) {
		return Employee.<Employee>findByIdOptional(id)
				.map(EmployeeDTO::fromEntity);
	}

	@Transactional
	public EmployeeDTO create(EmployeeDTO dto) {
		// Duplicate email check
		if (dto.getEmail() != null && Employee.find("email", dto.getEmail()).firstResult() != null) {
			throw new WebApplicationException("Email already exists", 409);
		}
		Employee e = dto.toEntity();
		
		if (dto.getDepartment() != null) {
			Department department = Department.find("name", dto.getDepartment()).firstResult();
			if (department == null) {
				throw new WebApplicationException("Department not found: " + dto.getDepartment(), 400);
			}
			e.setDepartment(department);
		}

		e.persist();
		if (!e.isPersistent()) {
			throw new WebApplicationException("Could not create employee", 400);
		}
		return EmployeeDTO.fromEntity(e);
	}

	@Transactional
	public EmployeeDTO update(Long id, EmployeeDTO dto) {
		Optional<Employee> opt = Employee.findByIdOptional(id);
		if (!opt.isPresent()) {
			throw new WebApplicationException("Employee not found", 404);
		}
		Employee e = opt.get();

		if (dto.getEmail() != null && !dto.getEmail().equals(e.getEmail())) {
			// Check duplicate
			if (Employee.find("email", dto.getEmail()).firstResult() != null) {
				throw new WebApplicationException("Email already exists", 409);
			}
			e.setEmail(dto.getEmail());
		}

		if (dto.getName() != null) e.setName(dto.getName());
		if (dto.getDepartment() != null) {
			Department department = Department.find("name", dto.getDepartment()).firstResult();
			if (department == null) {
				throw new WebApplicationException("Department not found: " + dto.getDepartment(), 400);
			}
			e.setDepartment(department);
		}
		if (dto.getPosition() != null) e.setPosition(dto.getPosition());
		if (dto.getJoindate() != null) e.setJoindate(dto.getJoindate());

		e.persist();
		return EmployeeDTO.fromEntity(e);
	}

	@Transactional
	public boolean delete(Long id) {
		Employee employee = Employee.findById(id);
		if (employee == null) {
			return false;
		}
		
		long activeTasks = Task.count("assignedTo = ?1 and status not in (?2, ?3)", 
			employee, Task.TaskStatus.DONE, Task.TaskStatus.CANCELLED);
		
		if (activeTasks > 0) {
			throw new WebApplicationException(
				"Cannot delete employee with " + activeTasks + " active task(s). Please complete or cancel tasks first.", 
				400
			);
		}
		
		return Employee.deleteById(id);
	}

	public List<TaskDTO> listEmployeeTasks(Long employeeId) {
		Employee employee = Employee.<Employee>findByIdOptional(employeeId)
				.orElseThrow(() -> new WebApplicationException("Employee not found", 404));
		
		return Task.<Task>find("assignedTo", employee).list().stream()
				.map(TaskDTO::fromEntity)
				.collect(Collectors.toList());
	}

	public List<EmployeeDTO> getEmployeeDepartment(String departmentName) {
		Department department = Department.find("name", departmentName).firstResult();
		if (department == null) {
			throw new WebApplicationException("Department not found: " + departmentName, 404);
		}
		return Employee.<Employee>find("department", department).list().stream()
				.map(EmployeeDTO::fromEntity)
				.collect(Collectors.toList());
	}
}
