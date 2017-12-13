package com.my.elasticsearch.example;

import java.io.IOException;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import com.my.elasticsearch.config.ESClientConfig;

public class ElasticSearchExample1 {
	public static void main(String[] args) throws IOException {
	
		ESClientConfig config = new ESClientConfig();
		RestClient restClient = config.restClient("localhost", 9200);
		Response response = restClient.performRequest("GET", "/");
		System.out.println(response);
		
		
		Map<String, String> params = Collections.emptyMap();
		String jsonString = "{" +
		            "\"user\":\"kimchy\"," +
		            "\"postDate\":\"2013-01-30\"," +
		            "\"message\":\"trying out Elasticsearch\"" +
		        "}";
		HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
		Response response1 = restClient.performRequest("PUT", "/posts/doc/1", params, entity); 
		System.out.println(response1);

		
		
		Map<String, String> pahram3 = Collections.emptyMap();
		HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
		        new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
		
		Response response2 = restClient.performRequest("GET", "/posts/_search", pahram3, null, consumerFactory); 
		System.out.println("===response2"+response2);
		restClient.close();
		
	}
}
