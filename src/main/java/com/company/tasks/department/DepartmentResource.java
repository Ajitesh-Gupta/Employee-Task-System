package com.company.tasks.department;

import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/departments")
public class DepartmentResource {

    @Inject
    DepartmentService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDepartment(Department d) {
        Department created = service.create(d);
        return Response.created(URI.create("/api/departments/" + created.id)).entity(created).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllDepartments() {
        List<Department> deps = service.listAll();
        return Response.ok(deps).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartmentById(@PathParam("id") Long id) {
        return service.findById(id)
            .map(dep -> Response.ok(dep).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/{id}/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDepartmentEmployees(@PathParam("id") Long id) {
        return Response.ok(service.listEmployeesByDepartment(id)).build();
    }

    @GET
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDepartmentTasks(@PathParam("id") Long id) {
        return Response.ok(service.listTasksByDepartment(id)).build();
    }
}
