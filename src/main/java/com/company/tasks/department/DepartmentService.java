package com.company.tasks.department;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.company.tasks.employee.Employee;
import com.company.tasks.task.Task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class DepartmentService {

    @Transactional
    public DepartmentDTO create(DepartmentDTO dto) {
        Department d = dto.toEntity();
        d.persist();
        if (!d.isPersistent()) {
            throw new WebApplicationException("Could not create department", 400);
        }
        return DepartmentDTO.fromEntity(d);
    }

    public List<DepartmentDTO> listAll() {
        return Department.<Department>listAll().stream()
            .map(DepartmentDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> findById(Long id) {
        return Department.<Department>findByIdOptional(id)
            .map(DepartmentDTO::fromEntity);
    }

    public List<Employee> listEmployeesByDepartment(Long id) {
        Department department = Department.<Department>findByIdOptional(id)
            .orElseThrow(() -> new WebApplicationException("Department not found", 404));
        return Employee.list("department", department);
    }

    public List<Task> listTasksByDepartment(Long id) {
        Department department = Department.<Department>findByIdOptional(id)
            .orElseThrow(() -> new WebApplicationException("Department not found", 404));
        return Task.list("assignedTo.department", department);
    }
}
