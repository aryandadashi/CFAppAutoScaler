package com.sap.hcp.autoscaler.logaggregation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sap.hcp.autoscaler.autoscaling.Constants;

public class CFLogStream {
	
	String cf_location = "/usr/local/bin/cf";   		// In Windows OS, it could be just cf
	
	public void streamingCFLogs(){
		try {
			
			String[] cf_logs = {cf_location , "logs" , Constants.app_name};
			
			Process log_stream = Runtime.getRuntime().exec(cf_logs);
			
			// Printing output of logs
            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(log_stream.getInputStream()));
            //BufferedReader stdError = new BufferedReader(new InputStreamReader(log_stream.getErrorStream()));
            String s; 
            
            if((s = stdInput.readLine()) != null){
                // Writing the CF Logs in a separate thread
                WriteLogsThread logs_write = new WriteLogsThread(stdInput);
                Thread t1 = new Thread(logs_write);
                t1.start(); 
                
                ReadLogsThread logs_read = new ReadLogsThread();
                Thread t2 = new Thread(logs_read);
                t2.start();
                
                try {
					t1.join();
					t2.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 
                // Rename Current File to Previous File
                File file1 = new File("/Users/d063995/git/Thesis-CFAutoScaler/resources/cflogsstream_curr.txt");
                File file2 = new File("/Users/d063995/git/Thesis-CFAutoScaler/resources/cflogsstream_prev.txt");
                if (file2.exists())
                	   throw new java.io.IOException("file exists");
                file1.renameTo(file2);
            }           
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
