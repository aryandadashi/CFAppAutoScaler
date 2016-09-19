package com.sap.hcp.autoscaler.data;

public class ModelDataObject {

	private int timeStamp;
	private double avgResponseTime;
	private double requestsPerSecond;
	private int noInstances;
	private double memory_percent;
	private double disk_percent;
	private double cpu_percent;
	
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getAvgResponseTime() {
		return avgResponseTime;
	}
	public void setAvgResponseTime(double avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}
	public double getRequestsPerSecond() {
		return requestsPerSecond;
	}
	public void setRequestsPerSecond(double requestsPerSecond) {
		this.requestsPerSecond = requestsPerSecond;
	}
	public int getNoInstances() {
		return noInstances;
	}
	public void setNoInstances(int noInstances) {
		this.noInstances = noInstances;
	}
	public double getMemory_percent() {
		return memory_percent;
	}
	public void setMemory_percent(double memory_percent) {
		this.memory_percent = memory_percent;
	}
	public double getDisk_percent() {
		return disk_percent;
	}
	public void setDisk_percent(double disk_percent) {
		this.disk_percent = disk_percent;
	}
	public double getCpu_percent() {
		return cpu_percent;
	}
	public void setCpu_percent(double cpu_percent) {
		this.cpu_percent = cpu_percent;
	}	
	
}
