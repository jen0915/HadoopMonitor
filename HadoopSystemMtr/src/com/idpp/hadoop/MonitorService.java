package com.idpp.hadoop;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.idpp.hadoop.dao.TestDAO;
import com.idpp.hadoop.dto.DiskInfoVO;
import com.idpp.hadoop.dto.ResponseModel;

public class MonitorService {

	private static List<String> hostList = new ArrayList<String>();
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
	
	public static String GetRestResponse(String requesturl, String authStringEnc) {
		String result = "";
		try {
			URL url = new URL(requesturl);
	        URLConnection url_connection = url.openConnection();
	        url_connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
	        InputStream is = url_connection.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);

	        int numCharsReadCnt;
	        char[] charArr = new char[1024];
	        StringBuffer sb = new StringBuffer();
	        while ((numCharsReadCnt = isr.read(charArr)) > 0) {
	        	sb.append(charArr, 0, numCharsReadCnt);
	        }
	        result = sb.toString();
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void getNameNode(String authStringEnc) {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE?fields=metrics/dfs,metrics/cpu,metrics/jvm";
		
		try {
			String result = GetRestResponse(requestURL, authStringEnc);
	       
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
			model.setDFS_DISK_TOTAL(CapacityTotalGB); //분산시스템 디스크 총량
			model.setDFS_DISK_USED(CapacityUsedGB); //분산시스템 디스크 사용량
			model.setDFS_DISK_USED_PERCENT(PercentUsed);
			model.setDFS_DISK_REMAINING(CapacityRemainingGB);
			model.setDFS_DISK_REMAINING_PERCENT(PercentRemaining);
			model.setLAST_CHECK_TIME(timenow);

			TestDAO.insertHdfsOverall(model);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getHostList(String authStringEnc) throws ParseException {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts";
		JSONArray h_arr;
		
		try {
			String result = GetRestResponse(requestURL, authStringEnc);
	        
	        h_arr = JsonArrayParsing(result, "items");
	      
	        for(int i=0; i<h_arr.size(); i++) {
	        	JSONObject hostobj = (JSONObject)h_arr.get(i);
	        	
	        	String Host = hostobj.get("Hosts").toString();
				String host_nm = JsonParsing(Host, "host_name");
				
				//lake1.idpp.com ~ lake8.idpp.com 까지 출력
				System.out.println(host_nm);  
				hostList.add(host_nm);
	        }
				
	        InsertStateInfo(hostList, authStringEnc);
	        
	        InsertPerformInfo(hostList, authStringEnc);
				
				
		}catch(Exception e) {
			e.printStackTrace();
	 }
	}
	

	private static void InsertPerformInfo(List<String> hostList, String authStringEnc) {
		List<ResponseModel> performlist = new ArrayList<ResponseModel>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timenow = sdf.format(System.currentTimeMillis());
		
		for(int i=0; i<hostList.size(); i++) {
			String url = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts/" + hostList.get(i) + "?fields=metrics/disk,metrics/cpu,metrics/memory";
			String metrics = "";
			String cpu = "";
			String disk = "";
			String mem = "";
			
			try {
				String result = GetRestResponse(url, authStringEnc);
		        
		        metrics = JsonParsing(result, "metrics");
				
		        cpu = JsonParsing(metrics, "cpu");
				disk = JsonParsing(metrics, "disk");
				mem = JsonParsing(metrics, "memory");
				
				
				String cpu_idle = JsonParsing(cpu, "cpu_idle");
				String disk_free = JsonParsing(disk, "disk_free");
				String disk_total = JsonParsing(disk, "disk_total");
				String disk_usage_rate = String.format("%.2f", (Double.parseDouble(disk_total)-Double.parseDouble(disk_free))*100/Double.parseDouble(disk_total));
				String mem_free = JsonParsing(mem, "mem_free");
				String mem_total = JsonParsing(mem, "mem_total");
				
				ResponseModel model = new ResponseModel();
				model.setHOST_NAME(hostList.get(i));
				model.setHOST_CPU_IDLE(cpu_idle);
				model.setHOST_DISK_FREE(disk_free);
				model.setHOST_DISK_TOTAL(disk_total);
				model.setHOST_DISK_USAGE_RATE(disk_usage_rate);
				model.setHOST_MEM_FREE(mem_free);
				model.setHOST_MEM_TOTAL(mem_total);
				model.setLAST_CHECK_TIME(timenow);
				
				
				performlist.add(model);
				
			}catch(NullPointerException npe) {
				InsertPerformInfo(hostList, authStringEnc);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			TestDAO.insertHostPerformInfo(performlist);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void InsertStateInfo(List<String> hostList, String authStringEnc) {
		List<ResponseModel> statelist = new ArrayList<ResponseModel>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timenow = sdf.format(System.currentTimeMillis());
		
		for(int i=0; i<hostList.size(); i++) {
			String stateUrl = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts/" + hostList.get(i) + "?fields=Hosts/host_state,Hosts/ip,Hosts/host_status";
			
			String name = hostList.get(i);
			String host_state="";
			String host_ip="";
			String host_status="";
			
			
			
			try {
				String host_result = GetRestResponse(stateUrl, authStringEnc);
		        
		        System.out.println("host_result : " + host_result);
		        String json_hosts = JsonParsing(host_result, "Hosts");
				host_state = JsonParsing(json_hosts, "host_state");
				host_ip = JsonParsing(json_hosts, "ip");
				host_status = JsonParsing(json_hosts, "host_status");
				
				System.out.println("host name : " + name);
				System.out.println("host_state : " + host_state);
				System.out.println("host_ip : " + host_ip);
				System.out.println("host_status : " + host_status);
				
				ResponseModel r = new ResponseModel();
				r.setHOST_NAME(name);
				r.setHOST_INDEX(i+1);
				r.setHOST_IP(host_ip);
				r.setHOST_STATE_CODE(host_state);
				r.setHOST_STATUS_CODE(host_status);
				r.setLAST_CHECK_TIME(timenow);
				
				statelist.add(r);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			TestDAO.InsertHostStateInfo(statelist);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void getNodeCntState(String authStringEnc) {
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/DATANODE?fields=ServiceComponentInfo";
		String livenodeCnt = "";
		String totalCnt = "";
		String datanodeState = "";
		String sec_state = "";
		String nameState = "";
		String timenow = "";
		
		try {
			String result = GetRestResponse(requestURL, authStringEnc);
	        
	        String ServiceComponentInfo = JsonParsing(result, "ServiceComponentInfo");
			
			livenodeCnt = JsonParsing(ServiceComponentInfo, "started_count");
			totalCnt = JsonParsing(ServiceComponentInfo, "total_count");
			datanodeState = JsonParsing(ServiceComponentInfo, "state");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String sec_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/SECONDARY_NAMENODE";
		
		try {
			String result = GetRestResponse(sec_requestURL, authStringEnc);
	        
	        String serviceCompo = JsonParsing(result, "ServiceComponentInfo");
			sec_state = JsonParsing(serviceCompo, "state");
			
//			JSONArray arr = JsonArrayParsing(result, "host_components");
//			JSONObject obj = (JSONObject)arr.get(0);
//			
			/* SECONDARY NAMENODE 이름 표시할때 */
//			String hostRoles = obj.get("HostRoles").toString();
//			String sec_node_name = JsonParsing(hostRoles, "host_name");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String namenode_url = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE?fields=ServiceComponentInfo/state,host_components";
		
		try {
			String result = GetRestResponse(namenode_url, authStringEnc);
	        
	        String ComponentINFO = JsonParsing(result, "ServiceComponentInfo");
			nameState = JsonParsing(ComponentINFO, "state");
			
			JSONArray jarr = JsonArrayParsing(result, "host_components");
			JSONObject jobj = (JSONObject)jarr.get(0);
			
			String HostRoles = jobj.get("HostRoles").toString();
			String namenodeNM = JsonParsing(HostRoles, "host_name");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timenow = sdf.format(System.currentTimeMillis());
			
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
	
	public static void getNodeRoles(String authStringEnc) {
		List<ResponseModel> list = new ArrayList<ResponseModel>();
		ResponseModel model1 = new ResponseModel();
		ResponseModel model2 = new ResponseModel();
		
		//NAMENODE
		String requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/NAMENODE";
		
		try {
			String result = GetRestResponse(requestURL, authStringEnc);
	        
	        JSONArray arr= JsonArrayParsing(result, "host_components");
	        JSONObject resultobj = (JSONObject)arr.get(0);
	        
	        String hostRole = resultobj.get("HostRoles").toString();
			String componentN = JsonParsing(hostRole, "component_name");
			String hostN = JsonParsing(hostRole, "host_name");
			
			System.out.println("host name : " + hostN + ", component name : " + componentN);
			
			
			model1.setHOST_NAME(hostN);
			model1.setCOMPONENT_CODE_NM(componentN);
			
			list.add(model1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//SECONDARY_NAMENODE
		String s_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/SECONDARY_NAMENODE";
		
		try {
			String result = GetRestResponse(s_requestURL, authStringEnc);
	        
	        JSONArray s_arr = JsonArrayParsing(result, "host_components");
	        JSONObject s_resultobj = (JSONObject)s_arr.get(0);
	        
	        String hostRole_s = s_resultobj.get("HostRoles").toString();
			String componentN_s = JsonParsing(hostRole_s, "component_name");
			String hostN_s = JsonParsing(hostRole_s, "host_name");
			
			System.out.println("host name : " + hostN_s + ", component name : " + componentN_s);
			
			
			model2.setHOST_NAME(hostN_s);
			model2.setCOMPONENT_CODE_NM(componentN_s);
			
			list.add(model2);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//DataNode
		String d_requestURL = "http://50.100.100.11:8080/api/v1/clusters/idpp/services/HDFS/components/DATANODE";
		
		try {
			String result = GetRestResponse(d_requestURL, authStringEnc);
	        
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

	public static void getServerDiskInfo(String authStringEnc) throws ParseException {
		
		List<Map<String, Object>>perDisk = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> lakeMap = new HashMap<String, Object>(); 
		
		List<DiskInfoVO> disklist = new ArrayList<DiskInfoVO>();
		System.out.println("hostlist size : " + hostList.size());
		
		
		for(int k=0; k<hostList.size(); k++) {
			System.out.println("host" + k + ": " + hostList.get(k));
			String url = "http://50.100.100.11:8080/api/v1/clusters/idpp/hosts/" + hostList.get(k) + "?fields=Hosts";
			
			String result = GetRestResponse(url, authStringEnc);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timenow = sdf.format(System.currentTimeMillis());
			
			String json_hosts = JsonParsing(result, "Hosts");
			
			JSONArray disk_info = JsonArrayParsing(json_hosts, "disk_info");
			
//			List<DiskInfoVO> disklist = new ArrayList<DiskInfoVO>();
			
			System.out.println("disk_info size : " + disk_info.size());
			
			
			for(int i=0; i<disk_info.size(); i++) {
				
				JSONObject hostobj = (JSONObject)disk_info.get(i);
	        	
				String device = hostobj.get("device").toString();
				String device_used = hostobj.get("used").toString();
				String device_available = hostobj.get("available").toString();
				String device_percent = hostobj.get("percent").toString();
				String device_size = hostobj.get("size").toString();
	        	
				System.out.println("device : " + device);
				System.out.println("device_used : " + device_used);
				System.out.println("device_available : " + device_available);
				System.out.println("device_percent : " + device_percent);
				System.out.println("device_size : " + device_size);
				
				DiskInfoVO disk = new DiskInfoVO();
				disk.setSERVER_NM(hostList.get(k));
				disk.setDISK_NM(device);
				disk.setDISK_AVAILABLE(device_available);
				disk.setDISK_USED(device_used);
				disk.setDISK_PERCENT(device_percent);
				disk.setDISK_TOTAL(device_size);
				disk.setUPDATE_TIME(timenow);
				
				disklist.add(disk); //lake1의 disk 3part 리스트
				
			}
			lakeMap.put(hostList.get(k), disklist); // lake1, List(device 1,2,3) 
		}
		
		perDisk.add(lakeMap);
		TestDAO.insertLakeDeviceList(perDisk);
		
		
	}
}
