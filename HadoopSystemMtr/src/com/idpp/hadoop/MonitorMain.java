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
		
        MonitorService.getNameNode(authStringEnc);  //hdfs overall ��������
        MonitorService.getHostList(authStringEnc); //�Ϲٸ� ȣ��Ʈ ��������, ��������
        MonitorService.getServerDiskInfo(authStringEnc); //���� ��ũ ��Ƽ�� �� ����
        
        
        /*db �� ���� ���� */
//        MonitorService.getNodeCntState(authStringEnc); //livenode, total node, state ����
//        MonitorService.getNodeRoles(authStringEnc); 
        
        
	}

}
