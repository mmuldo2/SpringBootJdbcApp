package com.fepoc.pae.pamuse.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

//procCode mapping object
public class ProcCodeMapper implements RowMapper<ProcCode> {
	@Override
	public ProcCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProcCode pCode = new ProcCode();
				
		pCode.setPlanCode(rs.getString("PLAN_CODE"));
		pCode.setTotalGen(rs.getInt("TOTAL_GEN"));
		pCode.setTotalNotGen(rs.getInt("TOTAL_NOTGEN"));
		pCode.setTotalInProg(rs.getInt("HOLD_INPROG"));
		
		return pCode;
	}
}
