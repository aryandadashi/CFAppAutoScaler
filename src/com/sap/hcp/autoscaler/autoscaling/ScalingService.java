package com.sap.hcp.autoscaler.autoscaling;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.sap.hcp.autoscaler.MonitoringService;

public class ScalingService {
	
	static Timer timer_scale = new Timer ();
	static TimerTask autoscaler = new TimerTask(){
		@Override
		public void run(){
			calculateAvgCPU();
		}
	};
	
	public static void invokeAutoScaler(){
		timer_scale.schedule(autoscaler, 60000, Constants.cool_down_interval);
	}
	
	public static void calculateAvgCPU(){
		
		synchronized(MonitoringService.cpu){
			if(MonitoringService.cpu.isEmpty()){
				return;
			}
			else {
				System.out.println("CPU List count:" + MonitoringService.cpu.size());
				double cpu_avg = MonitoringService.cpu.parallelStream().mapToDouble(e -> e.doubleValue()).average().getAsDouble();

				CFCalls cf_call = new CFCalls();
				if(cpu_avg > Constants.max_cpu_threshold){
					cf_call.cfLogin(Constants.cf_api, Constants.cf_username, Constants.cf_password);
					cf_call.getOAuthToken();
					cf_call.cfHorizontalScaling(cf_call.getCurrentRunningInstances()+1, Constants.app_name);
					cf_call.cfLogout();
				}
				else if (cpu_avg < Constants.min_cpu_threshold){
					cf_call.cfLogin(Constants.cf_api, Constants.cf_username, Constants.cf_password);
					cf_call.getOAuthToken();
					cf_call.cfHorizontalScaling(cf_call.getCurrentRunningInstances()-1, Constants.app_name);
					cf_call.cfLogout();
				}
				MonitoringService.cpu = new ArrayList<Double>(); // Refresh CPU utilization for the next cool_down period
			}
		}

	}
}
