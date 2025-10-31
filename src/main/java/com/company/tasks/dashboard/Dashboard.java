package com.company.tasks.dashboard;

import com.company.tasks.task.Task;
import com.company.tasks.employee.Employee;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Dashboard {
    
    public Map<String, Long> getTaskCountByStatus() {
        List<Task> tasks = Task.listAll();
        return tasks.stream()
            .collect(Collectors.groupingBy(
                task -> task.getStatus().name(),
                Collectors.counting()
            ));
    }

    public List<Employee> getTopEmployeesByTaskCount(int limit) {
        List<Employee> employees = Employee.listAll();
        return employees.stream()
            .sorted((e1, e2) -> Integer.compare(
                (int) Task.count("assignedTo", e2),
                (int) Task.count("assignedTo", e1)
            ))
            .limit(limit)
            .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasksSummary() {
        List<Task> tasks = Task.listAll();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return tasks.stream()
            .filter(task -> task.getDueDate() != null 
                && task.getDueDate().isBefore(now) 
                && task.getStatus() != Task.TaskStatus.DONE 
                && task.getStatus() != Task.TaskStatus.CANCELLED)
            .collect(Collectors.toList());
    }

    public Map<String, Object> getTaskCompletionMetrics() {
        List<Task> tasks = Task.listAll();
        long total = tasks.size();
        long completed = tasks.stream().filter(task -> "DONE".equalsIgnoreCase(task.getStatus().name())).count();
        double completionRate = total == 0 ? 0 : (double) completed / total;
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalTasks", total);
        metrics.put("completedTasks", completed);
        metrics.put("completionRate", completionRate);
        return metrics;
    }

    public long getTotalEmployeeCount() {
        return Employee.count();
    }

    public Double getAverageTaskCompletionTime() {
        List<Task> completedTasks = Task.list("status = ?1 and completedAt is not null", Task.TaskStatus.DONE);
        
        if (completedTasks.isEmpty()) {
            return 0.0;
        }
        
        double totalHours = completedTasks.stream()
            .filter(task -> task.getCreatedAt() != null && task.getCompletedAt() != null)
            .mapToDouble(task -> {
                java.time.Duration duration = java.time.Duration.between(
                    task.getCreatedAt(), 
                    task.getCompletedAt()
                );
                return duration.toHours();
            })
            .sum();
        
        return totalHours / completedTasks.size();
    }
}
