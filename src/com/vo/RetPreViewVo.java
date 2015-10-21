package com.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zfc
 * 
 *         http://192.168.12.47:10006/recording/cmd?RecordCmd=RtspPreview
 */
public class RetPreViewVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<RetPreView2Vo> result;
	private String info;

	public List<RetPreView2Vo> getResult() {
		return result;
	}

	public void setResult(List<RetPreView2Vo> result) {
		this.result = result;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
