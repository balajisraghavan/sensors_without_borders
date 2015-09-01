package com.swb.searchserv.controller;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Path("/")
public class DataIngestionResource {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(DataIngestionResource.class);
	private static final ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	private Client esClient;

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{index}/{type}")
	public Response getAll(@PathParam("index") Index index, @PathParam("type") DocType type, byte[] inputJson)
			throws JsonParseException, JsonMappingException, IOException {
		if (index == null || type == null)
			throw new WebApplicationException("Invalid index or document type", 400);
		log.debug("Save {} / {} message: {}", index, type, new String(inputJson));
		// validate input to see if its a valid json
		jsonMapper.readValue(inputJson, Object.class);
		IndexResponse response = esClient.prepareIndex(index.toString(), type.toString()).setSource(inputJson).execute()
				.actionGet();
		return Response.created(null).entity("{\"id\": \"" + response.getId() + "\"}").build();
	}

}
