package com.idpp.hadoop;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.idpp.hadoop.dao.TestDAO;
import com.idpp.hadoop.dto.ResponseModel;

public class MonitorMain {

	List<String> hostList = new ArrayList<String>();
	List<String> dataNodeList = new ArrayList<String>();
	
	public static String JsonParsing (String jsontext, String getTxt) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(jsontext);
	
		String parsedText = jobj.get(getTxt).toString();
		
		return parsedText;
	}
	
	public static JSONArray JsonArrayParsing(String result, String arrString) throws ParseException {
		
		JSONParser jparser = new JSONParser();
		JSONObject jobj = (JSONObject)jparser.parse(result);
		JSONArray jarr = (JSONArray)jobj.get(arrString);
		return jarr;
	}
	
	public static void main(String[] args) throws ParseException {
		MonitorMain main = new MonitorMain();
		String name = "admin";
        String password = "QKDqwer1234!@";

        String authString = name + ":" + password;
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
		
        main.getNameNode(authStringEnc);
//        main.getHostState(authStringEnc);
//        main.getNodeCntState(authStringEnc); 
//        main.getNodeRoles(authStringEnc);
	}
	
	public void getNameNode(String authStringEnc) {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE?fields=metrics/dfs,metrics/cpu,metrics/jvm";
		
		try {
			/*
			 * String name = "admin"; String password = "QKDqwer1234!@";
			 * 
			 * String authString = name + ":" + password; byte[] authEncBytes =
			 * Base64.getEncoder().encode(authString.getBytes()); String authStringEnc = new
			 * String(authEncBytes);
			 */

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
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timenow = sdf.format(System.currentTimeMillis());

			String metrics = JsonParsing(result, "metrics");
			
			String cpu = JsonParsing(metrics, "cpu");
			String dfs = JsonParsing(metrics, "dfs");
			String jvm = JsonParsing(metrics, "jvm");
			
			
			String cpu_idle = JsonParsing(cpu, "cpu_idle");
			cpu_idle = String.format("%.2f", Double.parseDouble(cpu_idle));
			String FSNamesystem = JsonParsing(dfs, "FSNamesystem");
			String CapacityTotalGB = JsonParsing(FSNamesystem, "CapacityTotalGB"); // DFS_DISK_TOTAL
			String CapacityUsedGB = JsonParsing(FSNamesystem, "CapacityUsedGB"); //column DFS_DISK_USED
			String CapacityRemainingGB = JsonParsing(FSNamesystem, "CapacityRemainingGB"); //column DFS_DISK_REMAINING
			
			String namenode = JsonParsing(dfs, "namenode");
			String PercentUsed = JsonParsing(namenode, "PercentUsed");  //coulum DFS_DISK_USED_PERCENT
			PercentUsed = String.format("%.2f", Double.parseDouble(PercentUsed));
			String PercentRemaining = JsonParsing(namenode, "PercentRemaining"); //COLUMN DFS_DISK_REMINING_PERCENT
			PercentRemaining = String.format("%.2f", Double.parseDouble(PercentRemaining));
					
			String namenode_heap_used = JsonParsing(jvm, "memHeapUsedM"); //NAMENODE_HEAP_USED 
			namenode_heap_used = String.format("%.2f", Double.parseDouble(namenode_heap_used));
			String namenode_heap_committed = JsonParsing(jvm, "memHeapCommittedM"); //NAMENODE_HEAP_COMMITTED 
			namenode_heap_committed = String.format("%.2f", Double.parseDouble(namenode_heap_committed));
			
			
			System.out.println("cpu idle : " + cpu_idle);
			System.out.println("CapacityTotalGB : " + CapacityTotalGB);
			System.out.println("CapacityUsedGB : " + CapacityUsedGB);
			System.out.println("CapacityRemainingGB : " + CapacityRemainingGB);
			System.out.println("PercentUsed : " + PercentUsed);
			System.out.println("PercentRemaining : " + PercentRemaining);
			System.out.println("namenode_heap_used : " + namenode_heap_used);
			System.out.println("namenode_heap_committed : " + namenode_heap_committed);
			
			
			ResponseModel model = new ResponseModel();
			model.setCPU_IDLE(cpu_idle);
			model.setNAMENODE_HEAP_USED(namenode_heap_used);
			model.setNAMENODE_HEAP_COMMITTED(namenode_heap_committed);
			model.setDFS_DISK_TOTAL(CapacityTotalGB);
			model.setDFS_DISK_USED(CapacityUsedGB);
			model.setDFS_DISK_USED_PERCENT(PercentUsed);
			model.setDFS_DISK_REMAINING(CapacityRemainingGB);
			model.setDFS_DISK_REMAINING_PERCENT(PercentRemaining);
			model.setLAST_CHECK_TIME(timenow);

			TestDAO.insertHdfsOverall(model);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}



	private void getNodeRoles(String authStringEnc) {
		List<ResponseModel> list = new ArrayList<ResponseModel>();
		ResponseModel model1 = new ResponseModel();
		ResponseModel model2 = new ResponseModel();
		
		//NAMENODE
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE";
		
		try {
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
	        
	        JSONArray arr= JsonArrayParsing(result, "host_components");
	        JSONObject resultobj = (JSONObject)arr.get(0);
	        
	        String hostRole = resultobj.get("HostRoles").toString();
			String componentN = JsonParsing(hostRole, "component_name");
			String hostN = JsonParsing(hostRole, "host_name");
			
			System.out.println("host name : " + hostN + ", component name : " + componentN);
			
			
			model1.setHOST_NAME(hostN);
			model1.setCOMPONENT_CODE_NM(componentN);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//SECONDARY_NAMENODE
		String s_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/SECONDARY_NAMENODE";
		
		try {
			URL url = new URL(s_requestURL);
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
	        
	        JSONArray s_arr = JsonArrayParsing(result, "host_components");
	        JSONObject s_resultobj = (JSONObject)s_arr.get(0);
	        
	        String hostRole_s = s_resultobj.get("HostRoles").toString();
			String componentN_s = JsonParsing(hostRole_s, "component_name");
			String hostN_s = JsonParsing(hostRole_s, "host_name");
			
			System.out.println("host name : " + hostN_s + ", component name : " + componentN_s);
			
			
			model2.setHOST_NAME(hostN_s);
			model2.setCOMPONENT_CODE_NM(componentN_s);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		list.add(model1);
		list.add(model2);
		
		//DataNode
		String d_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/DATANODE";
		
		try {
			URL url = new URL(d_requestURL);
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
	        
	        JSONArray d_arr = JsonArrayParsing(result, "host_components");
	        
	        for(int i=0; i<d_arr.size(); i++) {
				JSONObject roleObj3 = (JSONObject)d_arr.get(i);
				
				String hostRole_d = roleObj3.get("HostRoles").toString();
				String componentN_d = JsonParsing(hostRole_d, "component_name");
				String hostN_d = JsonParsing(hostRole_d, "host_name");
				
				System.out.println("host name : " + hostN_d + ", component name : " + componentN_d);
				
				ResponseModel model3 = new ResponseModel();
				model3.setHOST_NAME(hostN_d);
				model3.setCOMPONENT_CODE_NM(componentN_d);
				
				list.add(model3);
			}
	        
	        TestDAO.insertHostRole(list);
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void getNodeCntState(String authStringEnc) {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/DATANODE?fields=ServiceComponentInfo";
		String livenodeCnt = "";
		String totalCnt = "";
		String datanodeState = "";
		String sec_state = "";
		
		try {
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
	        
	        String ServiceComponentInfo = JsonParsing(result, "ServiceComponentInfo");
			
			livenodeCnt = JsonParsing(ServiceComponentInfo, "started_count");
			totalCnt = JsonParsing(ServiceComponentInfo, "total_count");
			datanodeState = JsonParsing(ServiceComponentInfo, "state");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String sec_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/SECONDARY_NAMENODE";
		
		try {
			URL url = new URL(sec_requestURL);
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
	        
	        String serviceCompo = JsonParsing(result, "ServiceComponentInfo");
			sec_state = JsonParsing(serviceCompo, "state");
			
			JSONArray arr = JsonArrayParsing(result, "host_components");
			JSONObject obj = (JSONObject)arr.get(0);
			
			String hostRoles = obj.get("HostRoles").toString();
			String sec_node_name = JsonParsing(hostRoles, "host_name");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String namenode_url = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/state,host_components";
		
		try {
			URL url = new URL(namenode_url);
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
	        
	        String ComponentINFO = JsonParsing(result, "ServiceComponentInfo");
			String nameState = JsonParsing(ComponentINFO, "state");
			
			JSONArray jarr = JsonArrayParsing(result, "host_components");
			JSONObject jobj = (JSONObject)jarr.get(0);
			
			String HostRoles = jobj.get("HostRoles").toString();
			String namenodeNM = JsonParsing(HostRoles, "host_name");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timenow = sdf.format(System.currentTimeMillis());
			
			ResponseModel model = new ResponseModel();
			model.setNAMENODE_STATUS(nameState);
			model.setSNAMENODE_STATUS(sec_state);
			model.setLIVE_DATANODE_CNT(livenodeCnt);
			model.setTOTAL_DATANODE_CNT(totalCnt);
			model.setDATANODE_STATE(datanodeState);
			model.setLAST_CHECK_TIME(timenow);
			
			TestDAO.insertNodeState(model);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void getHostState(String authStringEnc) throws ParseException {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts";
		JSONArray h_arr;
		
		try {
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
		
	        
	        h_arr = JsonArrayParsing(result, "items");
	      
		 
		
	        for(int i=0; i<h_arr.size(); i++) {
	        	JSONObject hostobj = (JSONObject)h_arr.get(i);
	        	
	        	String Host = hostobj.get("Hosts").toString();
				String host_nm = JsonParsing(Host, "host_name");
				
				//lake1.idpp.com ~ lake8.idpp.com 까지 출력
				System.out.println(host_nm);  
				hostList.add(host_nm);
				
				
				//호스트 정보 조회하면서 host_state, host ip, cpu_count, os_type 등  얻을 수 있음
				
				//test//lake1 ~8 까지 한꺼번에 했을때 시간이 좀 걸림
				if(i ==0) {
					String stateUrl = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts/" + host_nm + "?fields=Hosts/host_state,Hosts/ip,Hosts/host_status";
					
					String host_state="";
					String host_ip="";
					String host_status="";
					
					
					try {
						URL host_url = new URL(stateUrl);
				        URLConnection host_urlConnection = host_url.openConnection();
				        host_urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				        InputStream inputS = host_urlConnection.getInputStream();
				        InputStreamReader inputSR = new InputStreamReader(inputS);

				        int numCharsReadCnt;
				        char[] charArr = new char[1024];
				        StringBuffer stb = new StringBuffer();
				        while ((numCharsReadCnt = inputSR.read(charArr)) > 0) {
				        	stb.append(charArr, 0, numCharsReadCnt);
				        }
				        String host_result = stb.toString();
				        
				        System.out.println("host_result : " + host_result);
				        String json_hosts = JsonParsing(host_result, "Hosts");
						host_state = JsonParsing(json_hosts, "host_state");
						host_ip = JsonParsing(json_hosts, "ip");
						host_status = JsonParsing(json_hosts, "host_status");
						
						System.out.println("host name : " + host_nm);
						System.out.println("host_state : " + host_state);
						System.out.println("host_ip : " + host_ip);
						System.out.println("host_status : " + host_status);
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					
					/* cpu, disk, memory 정보 조회 */
					
					String host_metrics_url = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts/" + host_nm + "?fields=metrics/disk,metrics/cpu,metrics/memory";
					String metrics = "";
					String cpu = "";
					String disk = "";
					String mem = "";
					try {
						URL cdmUrl = new URL(host_metrics_url);
				        URLConnection cdm_urlConnection = cdmUrl.openConnection();
				        cdm_urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				        InputStream inputS = cdm_urlConnection.getInputStream();
				        InputStreamReader inputSR = new InputStreamReader(inputS);

				        int numCharsReadCnt;
				        char[] charArr = new char[1024];
				        StringBuffer buf = new StringBuffer();
				        while ((numCharsReadCnt = inputSR.read(charArr)) > 0) {
				        	buf.append(charArr, 0, numCharsReadCnt);
				        }
				        String cdm_result = buf.toString();
				        
				        metrics = JsonParsing(cdm_result, "metrics");
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timenow = sdf.format(System.currentTimeMillis());
					
					cpu = JsonParsing(metrics, "cpu");
					disk = JsonParsing(metrics, "disk");
					mem = JsonParsing(metrics, "memory");
					
					
					String cpu_idle = JsonParsing(cpu, "cpu_idle");
					String disk_free = JsonParsing(disk, "disk_free");
					String disk_total = JsonParsing(disk, "disk_total");
					String disk_usage_rate = String.format("%.2f", (Double.parseDouble(disk_total)-Double.parseDouble(disk_free))*100/Double.parseDouble(disk_total));
					String mem_free = JsonParsing(mem, "mem_free");
					String mem_total = JsonParsing(mem, "mem_total");
					
					System.out.println("cpu_idle : " + cpu_idle);
					System.out.println("disk free : " + disk_free);
					System.out.println("disk total : " + disk_total);
					System.out.println("disk usage rate : " + disk_usage_rate);
					System.out.println("mem free : " + mem_free);
					System.out.println("mem_total : " + mem_total);
					
					
					ResponseModel model = new ResponseModel();
					model.setHOST_NAME(host_nm);
					model.setHOST_INDEX(i+1);
					model.setHOST_IP(host_ip);
					model.setHOST_STATE_CODE(host_state);
					model.setHOST_STATUS_CODE(host_status);
					model.setHOST_CPU_IDLE(cpu_idle);
					model.setHOST_DISK_FREE(disk_free);
					model.setHOST_DISK_TOTAL(disk_total);
					model.setHOST_DISK_USAGE_RATE(disk_usage_rate);
					model.setHOST_MEM_FREE(mem_free);
					model.setHOST_MEM_TOTAL(mem_total);
					model.setLAST_CHECK_TIME(timenow);
					
					try {
						TestDAO.insertAmbariHostInfo(model);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
	        }
		}catch(Exception e) {
			e.printStackTrace();
	 }
	}
}
