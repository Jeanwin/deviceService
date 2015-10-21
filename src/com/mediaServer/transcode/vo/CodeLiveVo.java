package com.mediaServer.transcode.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class CodeLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rate;
	private String framerate;
	private String resolution;
	private String samprate;
	//private List<Map<String, Object>> transcodingurl;

	private String rtmp_repeater;
	private String uid;
	private String channel;
	private String audiorate;
	private String grade;
	private String endtime;
	private String group_id;

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFramerate() {
		return framerate;
	}

	public void setFramerate(String framerate) {
		this.framerate = framerate;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSamprate() {
		return samprate;
	}

	public void setSamprate(String samprate) {
		this.samprate = samprate;
	}

//	public List<Map<String, Object>> getTranscodingurl() {
//		return transcodingurl;
//	}
//
//	public void setTranscodingurl(List<Map<String, Object>> transcodingurl) {
//		this.transcodingurl = transcodingurl;
//	}

	public String getRtmp_repeater() {
		return rtmp_repeater;
	}

	public void setRtmp_repeater(String rtmp_repeater) {
		this.rtmp_repeater = rtmp_repeater;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAudiorate() {
		return audiorate;
	}

	public void setAudiorate(String audiorate) {
		this.audiorate = audiorate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

//	@Override
//	public String toString() {
//		return "CodeLiveVo [rate=" + rate + ", framerate=" + framerate
//				+ ", resolution=" + resolution + ", samprate=" + samprate
//				+ ", transcodingurl=" + transcodingurl + ", rtmp_repeater="
//				+ rtmp_repeater + ", uid=" + uid + ", channel=" + channel
//				+ ", audiorate=" + audiorate + ", grade=" + grade
//				+ ", endtime=" + endtime + ", group_id=" + group_id + "]";
//	}
	
	
	@Override
	public String toString() {
		return "CodeLiveVo [rate=" + rate + ", framerate=" + framerate
				+ ", resolution=" + resolution + ", samprate=" + samprate
				+ ", rtmp_repeater="
				+ rtmp_repeater + ", uid=" + uid + ", channel=" + channel
				+ ", audiorate=" + audiorate + ", grade=" + grade
				+ ", endtime=" + endtime + ", group_id=" + group_id + "]";
	}

	public Map<String,Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if(this.rate!=null&&!"".equals(this.rate))
		map.put("rate", this.rate);
		if(this.framerate!=null&&!"".equals(this.framerate))
		map.put("framerate", this.framerate);
		if(this.resolution!=null&&!"".equals(this.resolution))
		map.put("resolution", this.resolution);
		if(this.samprate!=null&&!"".equals(this.samprate))
		map.put("samprate", this.samprate);
		if(this.rtmp_repeater!=null&&!"".equals(this.rtmp_repeater))
		map.put("rtmp_repeater", this.rtmp_repeater);
		if(this.uid!=null&&!"".equals(this.uid))
		map.put("uid", this.uid);
		if(this.channel!=null&&!"".equals(this.channel))
		map.put("channel", this.channel);
		if(this.audiorate!=null&&!"".equals(this.audiorate))
		map.put("audiorate", this.audiorate);
		if(this.grade!=null&&!"".equals(this.grade))
		map.put("grade", this.grade);
		if(this.endtime!=null&&!"".equals(this.endtime))
		map.put("endtime", this.endtime);
		if(this.group_id!=null&&!"".equals(this.group_id))
		map.put("group_id", this.group_id);

//		if(this.transcodingurl!=null)
//		map.put("transcodingurl", toJson(this.transcodingurl));
		return map;
	}

	public static JSONArray toJson(List<Map<String,Object>> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray ;
		
	}
}
