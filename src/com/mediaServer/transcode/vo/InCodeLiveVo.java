package com.mediaServer.transcode.vo;

import java.io.Serializable;
import java.util.List;

public class InCodeLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rate;
	private String framerate;
	private String resolution;
	private String samprate;
	private List<InCodeLive2Vo> transcodingurl;

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

	public List<InCodeLive2Vo> getTranscodingurl() {
		return transcodingurl;
	}

	public void setTranscodingurl(List<InCodeLive2Vo> transcodingurl) {
		this.transcodingurl = transcodingurl;
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

	@Override
	public String toString() {
		return "CopyOfCodeLiveVo [rate=" + rate + ", framerate=" + framerate
				+ ", resolution=" + resolution + ", samprate=" + samprate
				+ ", transcodingurl=" + transcodingurl + ", channel=" + channel
				+ ", audiorate=" + audiorate + ", grade=" + grade
				+ ", endtime=" + endtime + ", group_id=" + group_id + "]";
	}

}
