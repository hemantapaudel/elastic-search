package com.my.elasticsearch.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESClientConfig {
	
	@Bean
	public RestClient restClient(@Value("${host}") String host, @Value("${port}") int port){
		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, "http"));
		Header[] defaultHeaders = new Header[]{new BasicHeader("Content-Type", "application/json")};
		restClientBuilder.setDefaultHeaders(defaultHeaders);	
		restClientBuilder.setMaxRetryTimeoutMillis(10000);
		restClientBuilder.setFailureListener(new RestClient.FailureListener() {
		    @Override
		    public void onFailure(HttpHost host) {
		    	System.out.println("failure callback happened");
		    }
		});
		return restClientBuilder.build();
	}
	
	
	
	
}
