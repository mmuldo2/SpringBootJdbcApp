package com.fepoc.pae.pamuse.domain;

import java.util.List;

public class FinalSheetContents {
	private List<ClaimData> mfClaimList;
	private List<ClaimData> tdmsClaimList;
	private List<ProcCode> tdmsPCList;
	public FinalSheetContents(List<ClaimData> mfClaimList,	List<ClaimData> tdmsClaimList, List<ProcCode> tdmsPCList) {
		// TODO Auto-generated constructor stub
		this.mfClaimList = mfClaimList;
		this.tdmsClaimList = tdmsClaimList;
		this.tdmsPCList = tdmsPCList;
	}
	public List<ClaimData> getMfClaimList() {
		return mfClaimList;
	}
	public void setMfClaimList(List<ClaimData> mfClaimList) {
		this.mfClaimList = mfClaimList;
	}
	public List<ClaimData> getTdmsClaimList() {
		return tdmsClaimList;
	}
	public void setTdmsClaimList(List<ClaimData> tdmsClaimList) {
		this.tdmsClaimList = tdmsClaimList;
	}
	public List<ProcCode> getTdmsPCList() {
		return tdmsPCList;
	}
	public void setTdmsPCList(List<ProcCode> tdmsPCList) {
		this.tdmsPCList = tdmsPCList;
	}
}
