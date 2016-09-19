package com.sap.hcp.autoscaler;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sap.hcp.autoscaler.autoscaling.CFCalls;
import com.sap.hcp.autoscaler.autoscaling.Constants;
import com.sap.hcp.autoscaler.autoscaling.ScalingService;
import com.sap.hcp.autoscaler.data.DatabaseOperations;
import com.sap.hcp.autoscaler.data.ModelDataObject;
import com.sap.hcp.autoscaler.logaggregation.CFLogStream;

public class MonitoringService {
	
	static public ModelDataObject modelDataObject;
	public static int current_instances;	
	static CFCalls cf = new CFCalls();
	static DatabaseOperations db = new DatabaseOperations();
	public static List<Double> cpu = new ArrayList<Double>();

	static void monitor() {	    
	    	initializeModelDataObject();
			cf.cfLogin(Constants.cf_api, Constants.cf_username, Constants.cf_password);
			cf.getOAuthToken();
			cf.pingCFcpu(Constants.app_name);			
			CFLogStream cflog = new CFLogStream();
			cflog.streamingCFLogs();			
			cf.cfLogout();
			db.insertIntoModelTable();	    
	}
	
	public static void main(String[] args){
		Constants.setConstantsFromProperties();	
		ScalingService.invokeAutoScaler();
		while(true){
			monitor();
		}
	}
	
	static void initializeModelDataObject(){
		modelDataObject = new ModelDataObject();
		MonitoringService.modelDataObject.setTimeStamp(getCurrentUnixTS());
	}
	
	public static int getCurrentUnixTS(){
		
		try {
			 Date date = new Date();			   
			 long unixTime = (long) date.getTime()/1000;
			 int time = toIntExact(unixTime);
			 System.out.println(unixTime); 		
			 System.out.println(time);
			 return time;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}
}
