package com.idpp.hadoop;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class MonitorMain {

	
	public void action() {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/SECONDARY_NAMENODE";
		
		try {
	        String name = "admin";
	        String password = "QKDqwer1234!@";

	        String authString = name + ":" + password;
	        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
	        String authStringEnc = new String(authEncBytes);

	        URL url = new URL(requestURL);
	        URLConnection urlConnection = url.openConnection();
	        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
	        InputStream is = urlConnection.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);

	        int numCharsRead;
	        char[] charArray = new char[1024];
	        StringBuffer sb = new StringBuffer();
	        while ((numCharsRead = isr.read(charArray)) > 0) {
	            sb.append(charArray, 0, numCharsRead);
	        }
	        String result = sb.toString();
	        System.out.println("result : " + result);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MonitorMain main = new MonitorMain();
		main.action();
	}
}
