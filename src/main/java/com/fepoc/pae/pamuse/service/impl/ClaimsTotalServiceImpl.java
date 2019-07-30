package com.fepoc.pae.pamuse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fepoc.pae.pamuse.domain.ClaimData;
import com.fepoc.pae.pamuse.domain.ClaimDataMapper;
import com.fepoc.pae.pamuse.domain.ProcCode;
import com.fepoc.pae.pamuse.domain.ProcCodeMapper;
import com.fepoc.pae.pamuse.service.ClaimsTotalService;

@Service
public class ClaimsTotalServiceImpl implements ClaimsTotalService {

	@Autowired
	@Qualifier("tdmsJdbcTemplate")
	private JdbcTemplate tdmsJdbcTemplate;
	
	@Autowired
	@Qualifier("MFJdbcTemplate")
	private JdbcTemplate MFJdbcTemplate;
	
	@Override
	public List<ClaimData> retrieveTdmsClaims(String procDate) {
		String query = "select ----- as -----, sum(CASE ----- WHEN '9' THEN 0 ELSE 1 END) as -----, sum(CASE ----- WHEN '9' THEN 1 ELSE 0 END) as ----- from -----.----- where ----- = '"+procDate+"' group by -----";
		List<ClaimData> tdmsClaimList = new ArrayList<ClaimData>();
		//tdmsClaimList = tdmsJdbcTemplate.query(query, new ClaimDataMapper());
		//List<ClaimData> tdmsClaimList = tdmsJdbcTemplate.query(query, new ClaimDataMapper());
		if (tdmsJdbcTemplate == null) {
			System.out.println("jdbctemplate is null");

		}
		try {
			tdmsClaimList = tdmsJdbcTemplate.query(query, new ClaimDataMapper());
			if(tdmsClaimList == null || tdmsClaimList.size() == 0)
			{
				System.out.println("TDMS Info does not exist");   //Insert Log here
			}
		} catch (NullPointerException e){
			System.out.println("TDMS Uh Oh... have fun with this exception...\n");
			e.printStackTrace();
			//Insert Log here
		}
		System.out.println("TDMS procDate: "+procDate);
		return tdmsClaimList;
	}

	@Override
	public List<ClaimData> retrieveMfClaims(String procDate) {  //new MF query
		String query = "select ----- as -----, sum(CASE WHEN ----- != '9' THEN 1 ELSE 0 END) as -----, sum(CASE WHEN ----- = '9' THEN 1 ELSE 0 END) as ----- FROM -----.----- where ----- = '"+procDate+"' and ----- = 'APP' group by -----";
		List<ClaimData> mfClaimList = new ArrayList<ClaimData>();	
		try {
			mfClaimList = MFJdbcTemplate.query(query, new ClaimDataMapper());
			if(mfClaimList == null || mfClaimList.size() == 0)
			{
				System.out.println("MF Info does not exist");   //Insert Log here
			}
		} catch (Exception e){
			System.out.println("MF Uh Oh... have fun with this exception...\n"+e);     //Insert Log here
		}
		System.out.println("MF procDate: "+procDate);
		return mfClaimList;
	}

	@Override
	public List<ProcCode> retrieveProcCodes(String procDate) {
		String query = "select ----- as -----, sum(CASE WHEN ----- in ('-','-','-','-','-') THEN 1 ELSE 0 END) as -----, sum(CASE WHEN ----- in ('-') THEN 1 ELSE 0 END) as -----, sum(CASE WHEN ----- in ('-','-','-','-') THEN 1 ELSE 0 END) as ----- from -----.----- where ----- = '"+procDate+"' group by -----";
		List<ProcCode> pCodeList = new ArrayList<ProcCode>();
		try {
			pCodeList = tdmsJdbcTemplate.query(query, new ProcCodeMapper());
			if(pCodeList == null || pCodeList.size() == 0)
			{
				System.out.println("TDMS Info does not exist");   //Insert Log here
			}
		} catch (NullPointerException e){
			System.out.println("TDMS Uh Oh... have fun with this exception...\n");
			e.printStackTrace();
			//Insert Log here
		}
		System.out.println("PC procDate: "+procDate);
		return pCodeList;
	
	}
	
}
