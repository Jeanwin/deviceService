package com.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeviceAlarm {
	
    private String id;

    private String output;

    private String state;//状态0：高  1：低

    private String messageAlarm;//消息报警 0:启用  1:停用

    private String clues;//提示语

    private String bell;//铃音对应 数字对应的地址

    private String emailUse;//邮箱报警使用 0：启用  1:停用

    private String sms;//短信通知 0：启用  1:停用

    private Date createdate;

    private String createuser;

    private Date modifydate;

    private String modifyuser;
    
    private List<Map<String, Object>> areas;
    
    private String _loginname;
    
    private String areaId;
    
    private String outputName;
    
    private String mac;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output == null ? null : output.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getMessageAlarm() {
        return messageAlarm;
    }

    public void setMessageAlarm(String messageAlarm) {
        this.messageAlarm = messageAlarm == null ? null : messageAlarm.trim();
    }

    public String getClues() {
        return clues;
    }

    public void setClues(String clues) {
        this.clues = clues == null ? null : clues.trim();
    }

    public String getBell() {
        return bell;
    }

    public void setBell(String bell) {
        this.bell = bell == null ? null : bell.trim();
    }

    public String getEmailUse() {
        return emailUse;
    }

    public void setEmailUse(String emailUse) {
        this.emailUse = emailUse == null ? null : emailUse.trim();
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms == null ? null : sms.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser == null ? null : modifyuser.trim();
    }

	public List<Map<String, Object>> getAreas() {
		return areas;
	}

	public void setAreas(List<Map<String, Object>> areas) {
		this.areas = areas;
	}

	public String get_loginname() {
		return _loginname;
	}

	public void set_loginname(String _loginname) {
		this._loginname = _loginname;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "DeviceAlarm [id=" + id + ", output=" + output + ", state="
				+ state + ", messageAlarm=" + messageAlarm + ", clues=" + clues
				+ ", bell=" + bell + ", emailUse=" + emailUse + ", sms=" + sms
				+ ", createdate=" + createdate + ", createuser=" + createuser
				+ ", modifydate=" + modifydate + ", modifyuser=" + modifyuser
				+ ", areas=" + areas + ", _loginname=" + _loginname
				+ ", areaId=" + areaId + ", outputName=" + outputName
				+ ", mac=" + mac + "]";
	}

	
}