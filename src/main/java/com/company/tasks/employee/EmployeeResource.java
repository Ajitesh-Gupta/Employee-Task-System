package com.company.tasks.employee;

import java.net.URI;
import java.util.List;

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

@Path("/employees")
public class EmployeeResource {

    @Inject
    EmployeeService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(@Valid EmployeeDTO dto) {
        EmployeeDTO created = service.create(dto);
        return Response.created(URI.create("/employees/" + created.getId())).entity(created).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllEmployees() {
        List<EmployeeDTO> dtos = service.listAll();
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") Long id) {
        return service.findById(id)
            .map(dto -> Response.ok(dto).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") Long id, @Valid EmployeeDTO dto) {
        EmployeeDTO updated = service.update(id, dto);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeTask(@PathParam("id") Long id) {
        return Response.ok(service.listEmployeeTasks(id)).build();
    }

    @GET
    @Path("/search/department")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByDepartment(@QueryParam("department") String department) {
        if (department == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Department parameter is required")
                .build();
        }
        return Response.ok(service.getEmployeeDepartment(department)).build();
    }
}
