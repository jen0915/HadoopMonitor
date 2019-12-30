package com.idpp.hadoop;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class MonitorMain {

	
	public void action() {
		String requestURL = "";
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(requestURL); //GET五社球 URL 持失
			HttpResponse response = client.execute(getRequest);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		MonitorMain main = new MonitorMain();
		main.action();
	}
}
