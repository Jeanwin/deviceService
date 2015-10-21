package com.vo;

import java.io.Serializable;

public class CentralCmdsVo implements Serializable {

	private static final long serialVersionUID = 1854613215485L;
	private String CentralPowerCmd;          //中控电源控制命令
	private String ProjectorCmd;             //投影机控制命令
	private String TeacherPcCmd;             //老师pc控制命令
	private String LockCmd;                  //电控锁控制命令
	private String MuteMicCmd;               //音响控制命令
	private String PlaneCmd;                 //面板控制命令
	private String IPPhoneCmd;               //ip电话控制命令
	private String ScreenCmd;                //幕布控制命令
	private String mac;
	
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getCentralPowerCmd() {
		return CentralPowerCmd;
	}
	public void setCentralPowerCmd(String centralPowerCmd) {
		CentralPowerCmd = centralPowerCmd;
	}
	public String getProjectorCmd() {
		return ProjectorCmd;
	}
	public void setProjectorCmd(String projectorCmd) {
		ProjectorCmd = projectorCmd;
	}
	public String getTeacherPcCmd() {
		return TeacherPcCmd;
	}
	public void setTeacherPcCmd(String teacherPcCmd) {
		TeacherPcCmd = teacherPcCmd;
	}
	public String getLockCmd() {
		return LockCmd;
	}
	public void setLockCmd(String lockCmd) {
		LockCmd = lockCmd;
	}
	public String getMuteMicCmd() {
		return MuteMicCmd;
	}
	public void setMuteMicCmd(String muteMicCmd) {
		MuteMicCmd = muteMicCmd;
	}
	public String getPlaneCmd() {
		return PlaneCmd;
	}
	public void setPlaneCmd(String planeCmd) {
		PlaneCmd = planeCmd;
	}
	public String getIPPhoneCmd() {
		return IPPhoneCmd;
	}
	public void setIPPhoneCmd(String iPPhoneCmd) {
		IPPhoneCmd = iPPhoneCmd;
	}
	public String getScreenCmd() {
		return ScreenCmd;
	}
	public void setScreenCmd(String screenCmd) {
		ScreenCmd = screenCmd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CentralCmdsVo [CentralPowerCmd=" + CentralPowerCmd
				+ ", ProjectorCmd=" + ProjectorCmd + ", TeacherPcCmd="
				+ TeacherPcCmd + ", LockCmd=" + LockCmd + ", MuteMicCmd="
				+ MuteMicCmd + ", PlaneCmd=" + PlaneCmd + ", IPPhoneCmd="
				+ IPPhoneCmd + ", ScreenCmd=" + ScreenCmd + "]";
	}
	
	public String BattoZkdm() {
		String result="";
		if (!"".equals(CentralPowerCmd))
			result=CentralPowerCmd+"|";
		if (!"".equals(ProjectorCmd))
			result=ProjectorCmd+"|";
		if (!"".equals(TeacherPcCmd))
			result=TeacherPcCmd+"|";
		if (!"".equals(LockCmd))
			result=LockCmd+"|";
		if (!"".equals(MuteMicCmd))
			result=MuteMicCmd+"|";
		if (!"".equals(PlaneCmd))
			result=PlaneCmd+"|";
		if (!"".equals(IPPhoneCmd))
			result=IPPhoneCmd+"|";
		if (!"".equals(ScreenCmd))
			result=ScreenCmd+"|";	
		if (!"".equals(result))
			result=result.substring(0, result.length()-1);
		return result;
		
	}
	
}
