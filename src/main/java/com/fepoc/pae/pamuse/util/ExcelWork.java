package com.fepoc.pae.pamuse.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import com.fepoc.pae.pamuse.domain.ClaimData;
import com.fepoc.pae.pamuse.domain.FinalSheetContents;
import com.fepoc.pae.pamuse.domain.ProcCode;

public class ExcelWork {
	
	static List<String> mfcList = new ArrayList<>();
	static List<String> tdmscList = new ArrayList<>();
	static List<String> pcList = new ArrayList<>();
	
	public static void createTotalSheet (List<ClaimData> totals, String db, String procDate) throws IOException {
		
		System.out.println("db type: "+db);
		
		String filename = "PAM-"+db+"("+procDate+").xls";
		
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Total Claims"+filename);
		
        HSSFRow rowhead = sheet.createRow((short)0); //print headers
        rowhead.createCell(0).setCellValue("PlanCode");
        rowhead.createCell(1).setCellValue("Approved Claims");
        rowhead.createCell(2).setCellValue("Denied Claims");
        rowhead.createCell(3).setCellValue("Total");
		
        int rowNum = 1;
        //HSSFRow row = sheet.createRow((short)rowNum);
        for(ClaimData i : totals) {
        	HSSFRow row = sheet.createRow((short)rowNum);
            row.createCell(0).setCellValue(i.getPlanCode());
            row.createCell(1).setCellValue(i.getTotalApp());
            row.createCell(2).setCellValue(i.getTotalDen());
            row.createCell(3).setCellValue(i.getTotalAll());
            rowNum++;
        }
        
		FileOutputStream out = new FileOutputStream("cached/"+filename);
		workbook.write(out);
		out.close();
		System.out.println("\t excel file generated: "+filename);
		
	}
	
	public static void createFinalSheet (List<ClaimData> mfClaimList, List<ClaimData> tdmsClaimList, List<ProcCode> tdmsPCList, String procDate) throws IOException {
		String filename = "------("+procDate+").xls";
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FinalSheet-"+filename);
		
        HSSFRow rowhead = sheet.createRow((short)0); //print headers
        rowhead.createCell(0).setCellValue("PlanName");
        rowhead.createCell(1).setCellValue("PlanCode");
        rowhead.createCell(2).setCellValue("Approved Mainframe Claims");
        rowhead.createCell(3).setCellValue("Denied Mainframe Claims");
        rowhead.createCell(4).setCellValue("Total Mainframe Claims");
        rowhead.createCell(5).setCellValue("PlanCode");
        rowhead.createCell(6).setCellValue("Approved TDMS Claims");
        rowhead.createCell(7).setCellValue("Denied TDMS Claims");
        rowhead.createCell(8).setCellValue("Total TDMS Claims");
        rowhead.createCell(9).setCellValue("PlanCode");
        rowhead.createCell(10).setCellValue("Generated ");
        rowhead.createCell(11).setCellValue("Not Generated");
        rowhead.createCell(12).setCellValue("Hold/In-Progress");
        
        FinalSheetContents fSheet = new FinalSheetContents(mfClaimList,tdmsClaimList,tdmsPCList);
        
        List<String> ordered = getPlanList(fSheet);
        
        int rowNum = 1;
        for(String plancode : ordered) {
        	
        	String planName = Plans.getPlanName(plancode).substring(0,Plans.getPlanName(plancode).length()-4);
        	HSSFRow row = sheet.createRow((short)rowNum);
            row.createCell(0).setCellValue(planName);
            
            
            if (mfcList.contains(plancode)) {//MF 
            	
            	ClaimData cData = null;
            	for (ClaimData i : mfClaimList) {
            		if(i.getPlanCode().equals(plancode)){
            			cData = i;
            		}
            	}
            	            	           	
            	row.createCell(1).setCellValue(cData.getPlanCode()); //nullpointexc for 2019-07-29
            	row.createCell(2).setCellValue(cData.getTotalApp());
            	row.createCell(3).setCellValue(cData.getTotalDen());
            	row.createCell(4).setCellValue(cData.getTotalAll());       	
            	
            } else {
            	row.createCell(1).setCellValue(" ");
            	row.createCell(2).setCellValue(" ");
            	row.createCell(3).setCellValue(" ");
            	row.createCell(4).setCellValue(" ");
            }
        	
            if (tdmscList.contains(plancode)) {//TDMS 
            	
            	ClaimData cData = null;
            	for (ClaimData i : tdmsClaimList) {
            		if(i.getPlanCode().equals(plancode)){
            			cData = i;
            		}
            	}
            	            	           	
            	row.createCell(5).setCellValue(cData.getPlanCode());
            	row.createCell(6).setCellValue(cData.getTotalApp());
            	row.createCell(7).setCellValue(cData.getTotalDen());
            	row.createCell(8).setCellValue(cData.getTotalAll());       	
            	
            } else {
            	row.createCell(5).setCellValue(" ");
            	row.createCell(6).setCellValue(" ");
            	row.createCell(7).setCellValue(" ");
            	row.createCell(8).setCellValue(" ");
            }
        	
            if (tdmscList.contains(plancode)) {//TDMS PROCCODE
            	
            	ProcCode pCode = null;
            	for (ProcCode i : tdmsPCList) {
            		if(i.getPlanCode().equals(plancode)){
            			pCode = i;
            		}
            	}
            	            	           	
            	row.createCell(9).setCellValue(pCode.getPlanCode());
            	row.createCell(10).setCellValue(pCode.getTotalGen());
            	row.createCell(11).setCellValue(pCode.getTotalNotGen());
            	row.createCell(12).setCellValue(pCode.getTotalInProg());       	
            	
            } else {
            	row.createCell(9).setCellValue(" ");
            	row.createCell(10).setCellValue(" ");
            	row.createCell(11).setCellValue(" ");
            	row.createCell(12).setCellValue(" ");
            }
        	
        	
        	rowNum++;
        }
        
        
        
        
        
        //parse all plancodes as integers and sort then numericaly
        
        //create 2d array? of [claimdata, claimdata, procCode]
        
        //write headers
        
        
		
		
		FileOutputStream out = new FileOutputStream("target/"+filename);
		workbook.write(out);
		out.close();
		System.out.println("\t excel file generated: "+filename);
	}
	
	public static List<String> getPlanList (FinalSheetContents fSheet) {
		
		List<String> pCode = new ArrayList<String>();
		
		List<ClaimData> mfcl = fSheet.getMfClaimList();
		List<ClaimData> tdmscl = fSheet.getTdmsClaimList();
		List<ProcCode> tdmspc = fSheet.getTdmsPCList();
		
		mfcList = new ArrayList<>();//clear statics
		tdmscList = new ArrayList<>();
		pcList = new ArrayList<>();
		
		for(ClaimData a : mfcl) { //very ugly code here :(
			mfcList.add(a.getPlanCode());
			if(pCode.contains(a.getPlanCode())) {
				continue;
			}
			pCode.add(a.getPlanCode());
		}
		for(ClaimData a : tdmscl) {
			tdmscList.add(a.getPlanCode());
			if(pCode.contains(a.getPlanCode())) {
				continue;
			}
			pCode.add(a.getPlanCode());
		}
		for(ProcCode a : tdmspc) {
			pcList.add(a.getPlanCode());
			if(pCode.contains(a.getPlanCode())) {
				continue;
			}
			pCode.add(a.getPlanCode());
		}
		
		Collections.sort(pCode);
		return pCode;
	}
	
}
