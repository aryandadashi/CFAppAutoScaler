package com.sap.hcp.autoscaler.autoscaling;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

	public static String app_name;
	public static int min_instances;	
	public static int max_instances;
	public static long cool_down_interval;  // in milliseconds - CoolDown Interval is the minimum scaling interval (2 mins)
	public static double min_cpu_threshold;	
	public static double max_cpu_threshold;
	public static String cf_api;
	public static String cf_username;
	public static String cf_password;
	
	public static void setConstantsFromProperties(){
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("autoscaler.properties");
			
			prop.load(input);

			// set the properties value
			app_name = prop.getProperty("app_name");
			min_instances = Integer.parseInt(prop.getProperty("min_instances"));
			max_instances = Integer.parseInt(prop.getProperty("max_instances"));
			cool_down_interval = Integer.parseInt(prop.getProperty("cool_down_interval"));
			min_cpu_threshold = Double.parseDouble(prop.getProperty("min_cpu_threshold"));
			max_cpu_threshold = Double.parseDouble(prop.getProperty("max_cpu_threshold"));
			cf_api = prop.getProperty("cf_api");
			cf_username = prop.getProperty("cf_username");
			cf_password = prop.getProperty("cf_password");

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
	}
	
}
