package com.sap.hcp.autoscaler.logaggregation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLogsThread implements Runnable{

	BufferedReader stdInput;
	
	public WriteLogsThread(BufferedReader input){
		stdInput = input;
	}
	
	@Override
	public void run() {
		File f = null;
		BufferedWriter writer = null;
		try{
			// 	TODO Auto-generated method stub
			//	Read command standard output 
			String fileName = "/Users/d063995/git/Thesis-CFAutoScaler/resources/cflogsstream_curr.txt";
			f = new File(fileName);
			if(!f.exists()) { 
				f.createNewFile();
			}			
			
			writer = new BufferedWriter(new FileWriter(fileName));	
			long startTime = System.currentTimeMillis(); //fetch starting time
			while( false || (System.currentTimeMillis()-startTime) < 10000 )
			{
			    // do something
				 writer.append(stdInput.readLine());
				 writer.newLine();
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			   try {				
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	

}