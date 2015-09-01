package com.swb.searchserv.util;

import javax.annotation.PreDestroy;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContextLifecycleHandler {
	@Autowired
	private Client esClient;

	@PreDestroy
	public void destroy() {
		System.out.println("Closing elastic search connections..");
		if (esClient != null)
			esClient.close();
	}

}
