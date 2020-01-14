package com.idpp.hadoop.dto;


public class ResponseModel {

	private Integer HOST_INDEX;
	private String HOST_NAME;
	private String HOST_IP;
	private String HOST_STATE_CODE;
	private String HOST_STATUS_CODE;
	private String HOST_CPU_IDLE;
	private String HOST_DISK_FREE;
	private String HOST_DISK_TOTAL;
	private String HOST_DISK_USAGE_RATE;
	private String HOST_MEM_FREE;
	private String HOST_MEM_TOTAL;
	private String LAST_CHECK_TIME;

	
	private String NAMENODE_STATUS;
	private String SNAMENODE_STATUS;
	private String LIVE_DATANODE_CNT;
	private String TOTAL_DATANODE_CNT;
	private String DATANODE_STATE;
	
	private String HOST_COMPONENT_CODE;
	
	private String COMPONENT_CODE_NM;
	
	private String CPU_IDLE;
	private String NAMENODE_HEAP_USED;
	private String NAMENODE_HEAP_COMMITTED;
	private String DFS_DISK_TOTAL;
	private String DFS_DISK_USED;
	private String DFS_DISK_USED_PERCENT;
	private String DFS_DISK_REMAINING;
	private String DFS_DISK_REMAINING_PERCENT;
	
	
	
	
	public String getHOST_STATE_CODE() {
		return HOST_STATE_CODE;
	}
	public void setHOST_STATE_CODE(String hOST_STATE_CODE) {
		HOST_STATE_CODE = hOST_STATE_CODE;
	}
	public String getHOST_STATUS_CODE() {
		return HOST_STATUS_CODE;
	}
	public void setHOST_STATUS_CODE(String hOST_STATUS_CODE) {
		HOST_STATUS_CODE = hOST_STATUS_CODE;
	}
	public String getHOST_CPU_IDLE() {
		return HOST_CPU_IDLE;
	}
	public void setHOST_CPU_IDLE(String hOST_CPU_IDLE) {
		HOST_CPU_IDLE = hOST_CPU_IDLE;
	}
	public String getHOST_DISK_FREE() {
		return HOST_DISK_FREE;
	}
	public void setHOST_DISK_FREE(String hOST_DISK_FREE) {
		HOST_DISK_FREE = hOST_DISK_FREE;
	}
	public String getHOST_DISK_TOTAL() {
		return HOST_DISK_TOTAL;
	}
	public void setHOST_DISK_TOTAL(String hOST_DISK_TOTAL) {
		HOST_DISK_TOTAL = hOST_DISK_TOTAL;
	}
	public String getHOST_DISK_USAGE_RATE() {
		return HOST_DISK_USAGE_RATE;
	}
	public void setHOST_DISK_USAGE_RATE(String hOST_DISK_USAGE_RATE) {
		HOST_DISK_USAGE_RATE = hOST_DISK_USAGE_RATE;
	}
	public String getHOST_MEM_FREE() {
		return HOST_MEM_FREE;
	}
	public void setHOST_MEM_FREE(String hOST_MEM_FREE) {
		HOST_MEM_FREE = hOST_MEM_FREE;
	}
	public String getHOST_MEM_TOTAL() {
		return HOST_MEM_TOTAL;
	}
	public void setHOST_MEM_TOTAL(String hOST_MEM_TOTAL) {
		HOST_MEM_TOTAL = hOST_MEM_TOTAL;
	}
//	public Date getLAST_CHECK_TIME() {
//		return LAST_CHECK_TIME;
//	}
//	public void setLAST_CHECK_TIME(Date lAST_CHECK_TIME) {
//		LAST_CHECK_TIME = lAST_CHECK_TIME;
//	}
	
