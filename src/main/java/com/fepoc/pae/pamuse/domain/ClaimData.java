package com.fepoc.pae.pamuse.domain;

//data Object for App/Den claims (both tdms & mf)
public class ClaimData {
	private String planCode;
	private int totalApp;
	private int totalDen;
	private int totalAll;
	
	public String getPlanCode() {
		return planCode;
	}
	
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public int getTotalApp() {
		return totalApp;
	}

	public void setTotalApp(int totalApp) {
		this.totalApp = totalApp;
	}

	public int getTotalDen() {
		return totalDen;
	}

	public void setTotalDen(int totalDen) {
		this.totalDen = totalDen;
	}

	public int getTotalAll() {
		return totalAll;
	}

	public void setTotalAll(int totalAll) {
		this.totalAll = totalAll;
	}

}
