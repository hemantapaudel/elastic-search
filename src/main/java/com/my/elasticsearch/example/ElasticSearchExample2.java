package com.my.elasticsearch.example;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;

import com.my.elasticsearch.config.ESClientConfig;

public class ElasticSearchExample2 {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		ESClientConfig config = new ESClientConfig();
		RestClient restClient = config.restClient("localhost", 9200);
	
		class Listener implements ResponseListener{
		    @Override
		    public void onFailure(Exception exception) {
		        System.out.println("=====exception=="+exception.getMessage());
		    }

			@Override
			public void onSuccess(Response response) {
			System.out.println("============"+response);	
			}
		};
		Map<String, String> params = Collections.singletonMap("pretty", "true");
		restClient.performRequestAsync("GET", "/posts/doc/1", params,   new Listener());
		
		
		
		
		String jsonString = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		        "}";
		HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
		restClient.performRequestAsync("PUT", "/posts/doc/2", params, entity, new Listener()); 

		
		HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
		        new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
		restClient.performRequestAsync("GET", "/posts/_search", params, null, consumerFactory, new Listener()); 

		
		
		
		final CountDownLatch latch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
		    restClient.performRequestAsync(
		            "PUT",
		            "/posts/doc/" + i,
		            Collections.<String, String>emptyMap(),
		            entity,
		            new ResponseListener() {
		                @Override
		                public void onSuccess(Response response) {
		                	System.out.println("=======onSuccess====="+response);
		                    latch.countDown();
		                }

		                @Override
		                public void onFailure(Exception exception) {
		                	System.out.println("=======onFailure====="+exception.getMessage());
		                    latch.countDown();
		                }
		            }
		    );
		}
		latch.await();
		Thread.sleep(10000);
		restClient.close();
	
	}

}
