package com.my.elasticsearch.low;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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


class Params{
	
	private Employee emp;
	int count;
	
	public Params() {
		count =4;
		emp = new Employee();
		emp.id="420";
		emp.name ="Garurav";
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	
}

class Scrt{
	private String source = "ctx._source.employees.add(params.emp)";
	private String lang = "painless";
	private Params params;
	
	public Scrt() {
		params = new Params();
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}
}


class Query{
	
	private Scrt script;
	private Users upsert;
	public Scrt getScript() {
		return script;
	}
	public void setScript(Scrt script) {
		this.script = script;
	}
	public Users getUpsert() {
		return upsert;
	}
	public void setUpsert(Users upsert) {
		this.upsert = upsert;
	}
	
}

	
	

public class LowLevelClientExample {
	
	
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
		
		Scrt script = new Scrt();
		
		Query query = new Query();
		query.setScript(script);
		query.setUpsert(users);
		
		System.out.println(gson.toJson(query));
		
		return gson.toJson(query);

	}
	
	 private static HttpHeaders getHeaders(){
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Type", "application/json");
	        return headers;
	    }
	
	public static void main(String[] args) throws IOException {
		
		
		String json = getDummy();
		System.out.println("request ==="+json);
		
		HttpEntity<String> request = new HttpEntity<String>(json,getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		String uri = "http://localhost:9200/dummyindex/dummytype/101/_update?refresh=true";
		
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(uri, request, String.class);
		System.out.println(postForEntity.getBody());
		
	
						
	
	
	
	
	
	}
}



