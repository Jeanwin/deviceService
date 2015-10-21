package com.vo;

import java.util.List;
/**
 * 轮询所需实体类
 * @author gly
 *
 */
public class PollingInfo {
	private String class_id;//教室innerid
	private String serverip;//服务器ip
	private String image_path;//
	private String class_filename;
	private String endtime;
	private List<RtmpInfo> round_url;
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getClass_filename() {
		return class_filename;
	}
	public void setClass_filename(String class_filename) {
		this.class_filename = class_filename;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public List<RtmpInfo> getRound_url() {
		return round_url;
	}
	public void setRound_url(List<RtmpInfo> round_url) {
		this.round_url = round_url;
	}
	
}
