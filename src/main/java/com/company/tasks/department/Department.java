package com.company.tasks.department;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.tasks.employee.Employee;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "departments")
public class Department extends PanacheEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    public String name;

    @Column(length = 255)
    public String description;

    @OneToOne
    public Employee head;

    @OneToMany(mappedBy = "department")
    public List<Employee> employees = new ArrayList<>();

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getHead() {
        return head;
    }
    public void setHead(Employee head) {
        this.head = head;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
