package com.company.tasks.exception;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

public class ExceptionMappers {

	public static class ErrorResponse {
		private String message;
		private int status;
		private OffsetDateTime timestamp = OffsetDateTime.now();

		public ErrorResponse() {}

		public ErrorResponse(String message, int status) {
			this.message = message;
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public OffsetDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(OffsetDateTime timestamp) {
			this.timestamp = timestamp;
		}
	}

	@Provider
	public static class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

		@Override
		public Response toResponse(ResourceNotFoundException exception) {
			ErrorResponse err = new ErrorResponse(exception.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
			return Response.status(Response.Status.NOT_FOUND)
					.type(MediaType.APPLICATION_JSON)
					.entity(err)
					.build();
		}
	}

	@Provider
	public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

		@Override
		public Response toResponse(ConstraintViolationException exception) {
			String message = exception.getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(", "));
			ErrorResponse err = new ErrorResponse(message, Response.Status.BAD_REQUEST.getStatusCode());
			return Response.status(Response.Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(err)
					.build();
		}
	}

	@Provider
	public static class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

		@Override
		public Response toResponse(WebApplicationException exception) {
			Response exResp = exception.getResponse();
			int status = exResp != null ? exResp.getStatus() : Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
			String msg = exception.getMessage();
			if (msg == null || msg.trim().isEmpty()) {
				msg = "HTTP " + status;
			}
			ErrorResponse err = new ErrorResponse(msg, status);
			return Response.status(status)
					.type(MediaType.APPLICATION_JSON)
					.entity(err)
					.build();
		}
	}

	@Provider
	public static class GenericExceptionMapper implements ExceptionMapper<Exception> {

		@Override
		public Response toResponse(Exception exception) {
			ErrorResponse err = new ErrorResponse("Internal server error", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(MediaType.APPLICATION_JSON)
					.entity(err)
					.build();
		}
	}

}
