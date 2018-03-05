package com.my.elasticsearch.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import com.google.gson.Gson;

class Employee{
	String name;
	String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

class Users{
	List<Employee> employees;
	int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
}




public class UpsertExample {
	
	public static String getDummy() {
		List<Employee> employees = new ArrayList<Employee>();
		Employee e1 = new Employee();
		e1.setId("1251");
		e1.setName("Hemanta");
		employees.add(e1);
		
		Gson gson = new Gson();
		Users users = new Users();
		users.setCount(2);
		users.setEmployees(employees);
		
		return gson.toJson(users);

	}
	
	public static void main(String[] args) throws IOException  {
		
		
		
		
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http"),
		                new HttpHost("localhost", 9201, "http")));
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		
		Employee e1 = new Employee();
		e1.setId("1251");
		e1.setName("Hemanta");
		params.put("e", new Gson().toJson(e1));		
		params.put("count", 2);
		
		
		
		
	/*	UpdateRequest request = new UpdateRequest("posts","doc","123456");
		Script inline = new Script(ScriptType.INLINE, "painless","ctx._source.count +=params.count", params);  
		request.script(inline);
		String json = getDummy();
		System.out.println("dummy initial object" +json);
		
		
		
		request.upsert(new IndexRequest("posts","doc","123456").source(json));*/
		
		IndexRequest index = new IndexRequest(
		        "posts", 
		        "doc",  
		        "123456");
		index.source(new Gson().toJson(e1), XContentType.JSON);
		
		
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.add(index);
		
		Header[] headers = new Header[]{new BasicHeader("Content-Type", "application/json")};
		bulkRequest.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
		BulkResponse response = client.bulk(bulkRequest, headers);
		//client.close();
		
		//System.out.println(response);
		
		
		
		
		
		
		
		
		
		
		
		
	
		
				
		
	}

}
