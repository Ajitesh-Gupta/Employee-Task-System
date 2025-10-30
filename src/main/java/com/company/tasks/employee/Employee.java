package com.company.tasks.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.tasks.department.Department;
import com.company.tasks.task.Task;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class Employee extends PanacheEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    public String name;

    @NotBlank
    @Email
    @Column(unique = true)
    public String email;

    @ManyToOne
    public Department department;

    public String position;

    @OneToMany(mappedBy = "assignedTo")
    public List<Task> tasks = new ArrayList<>();

    @Column(name = "join_date")
    public LocalDate joindate;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getters/Setters
    public Long getId() {
        return id;
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

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
