package com.sap.hcp.autoscaler.logaggregation;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sap.hcp.autoscaler.MonitoringService;

public class ReadLogsThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		readCFLogs();
	}
	
	public void readCFLogs(){
		String line = null;
		Charset ENCODING = StandardCharsets.UTF_8;
		String fileName = "/Users/d063995/git/Thesis-CFAutoScaler/resources/cflogsstream_prev.txt";		
		File f = new File(fileName);
		if (!(f.exists()))
			return;
		List<Double> response_times = new ArrayList<Double>();
		Path path = Paths.get(fileName);
		try(BufferedReader reader = Files.newBufferedReader(path, ENCODING)){
			while((line = reader.readLine()) != null){				
				if(!(line.equals("")) && line.length() > 32){
					if(line.substring(29, 32).equals("RTR")){
		            	int index = line.indexOf("response_time");
		            	if(index == -1) continue;
		            	try {
		            		String temp_string = line.substring(index+14, index+22);
		            		String temp_number_only = temp_string.replaceAll("[^0-9\\.]+", "");		// Convert string to number only string
							Double rt = Double.parseDouble(temp_number_only);
							response_times.add(rt);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}					
				} 
			}
			
	         // Calculating average Response time of all the requests
			 if(!(response_times.isEmpty())){
				 MonitoringService.modelDataObject.setRequestsPerSecond(((double)response_times.size()) / 10.0);
		         double rt_avg = (response_times.parallelStream().mapToDouble(e -> e.doubleValue()).average().getAsDouble())*1000;		// In milliseconds
		         System.out.println(rt_avg);	            	                     
		         MonitoringService.modelDataObject.setAvgResponseTime(rt_avg);	 														// Set the Average Response Times  
			 }
			 
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			if(f.exists())
				f.delete();
		}
	}

}
