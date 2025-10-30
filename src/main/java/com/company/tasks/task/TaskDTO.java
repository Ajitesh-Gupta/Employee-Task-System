package com.company.tasks.task;

import java.time.LocalDateTime;

import com.company.tasks.employee.Employee;
import com.company.tasks.task.Task.TaskPriority;
import com.company.tasks.task.Task.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskDTO {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String title;

    @NotBlank
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Employee assignedEmployee;

    private LocalDateTime dueDate;
    
    private LocalDateTime completedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }
    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public static TaskDTO fromEntity(Task t) {
        if (t == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setStatus(t.getStatus());
        dto.setPriority(t.getPriority());
        dto.setAssignedEmployee(t.getAssignedEmployee());
        dto.setDueDate(t.getDueDate());
        return dto;
    }

    public Task toEntity() {
        Task t = new Task();
        t.setTitle(this.title);
        t.setDescription(this.description);
        t.setStatus(this.status);
        t.setPriority(this.priority);
        t.setAssignedEmployee(this.assignedEmployee);
        t.setDueDate(this.dueDate);
        return t;
    }
}
