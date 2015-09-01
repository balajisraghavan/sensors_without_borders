package com.swb.searchserv.util;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:app.properties")
public class AppConfig {
	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	@Value("${es.hosts}")
	private String esHosts;
	@Value("${es.port}")
	private int esPort;

	@Bean
	public Client createESClient() {
		log.info("Connecting to Elastic at hosts: {}", esHosts);
		TransportClient client = new TransportClient();
		for (String addr : esHosts.split(",")) {
			client.addTransportAddress(new InetSocketTransportAddress(addr, esPort));
		}
		return client;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
