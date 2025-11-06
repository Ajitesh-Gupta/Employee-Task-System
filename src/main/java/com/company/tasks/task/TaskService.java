package com.company.tasks.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.company.tasks.employee.Employee;
import com.company.tasks.task.Task.TaskPriority;
import com.company.tasks.task.Task.TaskStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class TaskService {

    public List<TaskDTO> listAll() {
        return Task.<Task>listAll().stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public Optional<TaskDTO> findById(Long id) {
        return Task.<Task>findByIdOptional(id)
            .map(TaskDTO::fromEntity);
    }

    @Transactional
    public TaskDTO create(TaskDTO dto) {
        Task t = dto.toEntity();
        if (t.getDueDate() != null && !t.getDueDate().isAfter(java.time.LocalDateTime.now())) {
            throw new WebApplicationException("Due date must be in the future", 400);
        }
        t.persist();
        if (!t.isPersistent()) {
            throw new WebApplicationException("Could not create task", 400);
        }
        return TaskDTO.fromEntity(t);
    }

    @Transactional
    public TaskDTO update(Long id, TaskDTO dto) {
        Optional<Task> opt = Task.findByIdOptional(id);
        if (!opt.isPresent()) {
            throw new WebApplicationException("Task not found", 404);
        }
        Task t = opt.get();

        if (dto.getTitle() != null) t.setTitle(dto.getTitle());
        if (dto.getDescription() != null) t.setDescription(dto.getDescription());
        if (dto.getStatus() != null) t.setStatus(dto.getStatus());
        if (dto.getPriority() != null) t.setPriority(dto.getPriority());
        if (dto.getAssignedEmployeeId() != null) {
            Employee employee = Employee.findById(dto.getAssignedEmployeeId());
            if (employee != null) {
                t.setAssignedEmployee(employee);
            }
        }
        if (dto.getDueDate() != null) t.setDueDate(dto.getDueDate());

        t.persist();
        return TaskDTO.fromEntity(t);
    }
    
    @Transactional
    public boolean delete(Long id) {
        return Task.deleteById(id);
    }

    @Transactional
    public TaskDTO updateStatus(Long id, TaskStatus status) {
        Task t = Task.findById(id);
        if (t == null) {
            throw new WebApplicationException("Task not found", 404);
        }

        t.setStatus(status);    
        if (status == TaskStatus.DONE && t.completedAt == null) {
            t.completedAt = LocalDateTime.now();
        }
        
        t.persist();
        return TaskDTO.fromEntity(t);
    }

    @Transactional
    public TaskDTO assignTask(Long id, Long empId) {
        Task t = Task.findById(id);
        if (t == null) {
            throw new WebApplicationException("Task not found", 404);
        }

        Employee employee = Employee.findById(empId);
        if (employee == null) {
            throw new WebApplicationException("Employee not found", 404);
        }

        t.setAssignedEmployee(employee);
        t.persist();
        return TaskDTO.fromEntity(t);
    }

    public List<TaskDTO> findByStatus(TaskStatus status) {
        return Task.<Task>list("status", status)
            .stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<TaskDTO> findByPriority(TaskPriority priority) {
        return Task.<Task>list("priority", priority)
            .stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<TaskDTO> findOverdueTasks() {
        return Task.<Task>find("dueDate < ?1 and status not in (?2, ?3)", 
            LocalDateTime.now(), 
            TaskStatus.DONE, 
            TaskStatus.IN_PROGRESS)
            .list()
            .stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
