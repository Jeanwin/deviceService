package com.disrec.vo;

import java.io.Serializable;

public class ClassRoomDeviceVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recordStatus;
	private String livingStatus;
	private String reSetStatus;
	private String online;
	private String schedule;
	private String diskStatus;

	public ClassRoomDeviceVo() {
		// int i=0;
		// try {
		// this.recordStatus = GetInfoService.getRecordStatus();
		// this.livingStatus = GetInfoService.getLiveStatus();
		// if (Preset.call("1") != null) {
		// this.livingStatus = "1";
		// } else {
		// this.livingStatus = "0";
		// }
		// this.diskStatus =
		// GetInfoService.execute("RecordCmd=QueryDiskStatus");
		// i++;
		// } catch (Exception e) {
		// i=0;
		// }
		// if (i== 0) {
		// this.online = "0";
		// }else{
		// this.online = "1";
		// }
	}

	public ClassRoomDeviceVo(String s1, String s2, String s3, String s4,
			String s5, String s6) {
		this.recordStatus = s1;
		this.livingStatus = s2;
		this.reSetStatus = s3;
		this.online = s4;
		this.schedule = s5;
		this.diskStatus = s6;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getLivingStatus() {
		return livingStatus;
	}

	public void setLivingStatus(String livingStatus) {
		this.livingStatus = livingStatus;
	}

	public String getReSetStatus() {
		return reSetStatus;
	}

	public void setReSetStatus(String reSetStatus) {
		this.reSetStatus = reSetStatus;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDiskStatus() {
		return diskStatus;
	}

	public void setDiskStatus(String diskStatus) {
		this.diskStatus = diskStatus;
	}

	public static void main(String[] args) {
		return;
	}
}
