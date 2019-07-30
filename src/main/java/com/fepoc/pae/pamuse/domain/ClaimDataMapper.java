package com.fepoc.pae.pamuse.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fepoc.pae.pamuse.domain.ClaimData;

public class ClaimDataMapper implements RowMapper<ClaimData>{
	@Override
	public ClaimData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ClaimData cData = new ClaimData();
		
		int tApp = rs.getInt("TOTAL_APPROVED");
		int tDen = rs.getInt("TOTAL_DENIED");
		//System.out.println("here");
		
		cData.setPlanCode(rs.getString("PLAN_CODE"));
		cData.setTotalApp(tApp);
		cData.setTotalDen(tDen);
		cData.setTotalAll(tApp+tDen);
		
		return cData;
	}
}
