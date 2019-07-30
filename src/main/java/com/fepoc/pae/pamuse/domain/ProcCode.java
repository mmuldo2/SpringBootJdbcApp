package com.fepoc.pae.pamuse.domain;

//data Object for Process Codes
public class ProcCode {
	private String planCode;
	private int totalGen;
	private int totalNotGen;
	private int totalInProg;
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public int getTotalGen() {
		return totalGen;
	}
	public void setTotalGen(int totalGen) {
		this.totalGen = totalGen;
	}
	public int getTotalNotGen() {
		return totalNotGen;
	}
	public void setTotalNotGen(int totalNotGen) {
		this.totalNotGen = totalNotGen;
	}
	public int getTotalInProg() {
		return totalInProg;
	}
	public void setTotalInProg(int totalInProg) {
		this.totalInProg = totalInProg;
	}
}
