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
            .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(now) && !"COMPLETED".equalsIgnoreCase(task.getStatus().name()))
            .collect(Collectors.toList());
    }

    public Map<String, Object> getTaskCompletionMetrics() {
        List<Task> tasks = Task.listAll();
        long total = tasks.size();
        long completed = tasks.stream().filter(task -> "COMPLETED".equalsIgnoreCase(task.getStatus().name())).count();
        double completionRate = total == 0 ? 0 : (double) completed / total;
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalTasks", total);
        metrics.put("completedTasks", completed);
        metrics.put("completionRate", completionRate);
        return metrics;
    }
}
