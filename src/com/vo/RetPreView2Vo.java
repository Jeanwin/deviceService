package com.vo;

import java.io.Serializable;

/**
 * @author zfc
 * 
 *         http://192.168.12.47:10006/recording/cmd?RecordCmd=RtspPreview
 */
public class RetPreView2Vo  implements Serializable {

	private static final long serialVersionUID = 1L;
	private String movie;
	private String resource1;
	private String resource2;
	private String resource3;
	private String resource4;
	private String resource5;
	private String resource6;

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getResource1() {
		return resource1;
	}

	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}

	public String getResource2() {
		return resource2;
	}

	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}

	public String getResource3() {
		return resource3;
	}

	public void setResource3(String resource3) {
		this.resource3 = resource3;
	}

	public String getResource4() {
		return resource4;
	}

	public void setResource4(String resource4) {
		this.resource4 = resource4;
	}

	public String getResource5() {
		return resource5;
	}

	public void setResource5(String resource5) {
		this.resource5 = resource5;
	}

	public String getResource6() {
		return resource6;
	}

	public void setResource6(String resource6) {
		this.resource6 = resource6;
	}

}
