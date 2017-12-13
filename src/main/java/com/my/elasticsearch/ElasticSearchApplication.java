package com.my.elasticsearch;

import java.io.IOException;

import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ElasticSearchApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(ElasticSearchApplication.class, args);
		RestClient restClient = context.getBean(RestClient.class);
		
		Response response = restClient.performRequest("GET", "/");
		System.out.println(response);
		
		
		System.exit(0);
	}
}
