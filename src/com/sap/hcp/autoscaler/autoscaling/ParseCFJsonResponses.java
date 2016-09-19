package com.sap.hcp.autoscaler.autoscaling;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sap.hcp.autoscaler.MonitoringService;

public class ParseCFJsonResponses {

	public static double parseJsonGetCPUAvg(String json){
		
		List<Double> cpu_list = new ArrayList<Double>();
		List<Double> memory_list = new ArrayList<Double>();
		List<Double> disk_list = new ArrayList<Double>();
		
		 try {

	            JsonParser jsonParser = new JsonParser();
	            JsonObject jo = (JsonObject)jsonParser.parse(json);
	            int runnin_instance_count = 0;
	            for(int i = 0; i< Constants.max_instances; i++){
	            	String instance_str = Integer.toString(i);
	            	if (!(jo.has(instance_str))) break;

	            	String instance_state = jo.get(instance_str).getAsJsonObject().get("state").getAsString();
	            	
	            	if(instance_state.equals("RUNNING")){
	            		runnin_instance_count++;
	            		// Getting CPU Utilization%
		            	cpu_list.add(jo.get(instance_str).getAsJsonObject().get("stats").getAsJsonObject().get("usage").getAsJsonObject().get("cpu").getAsDouble());
		            	
		            	// Getting Memory Usage as Percentage
		            	double memory_percent = ((double)jo.get(instance_str).getAsJsonObject().get("stats").getAsJsonObject().get("usage").getAsJsonObject().get("mem").getAsLong() 
		            	/ jo.get(instance_str).getAsJsonObject().get("stats").getAsJsonObject().get("mem_quota").getAsLong());
		            	memory_list.add(memory_percent);
		            	
		            	// Getting Disk Usage as Percentage
		            	double disk_percent = ((double)jo.get(instance_str).getAsJsonObject().get("stats").getAsJsonObject().get("usage").getAsJsonObject().get("disk").getAsLong() 
		            	/ jo.get(instance_str).getAsJsonObject().get("stats").getAsJsonObject().get("disk_quota").getAsLong());
		            	disk_list.add(disk_percent);
	            	}
	            }
	            
	            // Calculating average of CPU Utilization of all instances
	            double cpu_avg = cpu_list.parallelStream().mapToDouble(e -> e.doubleValue()).average().getAsDouble()*100;
	            System.out.println(cpu_avg);
	            
	         // Calculating average of Memory usage of all instances
	            double mem_avg = memory_list.parallelStream().mapToDouble(e -> e.doubleValue()).average().getAsDouble()*100;
	            System.out.println(mem_avg);
	            
	         // Calculating average of Disk usage of all instances
	            double disk_avg = disk_list.parallelStream().mapToDouble(e -> e.doubleValue()).average().getAsDouble()*100;
	            System.out.println(disk_avg);
	            
	            MonitoringService.modelDataObject.setCpu_percent(cpu_avg); 		// Set the Average CPU Utilization %
	            MonitoringService.modelDataObject.setMemory_percent(mem_avg); 	// Set the Average Memory Usage
	            MonitoringService.modelDataObject.setDisk_percent(disk_avg); 	// Set the Average Disk Usage
	            MonitoringService.modelDataObject.setNoInstances(runnin_instance_count);	// Set the number of Instances
	            
	            return cpu_avg;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return 0.0;
	        }		
	}
	
	public static int parseJsonGetCurrentInstances(String json){
		
		 try {
	            JsonParser jsonParser = new JsonParser();
	            JsonObject jo = (JsonObject)jsonParser.parse(json);
	            int curr_inst = jo.get("running_instances").getAsInt();
	            System.out.println(curr_inst);
	            return curr_inst;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return 0;
	        }		
	}	
	
}
