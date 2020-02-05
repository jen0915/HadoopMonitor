package com.idpp.hadoop;

import java.util.Base64;

import org.json.simple.parser.ParseException;


public class MonitorMain {
	
	public static void main(String[] args) throws ParseException {
		String name = "admin";
        String password = "QKDqwer1234!@";

        String authString = name + ":" + password;
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
		
        MonitorService.getNameNode(authStringEnc);  //hdfs overall 상태정보
        MonitorService.getHostList(authStringEnc); //암바리 호스트 상태정보, 성능정보
        MonitorService.getServerDiskInfo(authStringEnc); //서버 디스크 파티션 별 정보
        
        
        /*db 에 저장 안함 */
//        MonitorService.getNodeCntState(authStringEnc); //livenode, total node, state 정보
//        MonitorService.getNodeRoles(authStringEnc); 
        
        
	}

}
