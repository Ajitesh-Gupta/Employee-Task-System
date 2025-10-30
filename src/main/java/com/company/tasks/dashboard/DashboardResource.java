package com.company.tasks.dashboard;

import java.util.List;
import java.util.Map;

import com.company.tasks.employee.Employee;
import com.company.tasks.task.Task;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

	@Inject
	Dashboard dashboard;

	@GET
	@Path("/stats")
	public Map<String, Object> getStats() {
		Map<String, Long> statusCounts = dashboard.getTaskCountByStatus();
		Map<String, Object> completionMetrics = dashboard.getTaskCompletionMetrics();
		return Map.of(
			"statusCounts", statusCounts,
			"completionMetrics", completionMetrics
		);
	}

	@GET
	@Path("/employees/top")
	public List<Employee> getTopEmployees(@QueryParam("limit") int limit) {
		return dashboard.getTopEmployeesByTaskCount(limit > 0 ? limit : 5);
	}

	@GET
	@Path("/tasks/overdue")
	public List<Task> getOverdueTasks() {
		return dashboard.getOverdueTasksSummary();
	}
}
