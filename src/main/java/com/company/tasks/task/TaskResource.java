package com.company.tasks.task;

import java.net.URI;
import java.util.List;

import com.company.tasks.task.Task.TaskPriority;
import com.company.tasks.task.Task.TaskStatus;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
public class TaskResource {

    @Inject
    TaskService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@Valid TaskDTO dto) {
        TaskDTO created = service.create(dto);
        return Response.created(URI.create("/tasks/" + created.getId())).entity(created).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllTasks() {
        List<TaskDTO> dtos = service.listAll();
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskById(@PathParam("id") Long id) {
        return service.findById(id)
            .map(dto -> Response.ok(dto).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") Long id, @Valid TaskDTO dto) {
        TaskDTO updated = service.update(id, dto);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") Long id, TaskStatus status) {
        TaskDTO statusUpdated = service.updateStatus(id, status);
        return Response.ok(statusUpdated).build();
    }

    @PUT
    @Path("/{id}/assign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignTask(@PathParam("id") Long id, Long employeeId) {
        TaskDTO assigned = service.assignTask(id, employeeId);
        return Response.ok(assigned).build();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByStatus(@QueryParam("status") TaskStatus status) {
        List<TaskDTO> dto = service.findByStatus(status);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/priority")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByPriority(@QueryParam("priority") TaskPriority priority) {
        List<TaskDTO> dto = service.findByPriority(priority);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/overdue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOverdueTasks() {
        List<TaskDTO> dto = service.findOverdueTasks();
        return Response.ok(dto).build();
    }
}
