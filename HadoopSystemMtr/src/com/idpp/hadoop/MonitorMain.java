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
	
	public static void main(String[] args) throws ParseException {
		MonitorMain main = new MonitorMain();
		String name = "admin";
        String password = "QKDqwer1234!@";

        String authString = name + ":" + password;
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
		
        //MonitorService.getNameNode(authStringEnc);
        MonitorService.getHostList(authStringEnc); //ok/�Ϲٸ� ȣ��Ʈ ��������, ȣ��Ʈ ��������
        MonitorService.getServerDiskInfo(authStringEnc);
        //MonitorService.getNodeCntState(authStringEnc); 
        //MonitorService.getNodeRoles(authStringEnc); 
        
        
	}

}
