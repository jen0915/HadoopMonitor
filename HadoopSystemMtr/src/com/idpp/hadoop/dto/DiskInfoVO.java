package com.idpp.hadoop.dto;

public class DiskInfoVO {

	private String SERVER_NM;
	private String DISK_NM;
	private String DISK_TOTAL;
	private String DISK_AVAILABLE;
	private String DISK_USED;
	private String DISK_PERCENT;
	private String UPDATE_TIME;
	
	public String getSERVER_NM() {
		return SERVER_NM;
	}
	public void setSERVER_NM(String sERVER_NM) {
		SERVER_NM = sERVER_NM;
	}
	public String getDISK_NM() {
		return DISK_NM;
	}
	public void setDISK_NM(String dISK_NM) {
		DISK_NM = dISK_NM;
	}
	public String getDISK_AVAILABLE() {
		return DISK_AVAILABLE;
	}
	public void setDISK_AVAILABLE(String dISK_AVAILABLE) {
		DISK_AVAILABLE = dISK_AVAILABLE;
	}
	public String getDISK_USED() {
		return DISK_USED;
	}
	public void setDISK_USED(String dISK_USED) {
		DISK_USED = dISK_USED;
	}
	public String getDISK_PERCENT() {
		return DISK_PERCENT;
	}
	public void setDISK_PERCENT(String dISK_PERCENT) {
		DISK_PERCENT = dISK_PERCENT;
	}
	public String getDISK_TOTAL() {
		return DISK_TOTAL;
	}
	public void setDISK_TOTAL(String dISK_TOTAL) {
		DISK_TOTAL = dISK_TOTAL;
	}
	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	
	
}