	public String getHOST_NAME() {
		return HOST_NAME;
	}
	public String getLAST_CHECK_TIME() {
		return LAST_CHECK_TIME;
	}
	public void setLAST_CHECK_TIME(String lAST_CHECK_TIME) {
		LAST_CHECK_TIME = lAST_CHECK_TIME;
	}
	public String getHOST_IP() {
		return HOST_IP;
	}
	public void setHOST_IP(String hOST_IP) {
		HOST_IP = hOST_IP;
	}
	public Integer getHOST_INDEX() {
		return HOST_INDEX;
	}
	public void setHOST_INDEX(Integer hOST_INDEX) {
		HOST_INDEX = hOST_INDEX;
	}
	public void setHOST_NAME(String hOST_NAME) {
		HOST_NAME = hOST_NAME;
	}
	public String getNAMENODE_STATUS() {
		return NAMENODE_STATUS;
	}
	public void setNAMENODE_STATUS(String nAMENODE_STATUS) {
		NAMENODE_STATUS = nAMENODE_STATUS;
	}
	public String getSNAMENODE_STATUS() {
		return SNAMENODE_STATUS;
	}
	public void setSNAMENODE_STATUS(String sNAMENODE_STATUS) {
		SNAMENODE_STATUS = sNAMENODE_STATUS;
	}
	public String getLIVE_DATANODE_CNT() {
		return LIVE_DATANODE_CNT;
	}
	public void setLIVE_DATANODE_CNT(String lIVE_DATANODE_CNT) {
		LIVE_DATANODE_CNT = lIVE_DATANODE_CNT;
	}
	public String getTOTAL_DATANODE_CNT() {
		return TOTAL_DATANODE_CNT;
	}
	public void setTOTAL_DATANODE_CNT(String tOTAL_DATANODE_CNT) {
		TOTAL_DATANODE_CNT = tOTAL_DATANODE_CNT;
	}
	public String getCPU_IDLE() {
		return CPU_IDLE;
	}
	public void setCPU_IDLE(String cPU_IDLE) {
		CPU_IDLE = cPU_IDLE;
	}
	public String getDFS_DISK_TOTAL() {
		return DFS_DISK_TOTAL;
	}
	public void setDFS_DISK_TOTAL(String dFS_DISK_TOTAL) {
		DFS_DISK_TOTAL = dFS_DISK_TOTAL;
	}
	public String getDFS_DISK_USED() {
		return DFS_DISK_USED;
	}
	public void setDFS_DISK_USED(String dFS_DISK_USED) {
		DFS_DISK_USED = dFS_DISK_USED;
	}
	public String getDFS_DISK_USED_PERCENT() {
		return DFS_DISK_USED_PERCENT;
	}
	public void setDFS_DISK_USED_PERCENT(String dFS_DISK_USED_PERCENT) {
		DFS_DISK_USED_PERCENT = dFS_DISK_USED_PERCENT;
	}
	public String getDFS_DISK_REMAINING() {
		return DFS_DISK_REMAINING;
	}
	public void setDFS_DISK_REMAINING(String dFS_DISK_REMAINING) {
		DFS_DISK_REMAINING = dFS_DISK_REMAINING;
	}
	public String getDFS_DISK_REMAINING_PERCENT() {
		return DFS_DISK_REMAINING_PERCENT;
	}
	public void setDFS_DISK_REMAINING_PERCENT(String dFS_DISK_REMAINING_PERCENT) {
		DFS_DISK_REMAINING_PERCENT = dFS_DISK_REMAINING_PERCENT;
	}
	public String getNAMENODE_HEAP_USED() {
		return NAMENODE_HEAP_USED;
	}
	public void setNAMENODE_HEAP_USED(String nAMENODE_HEAP_USED) {
		NAMENODE_HEAP_USED = nAMENODE_HEAP_USED;
	}
	public String getNAMENODE_HEAP_COMMITTED() {
		return NAMENODE_HEAP_COMMITTED;
	}
	public void setNAMENODE_HEAP_COMMITTED(String nAMENODE_HEAP_COMMITTED) {
		NAMENODE_HEAP_COMMITTED = nAMENODE_HEAP_COMMITTED;
	}
	public String getDATANODE_STATE() {
		return DATANODE_STATE;
	}
	public void setDATANODE_STATE(String dATANODE_STATE) {
		DATANODE_STATE = dATANODE_STATE;
	}
	public String getHOST_COMPONENT_CODE() {
		return HOST_COMPONENT_CODE;
	}
	public void setHOST_COMPONENT_CODE(String hOST_COMPONENT_CODE) {
		HOST_COMPONENT_CODE = hOST_COMPONENT_CODE;
	}
	public String getCOMPONENT_CODE_NM() {
		return COMPONENT_CODE_NM;
	}
	public void setCOMPONENT_CODE_NM(String cOMPONENT_CODE_NM) {
		COMPONENT_CODE_NM = cOMPONENT_CODE_NM;
	}
	
	
	
}
