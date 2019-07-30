package com.fepoc.pae.pamuse.service;

import java.util.List;

import com.fepoc.pae.pamuse.domain.ClaimData;
import com.fepoc.pae.pamuse.domain.ProcCode;

public interface ClaimsTotalService {
	
	public List<ClaimData> retrieveTdmsClaims(String procDate); // throws ...  something Exception
	
	public List<ClaimData> retrieveMfClaims(String procDate); // same throws exception - maybe different for tdms / mf?
	
	public List<ProcCode> retrieveProcCodes(String procDate);
	
}
