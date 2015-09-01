package com.swb.searchserv.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Provider
@Component
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	Logger logger = LoggerFactory.getLogger(GeneralExceptionMapper.class);

	@Override
	public Response toResponse(Exception exception) {
		Response response = null;
		logger.error("An unmapped exception was intercepted", exception);

		if (logger.isDebugEnabled() && exception instanceof WebApplicationException) {
			response = ((WebApplicationException) exception).getResponse();
			logger.debug("WebApplicationException response status code: " + response.getStatus());
		}

		if (exception instanceof WebApplicationException) {
			WebApplicationException webEx=(WebApplicationException) exception;
			ResponseBuilder responseBuilder = Response.status(webEx.getResponse().getStatus())
					.type(MediaType.APPLICATION_JSON)
					.entity("{\"message\": \"" + StringEscapeUtils.escapeJson(exception.getMessage()) + "\"}");
			response = responseBuilder.build();
		} else {
			ResponseBuilder responseBuilder = Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(MediaType.APPLICATION_JSON)
					.entity("{\"message\": \"Internal server error - " + StringEscapeUtils.escapeJson(exception.getMessage())
							+ "\"}");
			response = responseBuilder.build();
		}
		return response;
	}
}
