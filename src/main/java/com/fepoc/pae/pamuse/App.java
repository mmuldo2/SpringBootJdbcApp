package com.fepoc.pae.pamuse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.fepoc.pae.pamuse.domain.ClaimData;
import com.fepoc.pae.pamuse.domain.ProcCode;
import com.fepoc.pae.pamuse.service.ClaimsTotalService;
import com.fepoc.pae.pamuse.util.ExcelUtil;
import com.fepoc.pae.pamuse.util.ExcelWork;

@SpringBootApplication
@PropertySource("file:-----/udb.properties")
public class App implements CommandLineRunner
{		
	@Autowired
    private ClaimsTotalService claimsTotalService;
	
	private String[] pDates = new String[11];
	
	public static void main(String[] args)
	{
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		
        //String dateAhead = DateCalc(); //call method to get date 98 days ahead   
        
		DateCalc();
		
		for(String x : pDates) {
			System.out.println(x);
	
			if(checkFile(x)==true) {  //doesnt actually check yet  -  current will overwrite files
				System.out.println("\t CONTINUE!");
				continue;
			}
			//if date file found in cached logs, continue
			
			//else create the date file

			 //ClaimsTotalServiceImpl ctsi = new ClaimsTotalServiceImpl();                            // Maybe call controller instead of direct service?
        List<ClaimData> tdmsClaimList = claimsTotalService.retrieveTdmsClaims(x);
        List<ClaimData> mfClaimList = claimsTotalService.retrieveMfClaims(x);
        List<ProcCode> tdmsPCList = claimsTotalService.retrieveProcCodes(x);
        
        System.out.println("PC  List: \n code \t Gen \t NotGen \t InProg");
        for (ProcCode a : tdmsPCList) {
        	System.out.println(a.getPlanCode()+" \t"+a.getTotalGen()+" \t"+a.getTotalNotGen()+" \t"+a.getTotalInProg());
        }
        //System.out.println("/t TDMS calling excelWork...");
        
        
        System.out.println("TDMS Claim List: \n code \t App \t Den \t Total");
        for (ClaimData a : tdmsClaimList) {
        	System.out.println(a.getPlanCode()+" \t"+a.getTotalApp()+" \t"+a.getTotalDen()+" \t"+a.getTotalAll());
        }
        System.out.println("\t TDMS calling excelWork...");
        ExcelWork.createTotalSheet(tdmsClaimList, "TDMS", x);
        
        
        System.out.println("\n MF Claim List: \n code \t App \t Den \t Total");
        for (ClaimData a : mfClaimList) {
        	System.out.println(a.getPlanCode()+" \t"+a.getTotalApp()+" \t"+a.getTotalDen()+" \t"+a.getTotalAll());
        }
        System.out.println("\t MF calling excelWork...");
        ExcelWork.createTotalSheet(mfClaimList, "MF", x);
			
        
        //create final spreadsheeet with combined plancode
        
        ExcelWork.createFinalSheet(mfClaimList, tdmsClaimList, tdmsPCList, x);
        
		}//end of day loop
		
		
		
		
		
        
    }
    	
    public void DateCalc() {    	// calculate date 98 days from today    //MOVE TO UTILS??
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 98); // Adds 98 days       
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //format date
        for(int i=0; i<pDates.length; i++) { //loop through the array for 6 days, continue if find file,  if doesnt find file call db methods and save contents to file with the filename
        	        	
        	pDates[i] = simpleDateFormat.format(c.getTime());
        	
        	c.add(Calendar.DATE,  -1);
        }
        
        //System.out.println("\n   procDate: "+date+"\n");
        
        //return date;
    }
    
    public boolean checkFile(String procDate) {//check if excel sheet exists - return true/false
    	File dateFile;
    	dateFile = new File("target/-----("+procDate+")",".xlsx");
		
		boolean exists = dateFile.exists();
		System.out.println(" exists: "+exists);
		return exists;
    	
    }
}

