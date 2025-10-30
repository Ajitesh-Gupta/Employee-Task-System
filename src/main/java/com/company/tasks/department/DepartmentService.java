package com.company.tasks.department;

import java.util.List;
import java.util.Optional;

import com.company.tasks.employee.Employee;
import com.company.tasks.task.Task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class DepartmentService {

    public List<Department> listAll() {
        return Department.listAll();
    }

    public Optional<Department> findById(Long id) {
        return Department.findByIdOptional(id);
    }

    @Transactional
    public Department create(Department d) {
        d.persist();
        if (!d.isPersistent()) {
            throw new WebApplicationException("Could not create department", 400);
        }
        return d;
    }

    public List<Employee> listEmployeesByDepartment(Long id) {
        Department department = findById(id)
            .orElseThrow(() -> new WebApplicationException("Department not found", 404));
        return Employee.list("department", department.getName());
    }

    public List<Task> listTasksByDepartment(Long id) {
        Department department = findById(id)
            .orElseThrow(() -> new WebApplicationException("Department not found", 404));
        return Task.list("assignedTo.department", department.getName());
    }
}
